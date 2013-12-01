
package experiments.artemis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.artemis.Entity;
import com.artemis.World;
import com.google.inject.Inject;
import com.google.inject.Injector;

import experiments.IExperimentManager;
import experiments.IExperimentView;
import experiments.artemis.ai.StrategyPlanner;
import experiments.artemis.ai.behaviours.TaskSelector;
import experiments.artemis.components.PositionComponent;
import experiments.artemis.components.PositionGoal;
import experiments.artemis.components.tasks.MoveTo;
import experiments.artemis.components.tasks.TaskComponent;
import experiments.artemis.componentsbehaviours.BehaviorComponent;
import experiments.artemis.componentsbehaviours.Idle;
import experiments.artemis.systems.BehaviourSystem;
import experiments.artemis.systems.TaskSystem;


public class ArtemisExperiment implements IExperimentManager
{
	private static final Logger log = LoggerFactory.getLogger(ArtemisExperiment.class);


	@Inject
	protected Injector injector;


	@Inject
	protected IExperimentView view;


	private boolean isAiRunning = false;


	private World world;


	private BehaviourSystem behaviourSystem;


	public void initialize()
	{
		world = new World();

		world.setSystem(new BehaviourSystem());
		world.setSystem(new TaskSystem(new StrategyPlanner()));

		world.initialize();

		Entity e = world.createEntity();

		TaskComponent[] tasks = new TaskComponent[] { new MoveTo(), new MoveTo(), new Idle() };
		PositionGoal[] goals = new PositionGoal[] { new PositionGoal(10, 20), new PositionGoal(30, 50), };

		int j = -1;

		for (int i = 0; i < tasks.length; i++)
		{
			TaskComponent task = tasks[i];

			if (task instanceof MoveTo)
			{
				j = (j + 1) % goals.length;

				((MoveTo) task).setTarget((PositionGoal) goals[j]);

				Entity target = world.createEntity();
				target.addComponent(goals[j]);
				target.addToWorld();
			}
		}

		TaskSelector selector = new TaskSelector();
		selector.setBehaviours(tasks[0], tasks[1], tasks[2]);

		e.addComponent(new PositionComponent(0, 0));
		e.addComponent(new BehaviorComponent(selector));
		e.addToWorld();
	}


	public void startAi()
	{
		log.debug("------------------ starting AI ------------------");

		isAiRunning = true;
	}


	public void stopAi()
	{
		log.debug("------------------ stopping AI ------------------");

		isAiRunning = false;
	}


	public void doAiLogicStep()
	{
		if (isAiRunning)
		{
			log.debug("------------------ running AI ------------------");
			long start = System.nanoTime();
			world.process();
			log.debug("------------------ end time: {} ------------------", (System.nanoTime() - start) / 1000000f);
		}
	}


	public void loadAiFromXmlString(String source)
	{
	}


	public String getAiAsXmlString()
	{
		return null;
	}


	public void loadWorldFromXmlString(String source)
	{
	}


	public String getWorldAsXmlString()
	{
		return null;
	}


	public void loadAiFromJsonString(String source)
	{
	}


	public String getAiAsJsonString()
	{
		return null;
	}


	public void loadWorldFromJsonString(String source)
	{
	}


	public String getWorldAsJsonString()
	{
		return null;
	}


	public void saveXmlSchema()
	{

	}

}
