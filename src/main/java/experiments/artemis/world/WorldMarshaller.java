package experiments.artemis.world;

import java.util.ArrayList;

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
	public WorldChangeDescriptor marshall(World world)
	{
		WorldChangeDescriptor descriptor = (WorldChangeDescriptor) injector.getInstance(WorldChangeDescriptor.class);
		
		// entities
		EntityMarshaller entityMarshaller = (EntityMarshaller) injector.getInstance(EntityMarshaller.class);
		ImmutableBag<Entity> serializable = world.getManager(GroupManager.class).getEntities("serializable");
		
		ArrayList<EntityChangeDescriptor> entities = new ArrayList<EntityChangeDescriptor>();
		
		for (Entity entity : serializable)
		{
			entities.add(entityMarshaller.marshall(entity));
		}
		
		descriptor.entities = entities;
		
		return descriptor;
	}
}
