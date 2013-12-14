package experiments.artemis.ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.artemis.Entity;

import experiments.artemis.ai.behaviours.IBehavior;


public class AI
{
	private Map<String, IBehavior> behaviors = new HashMap<String, IBehavior>();
	
	
	private transient List<Entity> actors = new ArrayList<Entity>();
	
	
	public void setBehavior(String key, IBehavior behavior)
	{
		behaviors.put(key, behavior);
		
		for (Entity actor : getActors())
		{
			behavior.actorAdded(actor);
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
			}
		}
	}


	private List<Entity> getActors()
	{
		return actors;
	}


	public void addActor(Entity entity)
	{
		if (!actors.contains(entity))
		{
			actors.add(entity);
		}
		
		for (Map.Entry<String, IBehavior> entry : getBehaviors().entrySet())
		{
			entry.getValue().actorAdded(entity);
		}
	}


	public void removeActor(Entity entity)
	{
		actors.remove(entity);
		
		for (Map.Entry<String, IBehavior> entry : getBehaviors().entrySet())
		{
			entry.getValue().actorRemoved(entity);
		}
	}
}
