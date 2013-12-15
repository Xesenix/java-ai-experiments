
package experiments.artemis.ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.artemis.Entity;
import com.artemis.Manager;

import experiments.artemis.ai.behaviours.IBehavior;


public class AiManager extends Manager
{
	private Map<String, IBehavior> behaviors = new HashMap<String, IBehavior>();


	private transient List<Entity> actors = new ArrayList<Entity>();


	public void setBehavior(String key, IBehavior behavior)
	{
		behaviors.put(key, behavior);

		for (Entity actor : getActors())
		{
			behavior.actorAdded(actor);
			behavior.setActor(actor);
			behavior.reset();
		}
	}


	public Map<String, IBehavior> getBehaviors()
	{
		return behaviors;
	}


	public void setBehaviors(Map<String, IBehavior> behaviors)
	{
		this.behaviors = behaviors;

		for (Map.Entry<String, IBehavior> entry : getBehaviors().entrySet())
		{
			IBehavior behavior = entry.getValue();

			for (Entity actor : getActors())
			{
				behavior.actorAdded(actor);
				behavior.setActor(actor);
				behavior.reset();
			}
		}
	}


	private List<Entity> getActors()
	{
		return actors;
	}


	public void added(Entity entity)
	{
		if (!actors.contains(entity))
		{
			actors.add(entity);
		}

		for (Map.Entry<String, IBehavior> entry : getBehaviors().entrySet())
		{
			IBehavior behavior = entry.getValue();
			behavior.actorAdded(entity);
			behavior.setActor(entity);
			behavior.reset();
		}
	}


	public void changed(Entity e)
	{
	}


	public void deleted(Entity entity)
	{
		actors.remove(entity);

		for (Map.Entry<String, IBehavior> entry : getBehaviors().entrySet())
		{
			entry.getValue().actorRemoved(entity);
		}
	}


	public void disabled(Entity e)
	{
	}


	public void enabled(Entity e)
	{
	}
}
