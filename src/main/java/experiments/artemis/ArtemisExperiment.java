
package experiments.artemis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.world.IMetric;

import com.artemis.Entity;
import com.artemis.World;
import com.google.inject.Inject;
import com.google.inject.Injector;

import experiments.IExperimentManager;
import experiments.IExperimentView;
import experiments.artemis.ai.StrategyPlanner;
import experiments.artemis.ai.behaviours.TaskSelector;
import experiments.artemis.ai.world2d.EuclideanMetric2D;
import experiments.artemis.ai.world2d.Position;
import experiments.artemis.components.MovementSpeedComponent;
import experiments.artemis.components.PositionComponent;
import experiments.artemis.components.PositionGoal;
import experiments.artemis.components.behaviours.BehaviorComponent;
import experiments.artemis.components.behaviours.Idle;
import experiments.artemis.components.tasks.MoveTo;
import experiments.artemis.components.tasks.TaskComponent;
import experiments.artemis.systems.BehaviourSystem;
import experiments.artemis.systems.DebugEntityPositionSystem;
import experiments.artemis.systems.NavigationSystem;
import experiments.artemis.systems.TaskSystem;


public class ArtemisExperiment implements IExperimentManager
{
	private static final Logger log = LoggerFactory.getLogger(ArtemisExperiment.class);


	@Inject
	protected Injector injector;


	@Inject
	protected IExperimentView view;


	@Inject
	protected EuclideanMetric2D metric;


	private boolean isAiRunning = false;


	private World world;


	public void initialize()
	{
		world = new World();

		world.setSystem(new NavigationSystem((IMetric) metric), true);
		world.setSystem(new BehaviourSystem());
		world.setSystem(new TaskSystem(new StrategyPlanner()));
		world.setSystem(new DebugEntityPositionSystem(view));

		world.initialize();

		Position[] positions = new Position[] { new Position(100, 50), new Position(100, 200), new Position(300, 500)};
		TaskComponent[] tasks = new TaskComponent[] { new MoveTo(), new MoveTo(), new Idle() };
		PositionGoal[] goals = new PositionGoal[] { new PositionGoal(positions[1]), new PositionGoal(positions[2]), };

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

		Entity e = world.createEntity();
		TaskSelector selector = new TaskSelector();
		selector.setBehaviours(tasks[0], tasks[1], tasks[2]);
		
		e.addComponent(new PositionComponent(positions[0]));
		e.addComponent(new BehaviorComponent(selector));
		e.addComponent(new MovementSpeedComponent(10f));
		e.addToWorld();
		
		e = world.createEntity();
		selector = new TaskSelector();
		selector.setBehaviours(tasks[1], tasks[0], tasks[2]);

		e.addComponent(new PositionComponent(positions[2]));
		e.addComponent(new BehaviorComponent(selector));
		e.addComponent(new MovementSpeedComponent(20f));
		e.addToWorld();
		
		e = world.createEntity();
		selector = new TaskSelector();
		selector.setBehaviours(tasks[2], tasks[1], tasks[0]);

		e.addComponent(new PositionComponent(positions[1]));
		e.addComponent(new BehaviorComponent(selector));
		e.addComponent(new MovementSpeedComponent(30f));
		e.addToWorld();
		
		e = world.createEntity();
		selector = new TaskSelector();
		selector.setBehaviours(tasks[2], tasks[0], tasks[0]);

		e.addComponent(new PositionComponent(positions[1]));
		e.addComponent(new BehaviorComponent(selector));
		e.addComponent(new MovementSpeedComponent(30f));
		e.addToWorld();
		
		e = world.createEntity();
		selector = new TaskSelector();
		selector.setBehaviours(tasks[0], tasks[2], tasks[1]);

		e.addComponent(new PositionComponent(positions[2]));
		e.addComponent(new BehaviorComponent(selector));
		e.addComponent(new MovementSpeedComponent(30f));
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


	public void doAiLogicStep(long step)
	{
		if (isAiRunning)
		{
			log.debug("------------------ running AI ------------------");
			long start = System.nanoTime();
			world.setDelta(step / 1000f);
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
