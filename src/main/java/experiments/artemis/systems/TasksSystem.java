
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
import experiments.artemis.ai.behaviours.ITask;
import experiments.artemis.ai.behaviours.TaskState;
import experiments.artemis.ai.strategy.IStrategy;
import experiments.artemis.components.ConsoleDebugComponent;
import experiments.artemis.components.TasksComponent;


public class TasksSystem extends IntervalEntityProcessingSystem
{
	private static final ActiveLogger log = new ActiveLogger(LoggerFactory.getLogger(TasksSystem.class));


	@Mapper
	ComponentMapper<TasksComponent> tm;


	@Mapper
	ComponentMapper<ConsoleDebugComponent> cdm;


	private Bag<IStrategy> strategyByEntity = new Bag<IStrategy>();


	private StrategyPlanner strategyPlanner;


	public TasksSystem(StrategyPlanner strategyPlanner, float interval)
	{
		super(Aspect.getAspectForOne(TasksComponent.class), interval);

		this.strategyPlanner = strategyPlanner;
	}


	protected void process(Entity e)
	{
		boolean finished = true;

		log.setActive(cdm.get(e) != null && cdm.get(e).behavior);

		log.info("processing entity {}", e);
		log.info("retriving entity state..");

		TasksComponent tasksComponent = tm.get(e); // get behavior for entity
		
		log.info("tasks {}", tasksComponent);

		// decide what to do

		Iterator<ITask> iter = tasksComponent.getTasks().iterator();
		
		while (iter.hasNext())
		{
			ITask task = iter.next();
			
			runTask(e, task);
			
			if (task.isCompleted(world, e))
			{
				iter.remove();
			}
			
		}
		
		log.info("tasks {}", tasksComponent);
	}


	public boolean runTask(Entity e, ITask task)
	{
		boolean finished;
		
		log.setActive(cdm.get(e) != null && cdm.get(e).behavior);
		
		log.debug("running task {}", task);
		
		IStrategy runningStrategy = strategyByEntity.get(e.getId());
		
		runningStrategy = strategyPlanner.bestStrategyFor(world, e, task);
		
		log.debug("current running strategy {}", runningStrategy);
		
		if (runningStrategy != null)
		{
			finished = false;

			// check is strategy can be performed

			if (runningStrategy.canPerform(world, e, task))
			{
				task.setState(world, e, TaskState.RUNNING);
				
				// performing strategy
				log.info("performing chosen strategy");

				finished = runningStrategy.perform(world, e, task);

				log.debug("strategy finished: {}", finished);
				
				if (task.getGoals().achived(world, e))
				{
					log.info("goal achived");
					// TODO strategy successful modify priority so it would be used more often
					task.setState(world, e, TaskState.SUCCESS);
					
					return true;
				}
				else
				{
					log.info("goal not achived");
					// TODO strategy unsuccessful modify priority so it would be used less frequent
					
					if (finished)
					{
						log.info("finished performing chosen strategy");
						task.setState(world, e, TaskState.FAILURE);
						
						strategyByEntity.set(e.getId(), null);
						
						return false;
					}
					else
					{
						//task.setState(world, e, TaskState.RUNNING);
						TasksComponent tasksComponent = tm.get(e);
						tasksComponent.addTask(task);
						
						return true;
					}
				}
			}
			else
			{
				log.info("can`t perform chosen strategy");
			}
		}
		
		return false;
	}
}
