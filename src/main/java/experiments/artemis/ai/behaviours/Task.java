
package experiments.artemis.ai.behaviours;

import java.util.Collection;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.utils.Bag;

import experiments.artemis.components.BehaviorComponent;


public class Task implements ITask
{
	private Bag<Boolean> taskCompletedByEntity = new Bag<Boolean>();


	public Task()
	{
	}


	public IGoal[] getGoals()
	{
		return new IGoal[0];
	}


	public void setGoals(Collection<IGoal> goals)
	{
	}


	public final ITask chooseTask(World world, Entity e)
	{
		return this;
	}


	public boolean finished(World world, Entity e)
	{
		if (isCompleted(world, e))
		{
			return true;
		}

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


	public boolean isCompleted(World world, Entity e)
	{
		Boolean result = taskCompletedByEntity.get(e.getId());
		
		if (result == null)
		{
			return false;
		}
		
		return result;
	}


	public void setCompleted(World world, Entity e, boolean completed)
	{
		taskCompletedByEntity.set(e.getId(), completed);
	}


	public boolean isRunning(World world, Entity e)
	{
		return !isCompleted(world, e);
	}


	public void reset(World world, Entity e)
	{
		setCompleted(world, e, false);
	}


	public String toString()
	{
		return String.format("[%s@%x]", getClass().getSimpleName(), hashCode());
	}
}
