
package experiments.artemis;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import ai.AI;
import ai.actions.MoveTo;
import ai.actors.NPC;
import ai.world.IPosition;
import ai.world.IWorld;
import ai.world.IWorldDescriptor;
import ai.world.World;
import ai.world2d.Position;

import com.google.inject.AbstractModule;

import experiments.artemis.world.WorldDescriptor;


public class ArtemisModule extends AbstractModule
{
	public void configure()
	{
		bindActions();
		bindActors();
		bindEnviroment();
	}


	private void bindEnviroment()
	{
		bind(AI.class);
		bind(IWorld.class).to(World.class);
		bind(IWorldDescriptor.class).to(WorldDescriptor.class);
		bind(IPosition.class).to(Position.class);

		try
		{
			bind(JAXBContext.class).toInstance(JAXBContext.newInstance(WorldDescriptor.class));
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
