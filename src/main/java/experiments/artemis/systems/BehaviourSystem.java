
package experiments.artemis.systems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.Bag;

import experiments.artemis.ai.StrategyPlanner;
import experiments.artemis.ai.behaviours.IGoal;
import experiments.artemis.ai.behaviours.ITask;
import experiments.artemis.ai.strategy.IStrategy;
import experiments.artemis.components.BehaviorComponent;
import experiments.artemis.components.PositionGoal;


public class BehaviourSystem extends EntityProcessingSystem
{
	private static final Logger log = LoggerFactory.getLogger(BehaviourSystem.class);


	@Mapper
	ComponentMapper<BehaviorComponent> bm;
	
	
	private Bag<ITask> taskByEntity = new Bag<ITask>();
	
	
	private Bag<IGoal> goalByEntity = new Bag<IGoal>();
	
	
	private Bag<IStrategy> strategyByEntity = new Bag<IStrategy>();


	private StrategyPlanner planner;


	public BehaviourSystem(StrategyPlanner planner)
	{
		super(Aspect.getAspectForOne(BehaviorComponent.class));
		
		this.planner = planner;
	}


	protected void process(Entity e)
	{
		log.info("processing entity {}", e);
		log.info("retriving entity state..");
		
		BehaviorComponent behavior = bm.get(e); // get behavior for entity

		log.debug("entity behavior {}", behavior);

		// decide what to do
		
		ITask task = taskByEntity.get(e.getId());
		
		if (task == null)
		{
			task = behavior.chooseTask(world, e);
			
			log.debug("entity new task {}", task);
		}
		
		if (task == null)
		{
			return;
		}
		
		IStrategy runningStrategy = strategyByEntity.get(e.getId());
		
		log.debug("current task {}", task);
		log.debug("current running strategy {}", runningStrategy);

		if (runningStrategy == null && planner != null)
		{
			// choosing new strategy
			log.info("choosing new strategy");
			
			IGoal goal = goalByEntity.get(e.getId());
			
			log.debug("current goal {}", goal);
			
			if (goal != null && goal.achived(world, e))
			{
				log.info("goal achived");
				
				goal = null;
			}
			
			if (goal == null)
			{
				findStrategy(e, task);
			}
		}
		
		runningStrategy = strategyByEntity.get(e.getId());

		log.debug("chosen strategy: {}", runningStrategy);

		if (runningStrategy != null)
		{
			// check is strategy can be performed
			
			IGoal goal = goalByEntity.get(e.getId());
			
			if (runningStrategy.canPerform(world, e, goal))
			{
				// performing strategy
				log.info("performing chosen strategy");
				
				boolean finished = runningStrategy.perform(world, e, goal);

				log.debug("strategy finished: {}", finished);
				
				//e.changedInWorld();

				if (finished)
				{
					log.info("finished performing chosen strategy");
					
					if (goal.achived(world, e))
					{
						log.info("goal achived");
						// TODO strategy successful modify priority so it would be used more often
					}
					else
					{
						log.error("goal not achived");
						// TODO strategy unsuccessful modify priority so it would be used less frequent
					}
					
					strategyByEntity.remove(e.getId());
					runningStrategy = null;
				}
			}
			else
			{
				log.info("can`t perform chosen strategy");
				runningStrategy = null;
			}
		}
		
		if (task.finished(world, e))
		{
			log.info("task completed");
			task.setCompleted(world, e, true);
			task = null;
		}
		
		strategyByEntity.set(e.getId(), runningStrategy);
		taskByEntity.set(e.getId(), task);
	}


	public void findStrategy(Entity e, ITask task)
	{
		IGoal[] goals = task.getGoals();
		IGoal goal;
		
		log.debug("avaliable goals {}", goals);
		
		IStrategy strategy;
		boolean strategyExists = false;
		
		for (int i = 0; i < goals.length; i++)
		{
			goal = goals[i];
			
			if (!goal.achived(world, e))
			{
				// find strategy to achieve goal
				
				IStrategy[] strategies = planner.findStrategies(world, e, goal);
				
				// TODO use priorities for more successful strategies
				
				for (int j = 0; j < strategies.length; j++)
				{
					strategy = strategies[j];
					
					if (strategy.canPerform(world, e, goal))
					{
						strategyExists = true;
						
						log.debug("chosen strategy: {}", strategy);
						strategyByEntity.set(e.getId(), strategy);
					}
				}
			}
			
			if (strategyExists)
			{
				log.debug("chosen goal: {}", goal);
				goalByEntity.set(e.getId(), goal);
				
				return;
			}
		}
	}

}
