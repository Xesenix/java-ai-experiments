
package experiments.artemis;

import java.io.StringWriter;

import javafx.scene.paint.Color;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

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
import experiments.artemis.ai.behaviours.IBehavior;
import experiments.artemis.ai.behaviours.PrioritySelector;
import experiments.artemis.ai.behaviours.SequenceSelector;
import experiments.artemis.ai.conditions.Not;
import experiments.artemis.ai.goals.IPositionGoal;
import experiments.artemis.ai.goals.KeepInAreaGoal;
import experiments.artemis.ai.goals.NearPositionGoal;
import experiments.artemis.ai.goals.PositionGoal;
import experiments.artemis.ai.tasks.PositionTask;
import experiments.artemis.ai.tasks.Task;
import experiments.artemis.ai.world2d.EuclideanMetric2D;
import experiments.artemis.ai.world2d.Polygon;
import experiments.artemis.ai.world2d.Position;
import experiments.artemis.components.BehaviorComponent;
import experiments.artemis.components.ColorComponent;
import experiments.artemis.components.ConsoleDebugComponent;
import experiments.artemis.components.MovementDirectionComponent;
import experiments.artemis.components.MovementSpeedComponent;
import experiments.artemis.components.NearDistanceComponent;
import experiments.artemis.components.PositionComponent;
import experiments.artemis.components.ShapeComponent;
import experiments.artemis.systems.BehaviourSystem;
import experiments.artemis.systems.DebugEntitySystem;
import experiments.artemis.systems.MovementSystem;
import experiments.artemis.systems.NavigationSystem;
import experiments.artemis.systems.TasksSystem;
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
		
		BehaviourSystem behaviorSystem = world.setSystem(new BehaviourSystem(2.5f));
		world.setSystem(new TasksSystem(new StrategyPlanner(), 0.5f));
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
			new Position(0, 0),
		};

		IPositionGoal[] goals = null;
		
		try
		{
			goals = new IPositionGoal[] {
				new NearPositionGoal(targtPositions[0], 10),
				new NearPositionGoal(targtPositions[1], 20),
				new NearPositionGoal(targtPositions[2], 50),
				new NearPositionGoal(targtPositions[3], 5),
				new NearPositionGoal(targtPositions[4], 80),
				new KeepInAreaGoal(0, 0, 300, 0, 500, 300, 0, 500),
			};
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		Task[] tasks = new Task[] {
			new PositionTask("simple target 0", goals[0]),
			new PositionTask("simple target 1", goals[1]),
			new PositionTask("simple target 2", goals[2]),
			new PositionTask("simple target 3", goals[3]),
			new PositionTask("near 2 points", new PositionGoal(goals[4], goals[2])),
			new PositionTask("keep in area", goals[5]),
		};
		
		IBehavior keepInAreaBehavior = new SequenceSelector(
			new Not(goals[5]),
			tasks[5]
		);
		
		SequenceSelector quests = new SequenceSelector(
			new Counter(tasks[0], 3),
			tasks[1],
			tasks[2],
			new Counter(tasks[3], 2),
			new Counter(tasks[4], 4)
		);
		
		IBehavior crowdBehavior = new PrioritySelector(
			new Counter(keepInAreaBehavior, 2),
			quests
		);
		
		behaviorSystem.addBehavior("crowd", crowdBehavior);

		// Actors
		Entity entity = world.createEntity();
		world.getManager(GroupManager.class).add(entity, "actors");

		entity.addComponent(new ConsoleDebugComponent());
		entity.addComponent(new PositionComponent(positions[0]));
		entity.addComponent(new BehaviorComponent("crowd"));
		entity.addComponent(new MovementSpeedComponent(100, -50, 200, 250, 120));
		entity.addComponent(new MovementDirectionComponent(0, 0.5 * Math.PI));
		entity.addComponent(new NearDistanceComponent(60f));
		entity.addToWorld();

		/*entity = world.createEntity();

		entity.addComponent(new PositionComponent(positions[1]));
		entity.addComponent(new BehaviorComponent("crowd"));
		entity.addComponent(new MovementSpeedComponent(50f, 50f));
		entity.addComponent(new MovementDirectionComponent(0, 0.5 * Math.PI));
		entity.addComponent(new NearDistanceComponent(30f));
		entity.addToWorld();

		entity = world.createEntity();

		entity.addComponent(new PositionComponent(positions[2]));
		entity.addComponent(new BehaviorComponent("crowd"));
		entity.addComponent(new MovementSpeedComponent(130f, 200f));
		entity.addComponent(new MovementDirectionComponent(0, 0.5 * Math.PI));
		entity.addComponent(new NearDistanceComponent(50f));
		entity.addToWorld();

		entity = world.createEntity();

		entity.addComponent(new PositionComponent(positions[3]));
		entity.addComponent(new BehaviorComponent("crowd"));
		entity.addComponent(new MovementSpeedComponent(120f));
		entity.addComponent(new MovementDirectionComponent(0, 0.5 * Math.PI));
		entity.addComponent(new NearDistanceComponent(50f));
		entity.addToWorld();

		entity = world.createEntity();

		entity.addComponent(new PositionComponent(positions[4]));
		entity.addComponent(new BehaviorComponent("crowd"));
		entity.addComponent(new MovementSpeedComponent(80f));
		entity.addComponent(new MovementDirectionComponent(0, 1.5 * Math.PI));
		entity.addComponent(new NearDistanceComponent(50f));
		entity.addToWorld();*/

		// Landmarks
		for (int i = 0; i < targtPositions.length; i++)
		{
			entity = world.createEntity();
			
			world.getManager(GroupManager.class).add(entity, "targets");
			
			entity.addComponent(new PositionComponent(targtPositions[i]));
			
			if (goals[i] instanceof NearPositionGoal)
			{
				entity.addComponent(new NearDistanceComponent(((NearPositionGoal)goals[i]).getPrecision()));
				entity.addComponent(new ColorComponent(Color.rgb(255, 0, 0, 0.3f)));

				// e.addComponent(new MovementSpeedComponent(5f));
				entity.addComponent(new MovementDirectionComponent());
			}
			
			if (goals[i] instanceof KeepInAreaGoal)
			{
				entity.addComponent(new ShapeComponent((Polygon) ((KeepInAreaGoal) goals[i]).getArea()));
				entity.addComponent(new ColorComponent(Color.rgb(255, 0, 0, 0.3f)));
			}
			
			entity.addToWorld();
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
