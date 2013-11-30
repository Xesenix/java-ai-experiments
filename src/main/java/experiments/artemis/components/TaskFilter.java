
package experiments.artemis.components;

import com.artemis.Entity;
import com.artemis.World;

import conditions.ICondition;


public class TaskFilter extends BehaviourComponent implements ICondition
{
	private TaskComponent task;


	private transient World world;


	private transient Entity e;


	public TaskComponent chooseTask(World world, Entity e)
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


	public TaskComponent getTask()
	{
		return task;
	}


	public void setTask(TaskComponent task)
	{
		this.task = task;
	}

}
