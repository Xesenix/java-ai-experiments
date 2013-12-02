
package experiments.artemis.ai.behaviours;

import java.util.List;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.utils.Bag;


public class TaskSelector implements IBehavior
{
	private IBehavior behaviors[];
	
	
	private transient Bag<Integer> indexForEntity = new Bag<Integer>();


	public ITask chooseTask(World world, Entity e)
	{
		ITask task;
		
		for (int i = getIndexForEntity(world, e); i < behaviors.length; i++)
		{
			task = behaviors[i].chooseTask(world, e);
			
			if (task != null && !task.isCompleted(world, e))
			{
				indexForEntity.set(e.getId(), i);
				return task;
			}
		}

		return null;
	}


	public boolean isRunning(World world, Entity e)
	{
		for (int i = getIndexForEntity(world, e); i < behaviors.length; i++)
		{
			if (behaviors[i].isRunning(world, e))
			{
				return true;
			}
		}
		
		return false;
	}


	public void reset(World world, Entity e)
	{
		if (!isRunning(world, e))
		{
			indexForEntity.set(e.getId(), 0);
			
			for (int i = 0; i < behaviors.length; i++)
			{
				behaviors[i].reset(world, e);
			}
		}
	}
	
	
	private int getIndexForEntity(World world, Entity e)
	{
		Integer index = indexForEntity.get(e.getId());
		
		if (index == null)
		{
			index = 0;
			indexForEntity.set(e.getId(), index);
		}
		
		return index;
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
