
package experiments.artemis.systems;

import org.slf4j.LoggerFactory;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.Bag;

import experiments.artemis.ActiveLogger;
import experiments.artemis.ai.StrategyPlanner;
import experiments.artemis.ai.behaviours.ITask;
import experiments.artemis.ai.strategy.IStrategy;
import experiments.artemis.components.BehaviorComponent;
import experiments.artemis.components.ConsoleDebugComponent;


public class BehaviourSystem extends EntityProcessingSystem
{
	private static final ActiveLogger log = new ActiveLogger(LoggerFactory.getLogger(BehaviourSystem.class));


	@Mapper
	ComponentMapper<BehaviorComponent> bm;


	@Mapper
	ComponentMapper<ConsoleDebugComponent> cdm;


	private Bag<ITask> taskByEntity = new Bag<ITask>();


	private Bag<IStrategy> strategyByEntity = new Bag<IStrategy>();


	private StrategyPlanner strategyPlanner;


	public BehaviourSystem(StrategyPlanner strategyPlanner)
	{
		super(Aspect.getAspectForOne(BehaviorComponent.class));

		this.strategyPlanner = strategyPlanner;
	}


	protected void process(Entity e)
	{
		boolean finished = true;

		log.setActive(cdm.get(e) != null);

		log.info("processing entity {}", e);
		log.info("retriving entity state..");

		BehaviorComponent behavior = bm.get(e); // get behavior for entity

		log.debug("entity behavior {}", behavior);

		// decide what to do

		ITask previousTask = taskByEntity.get(e.getId());
		
		log.debug("previous task {}", previousTask);

		behavior.reset(world, e);
		ITask task = behavior.chooseTask(world, e);

		log.debug("entity new task {}", task);

		if (task == null)
		{
			return;
		}
		
		IStrategy runningStrategy;
		
		if (task != previousTask)
		{
			log.info("choosing new strategy");
			
			runningStrategy = strategyPlanner.bestStrategyFor(world, e, task);
		}
		else
		{
			log.info("continuing old strategy");
			
			runningStrategy = strategyByEntity.get(e.getId());
		}
		
		log.debug("current running strategy {}", runningStrategy);
		
		if (runningStrategy != null)
		{
			finished = false;

			// check is strategy can be performed

			if (runningStrategy.canPerform(world, e, task))
			{
				// performing strategy
				log.info("performing chosen strategy");

				finished = runningStrategy.perform(world, e, task);

				log.debug("strategy finished: {}", finished);

				if (finished)
				{
					log.info("finished performing chosen strategy");

					if (task.isSuccess(world, e))
					{
						log.info("goal achived");
						// TODO strategy successful modify priority so it would be used more often
					}
					else
					{
						log.info("goal not achived");
						// TODO strategy unsuccessful modify priority so it would be used less frequent
					}

					strategyByEntity.remove(e.getId());
					runningStrategy = null;
				}
				else
				{
					task.setCompleted(world, e, false);
				}
			}
			else
			{
				log.info("can`t perform chosen strategy");
				runningStrategy = null;
			}
		}

		if (finished && task.isSuccess(world, e))
		{
			log.info("task completed");
			task.setCompleted(world, e, true);
			task = null;
		}

		strategyByEntity.set(e.getId(), runningStrategy);
		taskByEntity.set(e.getId(), task);
	}
}
