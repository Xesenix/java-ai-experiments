
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

import ai.actions.IAction;
import ai.actions.MoveTo;
import ai.actors.IActor;
import ai.actors.NPC;
import ai.world.navigation.Target;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;


public class World implements IWorld
{
	@Inject
	private transient Injector injector;


	private List<IActor> actors = new ArrayList<IActor>();


	private List<IAction> actions = new ArrayList<IAction>();


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


	public List<IAction> getActions()
	{
		return actions;
	}


	public void setActions(List<IAction> actions)
	{
		this.actions = actions;
	}


	public NPC createNpcActor()
	{
		NPC npc = injector.getInstance(NPC.class);

		actors.add(npc);

		npc.setInstanceId(String.format("npc%02d", actors.indexOf(npc)));

		return npc;
	}


	public MoveTo createMoveAction()
	{
		MoveTo move = injector.getInstance(MoveTo.class);

		actions.add(move);

		move.setInstanceId(String.format("move%02d", actions.indexOf(move)));

		return move;
	}


	public Target createTarget(String name)
	{
		Target target = injector.getInstance(Target.class);

		worldObjects.put(name, target);

		return target;
	}


	public String toString()
	{
		return String.format("{actors: %s; actions: %s; objects: %s;}", actors, actions, worldObjects);
	}


	@XmlRootElement(name = "world")
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class WorldDescriptor implements IWorldDescriptor
	{
		@XmlElementWrapper(name = "actors")
		@XmlAnyElement(lax = true)
		public ArrayList<IActor> actors = new ArrayList<IActor>();


		@XmlElementWrapper(name = "actions")
		@XmlAnyElement(lax = true)
		private ArrayList<IAction> actions = new ArrayList<IAction>();


		@XmlElementWrapper(name = "objects")
		@XmlAnyElement(lax = true)
		public ArrayList<WorldObjectDescriptor> objects = new ArrayList<WorldObjectDescriptor>();
	}

	@Singleton
	public static class WorldMarshaller
	{
		@Inject
		private transient Injector injector;


		/**
		 * Converts world implementation to serializable model.
		 * 
		 * @param world
		 * @return world model object
		 */
		public WorldDescriptor marshall(IWorld world)
		{
			WorldDescriptor descriptor = (WorldDescriptor) injector.getInstance(IWorldDescriptor.class);

			descriptor.actors = new ArrayList<IActor>(world.getActors());

			descriptor.actions = new ArrayList<IAction>(world.getActions());

			WorldObjectMarshaller objectMarshaller = injector.getInstance(WorldObjectMarshaller.class);

			for (Map.Entry<String, IWorldObject> entry : world.getWorldObjects().entrySet())
			{
				descriptor.objects.add(objectMarshaller.marshall(entry));
			}

			return descriptor;
		}
	}

	@Singleton
	public static class WorldUnmarshaller
	{
		@Inject
		private transient Injector injector;


		/**
		 * Deserializes model to world object
		 * 
		 * @param descriptor
		 * @return
		 */
		public IWorld unmarshal(WorldDescriptor descriptor)
		{
			IWorld world = injector.getInstance(IWorld.class);

			world.setActors(descriptor.actors);
			world.setActions(descriptor.actions);

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

	@Singleton
	public static class WorldObjectMarshaller
	{
		public WorldObjectDescriptor marshall(Map.Entry<String, IWorldObject> entry)
		{
			WorldObjectDescriptor descriptor = new WorldObjectDescriptor();

			descriptor.name = entry.getKey();
			descriptor.object = entry.getValue();

			return descriptor;
		}
	}

	@Singleton
	public static class WorldObjectUnmarshaller
	{
		public Map.Entry<String, IWorldObject> unmarshall(WorldObjectDescriptor descriptor)
		{
			Map.Entry<String, IWorldObject> entry = new AbstractMap.SimpleEntry<String, IWorldObject>(descriptor.name,
				descriptor.object);

			return entry;
		}
	}
}
