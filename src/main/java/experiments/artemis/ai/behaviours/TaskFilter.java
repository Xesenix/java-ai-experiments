
package experiments.artemis.ai.behaviours;

import com.artemis.Entity;
import com.artemis.World;

import conditions.ICondition;
import experiments.artemis.components.behaviours.BehaviorComponent;


public class TaskFilter extends BehaviorComponent implements ICondition
{
	private ITask task;


	private transient World world;


	private transient Entity e;


	public ITask chooseTask(World world, Entity e)
	{
		setContext(world, e);

		if (this.isTrue())
		{
			return task.chooseTask(world, e);
		}

		return null;
	}


	private void setContext(World world, Entity e)
	{
		this.world = world;
		this.e = e;
	}


	public boolean isTrue()
	{
		return true;
	}


	public ITask getTask()
	{
		return task;
	}


	public void setTask(ITask task)
	{
		this.task = task;
	}


	public String toString()
	{
		return String.format("[%s@%x, {task: %s}]", getClass().getSimpleName(), hashCode(), task);
	}
}
