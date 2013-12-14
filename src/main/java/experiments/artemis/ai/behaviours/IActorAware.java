package experiments.artemis.ai.behaviours;

import com.artemis.Entity;

public interface IActorAware extends IContextAware
{
	void actorAdded(Entity entity);
	
	
	void actorRemoved(Entity entity);
}
