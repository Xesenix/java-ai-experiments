
package experiments.artemis.components.tasks;

import java.util.Collection;

import ai.behaviour.IGoal;

import com.artemis.Entity;
import com.artemis.World;

import experiments.artemis.ai.behaviours.ITask;
import experiments.artemis.componentsbehaviours.BehaviorComponent;


public class TaskComponent extends BehaviorComponent implements ITask
{
	private ITask task;


	private IGoal currentGoal;


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


	public void setCurrentGoal(IGoal goal)
	{
		this.currentGoal = goal;
	}


	public IGoal getCurrentGoal()
	{
		return currentGoal;
	}


	public String toString()
	{
		return String.format("[%s@%x, {task: %s}]", getClass().getSimpleName(), hashCode(), task);
	}
}
