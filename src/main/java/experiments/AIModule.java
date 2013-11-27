
package experiments;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import ai.AIEnviromentFactory;
import ai.actions.Move;
import ai.actors.NPC;
import ai.world.Position;

import com.google.inject.AbstractModule;


public class AIModule extends AbstractModule
{
	public void configure()
	{
		bindActions();
		bindActors();
		bindXmlEnviroment();
	}


	private void bindXmlEnviroment()
	{
		bind(AIEnviromentFactory.class);
		try
		{
			bind(JAXBContext.class).toInstance(JAXBContext.newInstance(Move.class, AIEnviromentFactory.class, Position.class, NPC.class));
		}
		catch (JAXBException e)
		{
			e.printStackTrace();
		}
	}


	private void bindActions()
	{
		bind(Move.class);
	}


	private void bindActors()
	{
		bind(NPC.class);
	}
}
