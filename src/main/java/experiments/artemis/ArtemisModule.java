
package experiments.artemis;

import ai.AI;
import ai.actions.MoveTo;
import ai.actors.NPC;
import ai.world.IPosition;
import ai.world.IWorld;
import ai.world.IWorldDescriptor;
import ai.world.World;
import ai.world.World.WorldDescriptor;
import ai.world2d.Position;

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
		bind(IPosition.class).to(Position.class);
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
