
package experiment;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import experiment.ai.AIEnviroment;
import experiment.ai.actions.Move;
import experiment.ai.actors.NPC;
import experiment.world.Position;

public class BaseExperiment implements IExperimentManager
{
	private static final Logger log = LoggerFactory.getLogger(BaseExperiment.class);


	@Inject
	protected IExperimentView view;


	@Inject
	protected AIEnviroment enviroment;


	@Inject
	private JAXBContext jaxbContext;


	public void initialize()
	{
		Move move;
		
		move = enviroment.creatMoveAction();
		move.setTarget(new Position(2f, 3f));
		
		move = enviroment.creatMoveAction();
		move.setTarget(new Position(5f, 3f));
		
		enviroment.creatMoveAction();
		
		NPC npc;
		
		npc = enviroment.creatNpcActor();
		npc.setName("Alistar");
		
		npc = enviroment.creatNpcActor();
		npc.setName("Baldarok");
		
		npc = enviroment.creatNpcActor();
		npc.setName("Ceron");
		

		log.debug("initialized: {}", enviroment);
	}


	public void start()
	{
		log.debug("starting AI");
	}


	public void loadFromXmlString(String source)
	{
		log.debug("loading: {}", source);
		
		StringReader xmlReader = new StringReader(source);
		
		try
		{
			enviroment = (AIEnviroment) jaxbContext.createUnmarshaller().unmarshal(xmlReader);
		}
		catch (JAXBException e)
		{
			e.printStackTrace();
		}
		
		log.debug("deserialized: {}", enviroment);
	}


	public String getAsXmlString()
	{
		StringWriter xmlWriter = new StringWriter();

		Marshaller jaxbMarshaller;
		try
		{
			jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(enviroment, xmlWriter);
		}
		catch (JAXBException e)
		{
			e.printStackTrace();
		}
		
		log.debug("xml: {}", xmlWriter.toString());

		return xmlWriter.toString();
	}

}
