package experiments.artemis.world;

import java.util.ArrayList;

import ai.world.IWorldObjectDescriptor;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.utils.Bag;
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
		
		// describing entity
		descriptor.id = String.format("%d", entity.getId());
		descriptor.uuid = entity.getUuid().toString();
		
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
