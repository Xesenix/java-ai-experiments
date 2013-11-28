
package experiments;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import ai.AI;
import ai.actions.Move;
import ai.actors.NPC;
import ai.behaviour.Scene;
import ai.world.IWorld;
import ai.world.Position;
import ai.world.World;

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
		bind(AI.class);
		bind(IWorld.class).to(World.class);
		
		try
		{
			bind(JAXBContext.class).toInstance(JAXBContext.newInstance(
				Move.class,
				AI.class,
				World.class,
				Position.class,
				NPC.class,
				Scene.class
			));
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
