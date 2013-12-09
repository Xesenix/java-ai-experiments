package experiments.artemis.world;

import java.util.ArrayList;

import ai.world.IWorldDescriptor;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.utils.ImmutableBag;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

@Singleton
public class WorldMarshaller
{
	@Inject
	private transient Injector injector;


	/**
	 * Converts world implementation to serializable model.
	 * 
	 * @param world
	 * @return world model object
	 */
	public WorldDescriptor marshall(World world)
	{
		ImmutableBag<Entity> entities;
		WorldDescriptor descriptor = (WorldDescriptor) injector.getInstance(IWorldDescriptor.class);
		
		// serialize actors
		
		entities = world.getManager(GroupManager.class).getEntities("actors");
		
		ArrayList<Integer> actors = new ArrayList<Integer>();
		
		for (Entity entity : entities)
		{
			actors.add(entity.getId());
		}
		
		descriptor.actors = actors;
		
		// serialize targets
		
		entities = world.getManager(GroupManager.class).getEntities("targets");
		
		ArrayList<Integer> targets = new ArrayList<Integer>();
		
		for (Entity entity : entities)
		{
			targets.add(entity.getId());
		}
		
		descriptor.targets = targets;
		
		// TODO rest
		
		return descriptor;
	}
}
