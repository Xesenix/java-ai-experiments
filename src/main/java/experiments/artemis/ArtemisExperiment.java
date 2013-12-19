
package experiments.artemis;

import java.io.StringReader;
import java.io.StringWriter;

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
import experiments.artemis.ai.AiDescriptor;
import experiments.artemis.ai.AiManager;
import experiments.artemis.ai.AiMarshaller;
import experiments.artemis.ai.AiUnmarshaller;
import experiments.artemis.ai.StrategyPlanner;
import experiments.artemis.ai.behaviours.IBehavior;
import experiments.artemis.ai.behaviours.Inverter;
import experiments.artemis.ai.behaviours.Limiter;
import experiments.artemis.ai.behaviours.PrioritySelector;
import experiments.artemis.ai.behaviours.SequenceSelector;
import experiments.artemis.ai.behaviours.Succeeder;
import experiments.artemis.ai.conditions.Not;
import experiments.artemis.ai.goals.IPositionGoal;
import experiments.artemis.ai.goals.KeepInAreaGoal;
import experiments.artemis.ai.goals.NearPositionGoal;
import experiments.artemis.ai.goals.PositionGoal;
import experiments.artemis.ai.tasks.NavigationTask;
import experiments.artemis.ai.tasks.Task;
import experiments.artemis.ai.world2d.EuclideanMetric2D;
import experiments.artemis.ai.world2d.Position;
import experiments.artemis.components.BehaviorComponent;
import experiments.artemis.components.ConsoleDebugComponent;
import experiments.artemis.components.MovementDirectionComponent;
import experiments.artemis.components.MovementSpeedComponent;
import experiments.artemis.components.NearDistanceComponent;
import experiments.artemis.components.PositionComponent;
import experiments.artemis.systems.BehaviourSystem;
import experiments.artemis.systems.DebugActorSystem;
import experiments.artemis.systems.DebugAiSystem;
import experiments.artemis.systems.MovementSystem;
import experiments.artemis.systems.NavigationSystem;
import experiments.artemis.systems.TasksSystem;
import experiments.artemis.world.WorldChangeDescriptor;
import experiments.artemis.world.WorldMarshaller;
import experiments.artemis.world.WorldUnmarshaller;


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
	private AiMarshaller aiMarshaller;


	@Inject
	private AiUnmarshaller aiUnmarshaller;


	@Inject
	private WorldMarshaller worldMarshaller;


	@Inject
	private WorldUnmarshaller worldUnmarshaller;


	@Inject
	private Json json;


	@Inject
	private JAXBContext jaxbContext;


	private boolean isAiRunning = false;


	private World world;


	private AiManager ai;


	public void initialize()
	{
		world = new World();
		
		world.setSystem(new BehaviourSystem(0.5f));
		world.setSystem(new TasksSystem(new StrategyPlanner(), 0.05f));
		world.setSystem(new NavigationSystem((IMetric) metric, 0.05f));
		world.setSystem(new MovementSystem());
		world.setSystem(new DebugAiSystem(view));
		world.setSystem(new DebugActorSystem(view));
		
		world.setManager(new GroupManager());
		ai = world.setManager(new AiManager());

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
				new NearPositionGoal(targtPositions[0], 10.0),
				new NearPositionGoal(targtPositions[1], 20.0),
				new NearPositionGoal(targtPositions[2], 60.0),
				new NearPositionGoal(targtPositions[3], 5.0),
				new NearPositionGoal(targtPositions[4], 80.0),
				new KeepInAreaGoal(0, 0, 300, 0, 500, 300, 0, 500),
			};
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		Task[] tasks = new Task[] {
			new NavigationTask("A", goals[0]),
			new NavigationTask("B", goals[1]),
			new NavigationTask("C", goals[2]),
			new NavigationTask("D", goals[3]),
			new NavigationTask("near 2 points", new PositionGoal(goals[4], goals[2])),
			new NavigationTask("keep in area", goals[5]),
		};
		
		IBehavior keepInAreaBehavior = new SequenceSelector(
			new Not("not in area", goals[5]),
			// new Condition("test", goals[4]),
			// new Task("test 2", goals[4]),
			tasks[5]
		);
		
		SequenceSelector quests = new SequenceSelector(
			new Succeeder(new Limiter(tasks[0], 3)),
			tasks[1],
			tasks[2],
			new Limiter(tasks[3], 2),
			new Limiter(tasks[4], 4)
		);
		
		IBehavior crowdBehavior = new PrioritySelector(
			new Limiter(keepInAreaBehavior, 2),
			quests
		);
		
		try
		{
			ai.setBehavior("simple", tasks[0]);
			ai.setBehavior("crowd", crowdBehavior);
			ai.setBehavior("sequence test", new SequenceSelector(
				new SequenceSelector(
					tasks[0],
					tasks[1],
					tasks[0].clone(),
					tasks[1].clone()
				),
				new Succeeder(new SequenceSelector(
					tasks[2],
					tasks[3],
					tasks[2].clone(),
					tasks[3].clone()
				)),
				tasks[1].clone(),
				new Inverter(tasks[4].clone())
			));
			ai.setBehavior("priority test", new PrioritySelector(
				new SequenceSelector(
					new Not("not in area", new KeepInAreaGoal(0, 0, 200, 0, 300, 300, 0, 200)),
					new NavigationTask("back to area", new KeepInAreaGoal(0, 0, 200, 0, 200, 200, 0, 200))
				),
				new SequenceSelector(
					new NavigationTask("get target A", new NearPositionGoal(new Position(50, 50), 20.0)),
					new NavigationTask("get target B", new NearPositionGoal(new Position(175, 25), 20.0)),
					new NavigationTask("get target C", new NearPositionGoal(new Position(20, 180), 20.0)),
					new NavigationTask("get target D", new NearPositionGoal(new Position(300, 300), 20.0))
				)
			));
			ai.setBehavior("limiter test", new SequenceSelector(
				new Limiter(new NavigationTask("get target A", new NearPositionGoal(new Position(50, 50), 20.0)), 1),
				new Limiter(new NavigationTask("get target B", new NearPositionGoal(new Position(175, 25), 20.0)), 2),
				new NavigationTask("get target C", new NearPositionGoal(new Position(20, 180), 20.0)),
				new NavigationTask("get target D", new NearPositionGoal(new Position(300, 300), 20.0))
			));
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		// Actors
		Entity entity = world.createEntity();
		world.getManager(GroupManager.class).add(entity, "actors");
		world.getManager(GroupManager.class).add(entity, "serializable");

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
		/*for (int i = 0; i < targtPositions.length; i++)
		{
			entity = world.createEntity();
			
			world.getManager(GroupManager.class).add(entity, "targets");
			world.getManager(GroupManager.class).add(entity, "serializable");
			
			entity.addComponent(new PositionComponent(targtPositions[i]));
			
			if (goals[i] instanceof NearPositionGoal)
			{
				entity.addComponent(new NearDistanceComponent(((NearPositionGoal)goals[i]).getPrecision()));
				entity.addComponent(new ColorComponent(Color.rgb(255, 0, 0).toString()));

				// e.addComponent(new MovementSpeedComponent(5f));
				entity.addComponent(new MovementDirectionComponent());
			}
			
			if (goals[i] instanceof KeepInAreaGoal)
			{
				entity.addComponent(new ShapeComponent((Polygon) ((KeepInAreaGoal) goals[i]).getArea()));
				entity.addComponent(new ColorComponent(Color.rgb(255, 0, 0).toString()));
			}
			
			entity.addToWorld();
		}*/
		
		// initialize entieties and systems
		world.process();
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


	public String getAiAsXmlString()
	{
		StringWriter xmlWriter = new StringWriter();

		Marshaller jaxbMarshaller;
		try
		{
			jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(aiMarshaller.marshall(ai), xmlWriter);
		}
		catch (JAXBException e)
		{
			e.printStackTrace();
		}

		String result = xmlWriter.toString();

		log.debug("serialized - XML AI: {}", result);

		return result;
	}


	public void loadAiFromXmlString(String source)
	{
		log.debug("loading - XML AI: {}", source);

		StringReader xmlReader = new StringReader(source);

		try
		{
			AiDescriptor descriptor = (AiDescriptor) jaxbContext.createUnmarshaller().unmarshal(xmlReader);

			aiUnmarshaller.setAi(ai);
			ai = aiUnmarshaller.unmarshal(descriptor);
		}
		catch (JAXBException e)
		{
			e.printStackTrace();
		}

		log.debug("deserialized - AI: {}", ai);
	}


	public void loadWorldFromXmlString(String source)
	{
		log.debug("loading - XML World: {}", source);

		StringReader xmlReader = new StringReader(source);

		try
		{
			WorldChangeDescriptor descriptor = (WorldChangeDescriptor) jaxbContext.createUnmarshaller().unmarshal(xmlReader);
			//world.getEntityManager().
			worldUnmarshaller.setWorld(world);
			world = worldUnmarshaller.unmarshal(descriptor);
		}
		catch (JAXBException e)
		{
			e.printStackTrace();
		}

		log.debug("deserialized - World: {}", world);
		
		world.setDelta(0);
		world.process();
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
		log.debug("loading - Json AI: {}", source);

		try
		{
			AiDescriptor descriptor = json.fromJson(AiDescriptor.class, source);
			
			aiUnmarshaller.setAi(ai);
			ai = aiUnmarshaller.unmarshal(descriptor);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		log.debug("deserialized - AI: {}", ai);
	}


	public String getAiAsJsonString()
	{
		String result = json.prettyPrint(aiMarshaller.marshall(ai));

		log.debug("serialized - Json AI: {}", result);

		return result;
	}


	public void loadWorldFromJsonString(String source)
	{
		log.debug("loading - Json World: {}", source);

		try
		{
			WorldChangeDescriptor descriptor = json.fromJson(WorldChangeDescriptor.class, source);
			worldUnmarshaller.setWorld(world);
			world = worldUnmarshaller.unmarshal(descriptor);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		log.debug("deserialized - World: {}", world);
		
		world.setDelta(0);
		world.process();
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
