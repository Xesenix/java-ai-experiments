
package experiments.artemis;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import javafx.scene.paint.Color;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.world.IMetric;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.badlogic.gdx.utils.Json;
import com.google.inject.Inject;
import com.google.inject.Injector;

import experiments.IExperimentManager;
import experiments.IExperimentView;
import experiments.artemis.ai.StrategyPlanner;
import experiments.artemis.ai.behaviours.Counter;
import experiments.artemis.ai.behaviours.IPositionGoal;
import experiments.artemis.ai.behaviours.NearPositionGoal;
import experiments.artemis.ai.behaviours.PositionTask;
import experiments.artemis.ai.behaviours.Task;
import experiments.artemis.ai.behaviours.TaskSelector;
import experiments.artemis.ai.world2d.EuclideanMetric2D;
import experiments.artemis.ai.world2d.Position;
import experiments.artemis.components.BehaviorComponent;
import experiments.artemis.components.ColorComponent;
import experiments.artemis.components.ConsoleDebugComponent;
import experiments.artemis.components.MovementDirectionComponent;
import experiments.artemis.components.MovementSpeedComponent;
import experiments.artemis.components.NearDistanceComponent;
import experiments.artemis.components.PositionComponent;
import experiments.artemis.systems.BehaviourSystem;
import experiments.artemis.systems.DebugEntitySystem;
import experiments.artemis.systems.MovementSystem;
import experiments.artemis.systems.NavigationSystem;
import experiments.artemis.world.WorldMarshaller;


public class ArtemisExperiment implements IExperimentManager
{
	private static final Logger log = LoggerFactory.getLogger(ArtemisExperiment.class);


	@Inject
	protected Injector injector;


	@Inject
	protected IExperimentView view;


	@Inject
	protected EuclideanMetric2D metric;


	@Inject
	private WorldMarshaller worldMarshaller;


	@Inject
	private Json json;


	@Inject
	private JAXBContext jaxbContext;


	private boolean isAiRunning = false;


	private World world;


	public void initialize()
	{
		world = new World();
		
		world.setSystem(new BehaviourSystem(new StrategyPlanner(), 2.5f));
		world.setSystem(new NavigationSystem((IMetric) metric, 0.05f));
		world.setSystem(new MovementSystem());
		world.setSystem(new DebugEntitySystem(view));
		
		world.setManager(new GroupManager());

		world.initialize();

		Position[] positions = new Position[] {
			new Position(100, 150),
			new Position(200, 200),
			new Position(100, 300),
			new Position(400, 100),
			new Position(300, 100),
		};

		Position[] targtPositions = new Position[] {
			new Position(100, 50),
			new Position(100, 200),
			new Position(300, 100),
			new Position(400, 100),
			new Position(300, 200),
		};

		IPositionGoal[] goals = new NearPositionGoal[] {
			new NearPositionGoal(targtPositions[0], 10),
			new NearPositionGoal(targtPositions[1], 20),
			new NearPositionGoal(targtPositions[2], 50),
			new NearPositionGoal(targtPositions[3], 5),
			new NearPositionGoal(targtPositions[4], 80),
		};

		Task[] tasks = new Task[] {
			new PositionTask(goals[0]),
			new PositionTask(goals[1]),
			new PositionTask(goals[2]),
			new PositionTask(goals[3]),
			new PositionTask(goals[4], goals[2]),
		};

		// Actors
		Entity e = world.createEntity();
		
		world.getManager(GroupManager.class).add(e, "actors");
		
		TaskSelector selector = new TaskSelector(
			new Counter(tasks[0], 2),
			tasks[1],
			tasks[2],
			new Counter(tasks[3], 2),
			new Counter(tasks[4], 2)
		);

		e.addComponent(new ConsoleDebugComponent());
		e.addComponent(new PositionComponent(positions[0]));
		e.addComponent(new BehaviorComponent(selector));
		e.addComponent(new MovementSpeedComponent(100, -50, 200, 250, 120));
		e.addComponent(new MovementDirectionComponent(0, 0.2 * Math.PI));
		e.addComponent(new NearDistanceComponent(60f));
		e.addToWorld();

		/*e = world.createEntity();
		selector = new TaskSelector(
			tasks[1],
			tasks[0],
			tasks[2]
		);

		e.addComponent(new PositionComponent(positions[1]));
		e.addComponent(new BehaviorComponent(selector));
		e.addComponent(new MovementSpeedComponent(50f, 50f));
		e.addComponent(new MovementDirectionComponent(0, 0.5 * Math.PI));
		e.addComponent(new NearDistanceComponent(30f));
		e.addToWorld();

		e = world.createEntity();
		selector = new TaskSelector(
			tasks[2],
			tasks[1],
			tasks[0]
		);

		e.addComponent(new PositionComponent(positions[2]));
		e.addComponent(new BehaviorComponent(selector));
		e.addComponent(new MovementSpeedComponent(130f, 200f));
		e.addComponent(new MovementDirectionComponent(0, 0.5 * Math.PI));
		e.addComponent(new NearDistanceComponent(50f));
		e.addToWorld();

		e = world.createEntity();
		selector = new TaskSelector(
			tasks[2],
			tasks[3],
			tasks[4]
		);

		e.addComponent(new PositionComponent(positions[3]));
		e.addComponent(new BehaviorComponent(selector));
		e.addComponent(new MovementSpeedComponent(120f));
		e.addComponent(new MovementDirectionComponent(0, 0.5 * Math.PI));
		e.addComponent(new NearDistanceComponent(50f));
		e.addToWorld();

		e = world.createEntity();
		selector = new TaskSelector(
			tasks[4],
			tasks[2],
			tasks[1]
		);

		e.addComponent(new PositionComponent(positions[4]));
		e.addComponent(new BehaviorComponent(selector));
		e.addComponent(new MovementSpeedComponent(80f));
		e.addComponent(new MovementDirectionComponent(0, 1.5 * Math.PI));
		e.addComponent(new NearDistanceComponent(50f));
		e.addToWorld();*/

		// Landmarks
		for (int i = 0; i < targtPositions.length; i++)
		{
			e = world.createEntity();
			
			world.getManager(GroupManager.class).add(e, "targets");
			
			e.addComponent(new PositionComponent(targtPositions[i]));
			
			if (goals[i] instanceof NearPositionGoal)
			{
				e.addComponent(new NearDistanceComponent(((NearPositionGoal)goals[i]).getPrecision()));
				e.addComponent(new ColorComponent(Color.rgb(255, 0, 0, 0.3f)));

				e.addComponent(new MovementSpeedComponent(5f));
				e.addComponent(new MovementDirectionComponent());
			}
			
			e.addToWorld();
		}
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
		StringWriter xmlWriter = new StringWriter();

		Marshaller jaxbMarshaller;
		try
		{
			jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(worldMarshaller.marshall(world), xmlWriter);
		}
		catch (JAXBException e)
		{
			e.printStackTrace();
		}

		String result = xmlWriter.toString();

		log.debug("serialized - XML World: {}", result);

		return result;
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
		String result = json.prettyPrint(worldMarshaller.marshall(world));

		log.debug("serialized - Json World: {}", result);

		return result;
	}


	public void saveXmlSchema()
	{

	}

}
