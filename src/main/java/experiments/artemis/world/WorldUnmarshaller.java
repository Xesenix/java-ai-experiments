
package experiments.artemis.world;

import java.util.HashMap;
import java.util.Map;

import ai.world.IWorld;

import com.artemis.Entity;
import com.artemis.World;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;


@Singleton
public class WorldUnmarshaller
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
	 * Deserializes model to world object
	 * 
	 * @param descriptor
	 * @return
	 */
	public World unmarshal(WorldDescriptor descriptor)
	{
		World world = getWorld();
		
		EntityUnmarshaller entityUnmrashaller = injector.getInstance(EntityUnmarshaller.class);
		
		entityUnmrashaller.setWorld(world);

		for (EntityDescriptor entityDescriptor : descriptor.entities)
		{
			Entity entity = entityUnmrashaller.unmarshal(entityDescriptor);
			
			world.addEntity(entity);
		}

		return world;
	}
}
