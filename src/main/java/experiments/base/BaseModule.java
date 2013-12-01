
package experiments.base;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import ai.AI;
import ai.actions.MoveTo;
import ai.actors.NPC;
import ai.behaviour.Scene;
import ai.world.IWorld;
import ai.world.IWorldDescriptor;
import ai.world.World;
import ai.world.World.WorldDescriptor;
import ai.world.navigation.IPosition;
import ai.world.navigation.PositionXY;
import ai.world.navigation.Target;

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
		bind(IPosition.class).to(PositionXY.class);

		try
		{
			bind(JAXBContext.class).toInstance(JAXBContext.newInstance(MoveTo.class,
				AI.class,
				World.WorldDescriptor.class,
				World.WorldObjectDescriptor.class,
				Target.class,
				PositionXY.class,
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
