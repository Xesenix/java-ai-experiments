
package experiments.artemis.components;

import java.util.Collection;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.World;

import experiments.artemis.ai.behaviours.IGoal;
import experiments.artemis.ai.behaviours.ITask;


public class TaskComponent extends Component implements ITask
{
	private ITask task;


	public TaskComponent(ITask task)
	{
		setTask(task);
	}


	public ITask getTask()
	{
		return task;
	}


	public void setTask(ITask task)
	{
		this.task = task;
	}


	public ITask chooseTask(World world, Entity e)
	{
		return task.chooseTask(world, e);
	}


	public boolean isRunning(World world, Entity e)
	{
		return task.isRunning(world, e);
	}


	public void reset(World world, Entity e)
	{
		task.reset(world, e);
	}


	public IGoal[] getGoals()
	{
		return this.task.getGoals();
	}


	public void setGoals(Collection<IGoal> goals)
	{
		task.setGoals(goals);
	}


	public boolean isSuccess(World world, Entity e)
	{
		return this.task.isSuccess(world, e);
	}


	public void setCompleted(World world, Entity e, boolean completed)
	{
		task.setCompleted(world, e, completed);
	}


	public boolean isCompleted(World world, Entity e)
	{
		return task.isCompleted(world, e);
	}


	public String toString()
	{
		return String.format("[%s@%x {task: %s}]", getClass().getSimpleName(), hashCode(), task);
	}
}
