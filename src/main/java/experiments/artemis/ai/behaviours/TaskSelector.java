
package experiments.artemis.ai.behaviours;

import java.util.List;

import com.artemis.Entity;
import com.artemis.World;


public class TaskSelector implements IBehavior
{
	private IBehavior behaviors[];


	public ITask chooseTask(World world, Entity e)
	{
		ITask task;

		for (int i = 0; i < behaviors.length; i++)
		{
			task = behaviors[i].chooseTask(world, e);

			if (task != null && !task.finished(world, e))
			{
				return task;
			}
		}

		return null;
	}


	public void setCompleted(World world, Entity e, boolean completed)
	{
		IBehavior behavior;

		for (int i = 0; i < behaviors.length; i++)
		{
			behavior = behaviors[i];

			if (!behavior.isRunning())
			{
				behavior.setCompleted(world, e, completed);
			}
		}
	}


	public boolean isRunning()
	{
		IBehavior behavior;
		
		for (int i = 0; i < behaviors.length; i++)
		{
			behavior = behaviors[i];

			if (behavior.isRunning())
			{
				return true;
			}
		}
		
		return false;
	}


	public IBehavior[] getBehaviours()
	{
		return behaviors;
	}


	public void setBehaviours(IBehavior... behaviours)
	{
		this.behaviors = behaviours;
	}


	public void setBehaviours(List<IBehavior> behavioursList)
	{
		this.behaviors = behavioursList.toArray(new IBehavior[0]);
	}


	public String toString()
	{
		return String.format("[%s@%x, {behaviors: %s}]", getClass().getSimpleName(), hashCode(), behaviors);
	}
}
