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
		WorldDescriptor descriptor = (WorldDescriptor) injector.getInstance(IWorldDescriptor.class);
		EntityMarshaller entityMarshaller = (EntityMarshaller) injector.getInstance(EntityMarshaller.class);
		
		ImmutableBag<Entity> serializable = world.getManager(GroupManager.class).getEntities("serializable");
		
		ArrayList<EntityDescriptor> entities = new ArrayList<EntityDescriptor>();
		
		for (Entity entity : serializable)
		{
			entities.add(entityMarshaller.marshall(entity));
		}
		
		descriptor.entities = entities;
		
		return descriptor;
	}
}
