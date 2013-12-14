package experiments.artemis.world;

import java.util.ArrayList;

import ai.world.IWorldObjectDescriptor;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

@Singleton
public class EntityMarshaller
{
	@Inject
	private transient Injector injector;


	/**
	 * Converts world implementation to serializable model.
	 * 
	 * @param world
	 * @return world model object
	 */
	public EntityDescriptor marshall(Entity entity)
	{
		EntityDescriptor descriptor = (EntityDescriptor) injector.getInstance(IWorldObjectDescriptor.class);
		World world = entity.getWorld();
		
		// describing entity
		descriptor.id = String.format("%d", entity.getId());
		
		// describing entity groups
		GroupManager groupManager = world.getManager(GroupManager.class);
		ImmutableBag<String> entityGroups = groupManager.getGroups(entity);
		
		ArrayList<String> groups = new ArrayList<String>();
		
		for (String group : entityGroups)
		{
			groups.add(group);
		}
		
		descriptor.groups = groups;
		
		// describing entity components 
		Bag<Component> componentsBag = new Bag<Component>();
		entity.getComponents(componentsBag);
		
		ArrayList<Component> components = new ArrayList<Component>();
		
		for (Component component : componentsBag)
		{
			components.add(component);
		}
		
		descriptor.components = components;
		
		return descriptor;
	}
}
