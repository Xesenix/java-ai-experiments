
package experiments.artemis.components.tasks;

import java.util.Collection;

import com.artemis.Entity;
import com.artemis.World;

import experiments.artemis.ai.behaviours.IGoal;
import experiments.artemis.ai.behaviours.ITask;
import experiments.artemis.components.behaviours.BehaviorComponent;


public class TaskComponent extends BehaviorComponent implements ITask
{
	private ITask task;


	private int currentGoalIndex = -1;


	public TaskComponent()
	{
	}


	public TaskComponent(ITask task)
	{
		super(task);

		this.task = task;
	}


	public boolean isRunning()
	{
		return task.isRunning();
	}


	public IGoal[] getGoals()
	{
		return task.getGoals();
	}


	public void setGoals(Collection<IGoal> goals)
	{
		task.setGoals(goals);
	}


	public final ITask chooseTask(World world, Entity e)
	{
		return this;
	}


	public boolean goalsAchived(World world, Entity e)
	{
		IGoal[] goals = getGoals();

		for (int i = 0; i < goals.length; i++)
		{
			if (!goals[i].achived(world, e))
			{
				return false;
			}
		}

		return true;
	}


	public String toString()
	{
		return String.format("[%s@%x, {task: %s}]", getClass().getSimpleName(), hashCode(), task);
	}
}
