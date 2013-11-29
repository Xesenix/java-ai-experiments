
package ai.world;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.inject.Inject;
import com.google.inject.Injector;

import ai.actors.IActor;
import ai.actors.NPC;
import ai.world.World.WorldDescriptor;




public class World implements IWorld
{
	@Inject
	private transient Injector injector;


	private List<IActor> actors = new ArrayList<IActor>();


	private Map<String, IWorldObject> worldObjects = new HashMap<String, IWorldObject>();


	public Map<String, IWorldObject> getWorldObjects()
	{
		return worldObjects;
	}


	public void setWorldObjects(Map<String, IWorldObject> worldObjects)
	{
		this.worldObjects = worldObjects;
	}


	public List<IActor> getActors()
	{
		return actors;
	}


	public void setActors(List<IActor> actors)
	{
		this.actors = actors;
	}


	public NPC createNpcActor()
	{
		NPC npc = injector.getInstance(NPC.class);

		actors.add(npc);

		npc.setInstanceId(String.format("npc%02d", actors.indexOf(npc)));

		return npc;
	}


	public Target createTarget(String name)
	{
		Target target = injector.getInstance(Target.class);

		worldObjects.put(name, target);

		return target;
	}


	public String toString()
	{
		return String.format("{actors: %s; objects: %s;}", actors, worldObjects);
	}
	
	
	@XmlRootElement(name = "world")
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class WorldDescriptor
	{
		@XmlElementWrapper(name = "actors")
		@XmlAnyElement(lax = true)
		public ArrayList<IActor> actors = new ArrayList<IActor>();


		@XmlElementWrapper(name = "objects")
		@XmlAnyElement(lax = true)
		public ArrayList<WorldObjectDescriptor> objects = new ArrayList<WorldObjectDescriptor>();
	}


	public static class WorldMarshaller
	{
		@Inject
		private transient Injector injector;


		public WorldDescriptor marshall(IWorld world)
		{
			WorldDescriptor descriptor = injector.getInstance(WorldDescriptor.class);

			descriptor.actors = new ArrayList<IActor>(world.getActors());
			
			WorldObjectMarshaller objectMarshaller = injector.getInstance(WorldObjectMarshaller.class);
			
			for (Map.Entry<String, IWorldObject> entry : world.getWorldObjects().entrySet())
			{
				descriptor.objects.add(objectMarshaller.marshall(entry));
			}

			return descriptor;
		}
	}


	public static class WorldUnmarshaller
	{
		@Inject
		private transient Injector injector;


		public World unmarshal(WorldDescriptor descriptor)
		{
			World world = injector.getInstance(World.class);

			world.setActors(descriptor.actors);
			
			WorldObjectUnmarshaller entryUnmrashaller = injector.getInstance(WorldObjectUnmarshaller.class);
			
			HashMap<String, IWorldObject> worldObjects = new HashMap<String, IWorldObject>();
			
			for (WorldObjectDescriptor objectDescriptor : descriptor.objects)
			{
				Map.Entry<String, IWorldObject> entry = entryUnmrashaller.unmarshall(objectDescriptor);
				worldObjects.put(entry.getKey(), entry.getValue());
			}
			
			world.setWorldObjects(worldObjects);

			return world;
		}
	}
	

	@XmlRootElement(name = "object")
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class WorldObjectDescriptor
	{
		@XmlAttribute(name = "name")
		public String name;


		@XmlAnyElement(lax = true)
		public IWorldObject object;
	}
	
	
	public static class WorldObjectMarshaller
	{
		@Inject
		private transient Injector injector;


		public WorldObjectDescriptor marshall(Map.Entry<String, IWorldObject> entry)
		{
			WorldObjectDescriptor descriptor = new WorldObjectDescriptor();

			descriptor.name = entry.getKey();
			descriptor.object = entry.getValue();

			return descriptor;
		}
	}
	
	
	public static class WorldObjectUnmarshaller
	{
		@Inject
		private transient Injector injector;


		public Map.Entry<String, IWorldObject> unmarshall(WorldObjectDescriptor descriptor)
		{
			Map.Entry<String, IWorldObject> entry = new AbstractMap.SimpleEntry<String, IWorldObject>(descriptor.name, descriptor.object);

			return entry;
		}
	}
}
