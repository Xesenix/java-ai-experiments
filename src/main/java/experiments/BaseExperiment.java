
package experiments;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.AI;
import ai.actions.Move;
import ai.actors.NPC;
import ai.behaviour.IBehaviour;
import ai.world.IWorld;
import ai.world.Position;
import ai.world.World;
import ai.world.World.WorldDescriptor;

import com.badlogic.gdx.utils.Json;
import com.google.inject.Inject;
import com.google.inject.Injector;


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
		Move move;

		move = ai.createMoveAction();
		move.setTarget(new Position(2f, 3f));

		move = ai.createMoveAction();
		move.setTarget(new Position(5f, 3f));

		ai.createMoveAction();

		NPC npcA = world.createNpcActor();
		npcA.setName("Alistar");

		NPC npcB = world.createNpcActor();
		npcB.setName("Baldarok");

		NPC npcC = world.createNpcActor();
		npcC.setName("Ceron");

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
			// TODO enable loading world class based on source
			world = json.fromJson(World.class, source);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		log.debug("deserialized - World: {}", world);
	}


	public String getWorldAsJsonString()
	{
		String result = json.prettyPrint(world);

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
