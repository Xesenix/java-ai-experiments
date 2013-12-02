
package experiments.base;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import ai.AI;
import ai.actions.MoveTo;
import ai.actors.NPC;
import ai.behaviour.Scene;
import ai.world.IPosition;
import ai.world.IWorld;
import ai.world.IWorldDescriptor;
import ai.world.World;
import ai.world.World.WorldDescriptor;
import ai.world.navigation.Target;
import ai.world2d.Position;

import com.google.inject.AbstractModule;


public class BaseModule extends AbstractModule
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
		bind(IWorldDescriptor.class).to(WorldDescriptor.class);
		bind(IPosition.class).to(Position.class);

		try
		{
			bind(JAXBContext.class).toInstance(JAXBContext.newInstance(MoveTo.class,
				AI.class,
				World.WorldDescriptor.class,
				World.WorldObjectDescriptor.class,
				Target.class,
				Position.class,
				NPC.class,
				Scene.class));
		}
		catch (JAXBException e)
		{
			e.printStackTrace();
		}
	}


	private void bindActions()
	{
		bind(MoveTo.class);
	}


	private void bindActors()
	{
		bind(NPC.class);
	}
}
