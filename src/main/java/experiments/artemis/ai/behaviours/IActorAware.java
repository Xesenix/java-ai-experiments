package experiments.artemis.ai.behaviours;

import com.artemis.Entity;

public interface IActorAware
{
	/**
	 * Actor added to system.
	 * 
	 * @param actor
	 */
	void actorAdded(Entity actor);
	
	
	/**
	 * Actor removed from system.
	 * 
	 * @param actor
	 */
	void actorRemoved(Entity actor);
	
	
	/**
	 * Sets current running context to actor.
	 * 
	 * @param actor
	 */
	void setActor(Entity actor);
}
