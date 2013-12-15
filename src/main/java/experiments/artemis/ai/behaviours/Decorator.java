
package experiments.artemis.ai.behaviours;

import javax.xml.bind.annotation.XmlAnyElement;

import com.artemis.Entity;


class Decorator implements IBehavior
{
	private IBehavior behavior;


	protected transient Entity actor;
	
	
	public Decorator()
	{
	}


	public Decorator(IBehavior behavior)
	{
		this.setBehavior(behavior);
	}


	@XmlAnyElement(lax = true)
	public IBehavior getBehavior()
	{
		return behavior;
	}


	public void setBehavior(IBehavior behavior)
	{
		this.behavior = behavior;
	}


	public void run()
	{
		behavior.run();
	}


	public void reset()
	{
		behavior.reset();
	}


	public boolean isReady()
	{
		return behavior.isReady();
	}


	public boolean isRunning()
	{
		return behavior.isRunning();
	}


	public boolean isSuccess()
	{
		return behavior.isSuccess();
	}


	public boolean isCompleted()
	{
		return behavior.isCompleted();
	}


	public void setActor(Entity actor)
	{
		this.actor = actor;
		behavior.setActor(actor);
	}


	public void actorAdded(Entity entity)
	{
		behavior.actorAdded(entity);
	}


	public void actorRemoved(Entity entity)
	{
		behavior.actorRemoved(entity);
	}


	public String toString()
	{
		return String.format("[%s@%x, {behavior: %s}]", getClass().getSimpleName(), hashCode(), behavior);
	}
}
