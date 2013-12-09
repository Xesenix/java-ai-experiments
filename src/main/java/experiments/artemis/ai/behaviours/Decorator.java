
package experiments.artemis.ai.behaviours;

import com.artemis.Entity;
import com.artemis.World;


public class Decorator implements IBehavior
{
	private IBehavior behavior;


	public Decorator(IBehavior behavior)
	{
		this.behavior = behavior;
	}


	public void run(World world, Entity e)
	{
		behavior.run(world, e);
	}


	public IBehavior getBehavior()
	{
		return behavior;
	}


	public void setBehavior(IBehavior task)
	{
		this.behavior = task;
	}


	public boolean isReady(World world, Entity e)
	{
		return behavior.isReady(world, e);
	}


	public boolean isRunning(World world, Entity e)
	{
		return behavior.isRunning(world, e);
	}


	public boolean isSuccess(World world, Entity e)
	{
		return behavior.isSuccess(world, e);
	}


	public boolean isCompleted(World world, Entity e)
	{
		return behavior.isCompleted(world, e);
	}


	public void reset(World world, Entity e)
	{
		behavior.reset(world, e);
	}


	public String toString()
	{
		return String.format("[%s@%x, {behavior: %s}]", getClass().getSimpleName(), hashCode(), behavior);
	}
}
