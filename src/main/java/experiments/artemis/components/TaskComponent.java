
package experiments.artemis.components;

import java.util.Collection;

import ai.behaviour.IGoal;
import ai.behaviour.ITask;

import com.artemis.Entity;
import com.artemis.World;


public class TaskComponent extends BehaviourComponent implements ITask
{
	private TaskComponent task;


	private IGoal currentGoal;


	public TaskComponent()
	{
	}


	public TaskComponent(TaskComponent task)
	{
		super(task);

		this.task = task;
	}


	public boolean isRunning()
	{
		return task.isRunning();
	}


	@Override
	public IGoal[] getGoals()
	{
		return task.getGoals();
	}


	@Override
	public void setGoals(Collection<IGoal> goals)
	{
		task.setGoals(goals);
	}


	public TaskComponent chooseTask(World world, Entity e)
	{
		return this;
	}


	public void setCurrentGoal(IGoal goal)
	{
		this.currentGoal = goal;

	}


	public IGoal getCurrentGoal()
	{
		return currentGoal;
	}
}
