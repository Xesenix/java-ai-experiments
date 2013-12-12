package experiments.artemis.world;

import java.util.UUID;

import ai.world.IWorld;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.World;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;


@Singleton
public class EntityUnmarshaller
{
	@Inject
	private transient Injector injector;


	private World world;


	public void setWorld(World world)
	{
		this.world = world;
	}
	
	
	public World getWorld()
	{
		if (world == null)
		{
			world = injector.getInstance(World.class);
			world.initialize();
		}
		
		return world;
	}


	/**
	 * Deserializes model to entity
	 * 
	 * @param descriptor
	 * @return
	 */
	public Entity unmarshal(EntityDescriptor descriptor)
	{
		World world = getWorld();
		
		Entity entity = world.getEntity(Integer.parseInt(descriptor.id));
		
		if (entity == null)
		{
			entity = world.createEntity(UUID.fromString(descriptor.uuid));
		}
		
		
		for (Component component : descriptor.components)
		{
			entity.addComponent(component);
		}
		
		return entity;
	}
}