package experiments.artemis.ai;

import java.util.HashMap;
import java.util.Map;

import experiments.artemis.ai.behaviours.IBehavior;


public class AI
{
	private Map<String, IBehavior> behaviors = new HashMap<String, IBehavior>();
	
	
	public void setBehavior(String key, IBehavior behavior)
	{
		behaviors.put(key, behavior);
	}
	
	
	public Map<String, IBehavior> getBehaviors()
	{
		return behaviors;
	}
	
	
	public void setBehaviors(Map<String, IBehavior> behaviors)
	{
		this.behaviors = behaviors;
	}
}
