
package experiments.artemis.systems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.Bag;

import experiments.artemis.ai.StrategyPlanner;
import experiments.artemis.ai.behaviours.IGoal;
import experiments.artemis.ai.behaviours.ITask;
import experiments.artemis.ai.strategy.IStrategy;
import experiments.artemis.components.tasks.TaskComponent;
import experiments.ui.PositionDebugSprite;


public class TaskSystem extends EntityProcessingSystem
{
	private static final Logger log = LoggerFactory.getLogger(TaskSystem.class);


	@Mapper
	ComponentMapper<TaskComponent> tm;
	
	
	private Bag<IGoal> goalByEntity = new Bag<IGoal>();
	
	
	private Bag<IStrategy> strategyByEntity = new Bag<IStrategy>();


	private StrategyPlanner planner;


	public TaskSystem(StrategyPlanner planner)
	{
		super(Aspect.getAspectForOne(TaskComponent.class));
		
		this.planner = planner;
	}


	protected void process(Entity e)
	{
		log.info("processing entity {}", e);
		log.info("retriving entity state..");

		IStrategy chosenStrategy = strategyByEntity.get(e.getId());
		TaskComponent task = tm.get(e); // get entity task
		
		
		log.debug("current task {}", task);
		log.debug("current strategy {}", chosenStrategy);

		if (chosenStrategy == null && planner != null)
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
		
		chosenStrategy = strategyByEntity.get(e.getId());

		log.debug("chosen strategy: {}", chosenStrategy);

		if (chosenStrategy != null)
		{
			// check is strategy can be performed
			
			IGoal goal = goalByEntity.get(e.getId());
			
			if (chosenStrategy.canPerform(world, e, goal))
			{
				// performing strategy
				log.info("performing chosen strategy");
				
				boolean finished = chosenStrategy.perform(world, e, goal);

				log.debug("strategy performed {}", finished);
				
				e.changedInWorld();

				if (finished)
				{
					log.info("finished performing chosen strategy");
					
					strategyByEntity.remove(e.getId());
					chosenStrategy = null;
				}
			}
			else
			{
				log.info("can`t perform chosen strategy");
				chosenStrategy = null;
			}
		}
		
		// 
		strategyByEntity.set(e.getId(), chosenStrategy);

		if (chosenStrategy == null)
		{
			e.removeComponent(task);
			e.changedInWorld();
		}
	}


	public void findStrategy(Entity e, ITask task)
	{
		IGoal[] goals = task.getGoals();
		IGoal goal;
		
		log.debug("aviable goals {}", goals);
		
		IStrategy strategy;
		boolean strategyExists = false;
		
		for (int i = 0; i < goals.length; i++)
		{
			goal = goals[i];
			
			if (!goal.achived(world, e))
			{
				// find strategy to achieve goal
				
				IStrategy[] strategies = planner.findStrategies(world, e, goal);
				
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
