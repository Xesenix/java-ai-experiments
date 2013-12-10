
package experiments.artemis.ai.behaviours;

import com.artemis.Entity;
import com.artemis.World;


public class Decorator implements IBehavior
{
	private IBehavior behavior;


	protected World world;


	protected Entity entity;


	public Decorator(IBehavior behavior)
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


	public void setContext(World world, Entity entity)
	{
		this.world = world;
		this.entity = entity;
		behavior.setContext(world, entity);
	}


	public IBehavior getBehavior()
	{
		return behavior;
	}


	public void setBehavior(IBehavior task)
	{
		this.behavior = task;
	}


	public String toString()
	{
		return String.format("[%s@%x, {behavior: %s}]", getClass().getSimpleName(), hashCode(), behavior);
	}
}
