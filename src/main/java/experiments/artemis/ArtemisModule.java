
package experiments.artemis;

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
		bind(IPosition.class).to(PositionXY.class);
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
