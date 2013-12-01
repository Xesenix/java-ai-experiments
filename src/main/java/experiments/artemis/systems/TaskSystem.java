
package experiments.artemis.systems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.behaviour.IGoal;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

import experiments.artemis.ai.StrategyPlanner;
import experiments.artemis.components.IStrategy;
import experiments.artemis.components.strategies.StrategyComponent;
import experiments.artemis.components.tasks.TaskComponent;


public class TaskSystem extends EntityProcessingSystem
{
	private static final Logger log = LoggerFactory.getLogger(TaskSystem.class);


	@Mapper
	ComponentMapper<TaskComponent> tm;


	@Mapper
	ComponentMapper<StrategyComponent> sm;


	private StrategyPlanner planner;


	public TaskSystem(StrategyPlanner planner)
	{
		super(Aspect.getAspectForOne(TaskComponent.class));
		this.planner = planner;
	}


	protected void process(Entity e)
	{
		log.debug("processing entity {}", e);

		StrategyComponent chosenStrategy = sm.get(e);
		TaskComponent task = tm.get(e); // get entity task

		log.debug("task {}", task);
		log.debug("current strategy {}", chosenStrategy);

		if (chosenStrategy == null && planner != null)
		{
			IGoal[] goals = task.getGoals();
			IGoal goal = null;
			IStrategy strategy = null;

			log.debug("goals {}", goals);

			for (int i = 0; i < goals.length; i++)
			{
				goal = goals[i];

				if (!goal.achived())
				{
					// find strategy to achieve goal
					IStrategy[] strategies = planner.findStrategies(goal, e);

					for (int j = 0; j < strategies.length; j++)
					{
						strategy = strategies[j];
						log.debug("checking strategy {}", strategy);
						if (strategy.canPerform(world, e, goal))
						{
							chosenStrategy = new StrategyComponent(strategy);
							break;
						}
						else
						{
							log.debug("can`t perform strategy {}", strategy);
						}
					}
				}

				if (chosenStrategy != null)
				{
					break;
				}
			}

			if (goal != null)
			{
				task.setCurrentGoal(goal);
			}
		}

		log.debug("new strategy {}", chosenStrategy);

		if (chosenStrategy != null)
		{
			if (chosenStrategy.canPerform(world, e, task.getCurrentGoal()))
			{
				boolean finished = chosenStrategy.perform(world, e, task.getCurrentGoal());

				log.debug("strategy performed {}", finished);

				if (finished)
				{
					e.removeComponent(chosenStrategy);
					chosenStrategy = null;
				}
				else if (!sm.has(e))
				{
					e.addComponent(chosenStrategy);
				}
			}
			else
			{
				chosenStrategy = null;
			}
		}

		if (chosenStrategy == null)
		{
			e.removeComponent(task);
		}

		e.changedInWorld();
	}

}
