
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


	public ITask chooseTask(World world, Entity e)
	{
		return task.chooseTask(world, e);
	}


	public ITask getTask()
	{
		return task;
	}


	public void setTask(ITask task)
	{
		this.task = task;
	}


	public void setCompleted(World world, Entity e, boolean ready)
	{
		task.setCompleted(world, e, ready);
	}


	public boolean isRunning(World world, Entity e)
	{
		return task.isRunning(world, e);
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
