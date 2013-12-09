
package experiments.artemis.ai.behaviours;

import com.artemis.Entity;
import com.artemis.World;


public class Decorator implements IBehavior
{
	private ITask task;


	public Decorator(ITask task)
	{
		this.task = task;
	}


	public boolean run(World world, Entity e)
	{
		return task.run(world, e);
	}


	public ITask getTask()
	{
		return task;
	}


	public void setTask(ITask task)
	{
		this.task = task;
	}


	public boolean isReady(World world, Entity e)
	{
		return task.isReady(world, e);
	}


	public boolean isRunning(World world, Entity e)
	{
		return task.isRunning(world, e);
	}


	public boolean isSuccess(World world, Entity e)
	{
		return task.isSuccess(world, e);
	}


	public boolean isCompleted(World world, Entity e)
	{
		return task.isCompleted(world, e);
	}


	public void reset(World world, Entity e)
	{
		task.reset(world, e);
	}


	public String toString()
	{
		return String.format("[%s@%x, {task: %s}]", getClass().getSimpleName(), hashCode(), task);
	}
}
