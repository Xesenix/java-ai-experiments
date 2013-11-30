
package experiments.base;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.AI;
import ai.actions.MoveTo;
import ai.actors.NPC;
import ai.behaviour.IBehaviour;
import ai.world.IWorld;
import ai.world.World;
import ai.world.navigation.PositionXY;
import ai.world.navigation.Target;

import com.badlogic.gdx.utils.Json;
import com.google.inject.Inject;
import com.google.inject.Injector;

import experiments.IExperimentManager;
import experiments.IExperimentView;


public class BaseExperiment implements IExperimentManager
{
	private static final Logger log = LoggerFactory.getLogger(BaseExperiment.class);


	@Inject
	protected Injector injector;


	@Inject
	private World.WorldMarshaller worldMarshaller;


	@Inject
	private World.WorldUnmarshaller worldUnmarshaller;


	@Inject
	protected IExperimentView view;


	@Inject
	protected AI ai;


	@Inject
	protected IWorld world;


	@Inject
	private JAXBContext jaxbContext;


	@Inject
	private Json json;


	private boolean isAiRunning = false;


	public void initialize()
	{
		MoveTo move;

		move = world.createMoveAction();
		move.setTarget(new PositionXY(2f, 3f));

		move = world.createMoveAction();
		move.setTarget(new PositionXY(5f, 3f));

		world.createMoveAction();

		NPC npcA = world.createNpcActor();
		npcA.setName("Alistar");

		NPC npcB = world.createNpcActor();
		npcB.setName("Baldarok");

		NPC npcC = world.createNpcActor();
		npcC.setName("Ceron");
		
		Target targetA = world.createTarget("strategyATarget");
		targetA.setPosition(new PositionXY(3f, 3f));

		Target targetB = world.createTarget("strategyBTarget");

		IBehaviour behavior;

		behavior = ai.createSceneBehaviour();

		behavior.addActor(npcA);
		behavior.addActor(npcB);
		behavior.addActor(npcC);

		log.debug("initialized: {}", ai);
	}


	public void startAi()
	{
		log.debug("starting AI");

		isAiRunning = true;
	}


	public void stopAi()
	{
		log.debug("stopping AI");

		isAiRunning = false;
	}


	public void doAiLogicStep()
	{
		if (isAiRunning)
		{
			log.debug("running AI");
		}
	}


	public void loadAiFromXmlString(String source)
	{
		log.debug("loading - XML AI: {}", source);

		StringReader xmlReader = new StringReader(source);

		try
		{
			ai = (AI) jaxbContext.createUnmarshaller().unmarshal(xmlReader);
		}
		catch (JAXBException e)
		{
			e.printStackTrace();
		}

		log.debug("deserialized - AI: {}", ai);
	}


	public String getAiAsXmlString()
	{
		StringWriter xmlWriter = new StringWriter();

		Marshaller jaxbMarshaller;
		try
		{
			jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(ai, xmlWriter);
		}
		catch (JAXBException e)
		{
			e.printStackTrace();
		}

		String result = xmlWriter.toString();

		log.debug("serialized - XML AI: {}", result);

		return result;
	}


	public void loadWorldFromXmlString(String source)
	{
		log.debug("loading - XML World: {}", source);

		StringReader xmlReader = new StringReader(source);

		try
		{
			World.WorldDescriptor descriptor = (World.WorldDescriptor) jaxbContext.createUnmarshaller().unmarshal(xmlReader);
			
			world = worldUnmarshaller.unmarshal(descriptor);
		}
		catch (JAXBException e)
		{
			e.printStackTrace();
		}

		log.debug("deserialized - World: {}", world);
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
			ai = json.fromJson(AI.class, source);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		log.debug("deserialized - AI: {}", ai);
	}


	public String getAiAsJsonString()
	{
		String result = json.prettyPrint(ai);

		log.debug("serialized - Json AI: {}", result);

		return result;
	}


	public void loadWorldFromJsonString(String source)
	{
		log.debug("loading - Json World: {}", source);

		try
		{
			World.WorldDescriptor descriptor = json.fromJson(World.WorldDescriptor.class, source);
			
			world = worldUnmarshaller.unmarshal(descriptor);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		log.debug("deserialized - World: {}", world);
	}


	public String getWorldAsJsonString()
	{
		String result = json.prettyPrint(worldMarshaller.marshall(world));

		log.debug("serialized - Json World: {}", result);

		return result;
	}
	
	
	public void saveXmlSchema()
	{
		try
		{
			jaxbContext.generateSchema(new SchemaOutputResolver() {
				public Result createOutput(String namespaceUri, String suggestedFileName) throws IOException
				{
					File file = new File(suggestedFileName);
					StreamResult result = new StreamResult(file);
					result.setSystemId(file.toURI().toURL().toString());
					return result;
				}
			});
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
