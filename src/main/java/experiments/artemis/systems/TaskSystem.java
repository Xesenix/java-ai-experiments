
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
import experiments.artemis.ai.strategy.IStrategy;

import experiments.artemis.components.tasks.TaskComponent;
import experiments.ui.PositionDebugSprite;


public class TaskSystem extends EntityProcessingSystem
{
	private static final Logger log = LoggerFactory.getLogger(TaskSystem.class);


	@Mapper
	ComponentMapper<TaskComponent> tm;
	
	
	private Bag<IGoal> goalsByEntity = new Bag<IGoal>();
	
	
	private Bag<IStrategy> strategiesByEntity = new Bag<IStrategy>();


	private StrategyPlanner planner;


	public TaskSystem(StrategyPlanner planner)
	{
		super(Aspect.getAspectForOne(TaskComponent.class));
		
		this.planner = planner;
	}


	protected void process(Entity e)
	{
		log.debug("processing entity {}", e);

		IStrategy chosenStrategy = strategiesByEntity.get(e.getId());
		TaskComponent task = tm.get(e); // get entity task
		
		log.debug("task {}", task);
		log.debug("current strategy {}", chosenStrategy);

		if (chosenStrategy == null && planner != null)
		{
			// choosing strategy
			
			IGoal goal = goalsByEntity.get(e.getId());
			
			if (goal == null || goal.achived(world, e))
			{
				// choosing goal
				
				IGoal[] goals = task.getGoals();
				
				log.debug("goals {}", goals);
				
				IStrategy strategy = null;
				
				for (int i = 0; i < goals.length; i++)
				{
					goal = goals[i];
	
					if (!goal.achived(world, e))
					{
						// find strategy to achieve goal
						
						IStrategy[] strategies = planner.findStrategies(goal, e);
	
						for (int j = 0; j < strategies.length; j++)
						{
							strategy = strategies[j];
							
							log.debug("checking strategy {}", strategy);
							
							if (strategy.canPerform(world, e, goal))
							{
								chosenStrategy = strategy;
								break;
							}
						}
					}
	
					if (chosenStrategy != null)
					{
						strategiesByEntity.set(e.getId(), chosenStrategy);
						break;
					}
				}
			}
			
			goalsByEntity.set(e.getId(), goal);
		}

		log.debug("new strategy {}", chosenStrategy);

		if (chosenStrategy != null)
		{
			// check is strategy can be performed
			
			IGoal goal = goalsByEntity.get(e.getId());
			
			if (chosenStrategy.canPerform(world, e, goal))
			{
				// performing strategy
				
				boolean finished = chosenStrategy.perform(world, e, goal);

				log.debug("strategy performed {}", finished);
				
				e.changedInWorld();

				if (finished)
				{
					strategiesByEntity.remove(e.getId());
					chosenStrategy = null;
				}
			}
			else
			{
				chosenStrategy = null;
			}
		}
		
		// 
		strategiesByEntity.set(e.getId(), chosenStrategy);

		if (chosenStrategy == null)
		{
			e.removeComponent(task);
			e.changedInWorld();
		}
	}

}
