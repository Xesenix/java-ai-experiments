package experiments.artemis.world;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;


@Singleton
public class EntityUnmarshaller
{
	@Inject
	private Injector injector;


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
	public Entity unmarshal(EntityChangeDescriptor descriptor)
	{
		World world = getWorld();
		
		Entity entity = world.getEntity(Integer.parseInt(descriptor.id));
		
		if (entity == null)
		{
			entity = world.createEntity();
			entity.addToWorld();
		}
		
		// groups
		
		GroupManager groupManager = world.getManager(GroupManager.class);
		
		groupManager.removeFromAllGroups(entity);
		
		for (String group : descriptor.groups)
		{
			groupManager.add(entity, group);
		}
		
		// components
		
		for (Component component : descriptor.components)
		{
			entity.addComponent(component);
		}
		
		if (descriptor.removeComponents != null)
		{
			for (Component component : descriptor.removeComponents)
			{
				entity.removeComponent(component);
			}
		}
		
		entity.changedInWorld();
		
		return entity;
	}
}