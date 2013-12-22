
package experiments.artemis.systems;

import java.util.Iterator;

import org.slf4j.LoggerFactory;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.IntervalEntityProcessingSystem;
import com.artemis.utils.Bag;

import experiments.artemis.ActiveLogger;
import experiments.artemis.ai.StrategyPlanner;
import experiments.artemis.ai.strategy.IStrategy;
import experiments.artemis.ai.tasks.BehaviorState;
import experiments.artemis.ai.tasks.ITask;
import experiments.artemis.components.ConsoleDebugComponent;
import experiments.artemis.components.TasksComponent;


public class TasksSystem extends IntervalEntityProcessingSystem
{
	private static final ActiveLogger log = new ActiveLogger(LoggerFactory.getLogger(TasksSystem.class));


	@Mapper
	ComponentMapper<TasksComponent> taskMapper;


	@Mapper
	ComponentMapper<ConsoleDebugComponent> consoleDebugMapper;


	private Bag<IStrategy> strategyByEntity = new Bag<IStrategy>();


	private StrategyPlanner strategyPlanner;


	public TasksSystem(StrategyPlanner strategyPlanner, float interval)
	{
		super(Aspect.getAspectForOne(TasksComponent.class), interval);

		this.strategyPlanner = strategyPlanner;
	}


	protected void process(Entity entity)
	{
		boolean finished = true;

		log.setActive(consoleDebugMapper.get(entity) != null && consoleDebugMapper.get(entity).behavior);

		log.info("processing entity {}", entity);
		log.info("retriving entity state..");

		TasksComponent tasksComponent = taskMapper.get(entity); // get behavior for entity
		
		log.info("tasks {}", tasksComponent);

		// decide what to do

		Iterator<ITask> iter = tasksComponent.getTasks().iterator();
		
		log.info("looping entity tasks list");
		
		while (iter.hasNext())
		{
			ITask task = iter.next();
			
			if (task.isReady())
			{
				log.info("loop - starting task {}", task);
				startTask(entity, task);
			}
			
			if (task.isCompleted())
			{
				log.info("loop - ending task {}", task);
				endTask(entity, task);
				
				log.info("loop - removing task {}", task);
				iter.remove();
				
				continue;
			}
			
			log.info("loop - running task {}", task);
			runTask(entity, task);
		}
		
		log.info("tasks {}", tasksComponent);
	}


	public void startTask(Entity actor, ITask task)
	{
		log.setActive(consoleDebugMapper.get(actor) != null && consoleDebugMapper.get(actor).behavior);
		
		log.debug("starting task {}", task);
		
		TasksComponent tasksComponent = taskMapper.get(actor);
		
		log.info("tasks {}", tasksComponent);
		
		tasksComponent.addTask(task);
	}


	public void runTask(Entity actor, ITask task)
	{
		boolean finished;
		
		log.setActive(consoleDebugMapper.get(actor) != null && consoleDebugMapper.get(actor).behavior);
		
		task.setActor(actor);
		
		log.debug("running task {}", task);
		
		IStrategy runningStrategy = strategyByEntity.get(actor.getId());
		
		runningStrategy = strategyPlanner.bestStrategyFor(world, actor, task);
		
		log.debug("current running strategy {}", runningStrategy);
		
		if (runningStrategy != null)
		{
			finished = false;

			// check is strategy can be performed

			if (runningStrategy.canPerform(world, actor, task))
			{
				task.setState(BehaviorState.RUNNING);
				
				// performing strategy
				log.info("performing chosen strategy");

				finished = runningStrategy.perform(world, actor, task);

				log.debug("strategy finished: {}", finished);
				
				if (task.getGoals().achived())
				{
					log.info("goal achived");
					// TODO strategy successful modify priority so it would be used more often
					task.setState(BehaviorState.SUCCESS);
					
					return;
				}
				else
				{
					log.info("goal not achived");
					// TODO strategy unsuccessful modify priority so it would be used less frequent
					
					if (finished)
					{
						log.info("finished performing chosen strategy");
						
						strategyByEntity.set(actor.getId(), null);
						
						// TODO if there is other strategy available choose it
						task.setState(BehaviorState.FAILURE);
						
						return;
					}
					else
					{
						//task.setState(world, e, TaskState.RUNNING);
						//TasksComponent tasksComponent = taskMapper.get(actor);
						//tasksComponent.addTask(task);
						
						return;
					}
				}
			}
			else
			{
				log.info("can`t perform chosen strategy");
			}
		}
		
		task.setState(BehaviorState.FAILURE);
	}


	public void endTask(Entity actor, ITask task)
	{
		log.setActive(consoleDebugMapper.get(actor) != null && consoleDebugMapper.get(actor).behavior);
		
		log.debug("ending task {}", task);
	}
	
	
	protected void removed(Entity entity)
	{
		strategyByEntity.set(entity.getId(), null);
	}
}
