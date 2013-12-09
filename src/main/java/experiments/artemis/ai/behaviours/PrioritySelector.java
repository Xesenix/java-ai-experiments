
package experiments.artemis.ai.behaviours;

import java.util.List;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.utils.Bag;


public class PrioritySelector implements IBehavior
{
	private IBehavior behaviors[];


	private transient Bag<Integer> indexForEntity = new Bag<Integer>();
	
	
	public PrioritySelector(IBehavior... behaviors)
	{
		setBehaviors(behaviors);
	}


	public void run(World world, Entity e)
	{
		for (int i = 0; i < behaviors.length; i++)
		{
			indexForEntity.set(e.getId(), i);
			
			behaviors[i].run(world, e);
			
			if (behaviors[i].isRunning(world, e))
			{
				return;
			}
		}
	}


	public boolean isReady(World world, Entity e)
	{
		int index = getIndexForEntity(world, e);
		
		return index == 0 && behaviors[index].isReady(world, e);
	}


	public boolean isRunning(World world, Entity e)
	{
		int index = getIndexForEntity(world, e);
		
		return behaviors[index].isRunning(world, e) || behaviors[index].isSuccess(world, e) && index < behaviors.length - 1;
	}


	public boolean isSuccess(World world, Entity e)
	{
		int index = getIndexForEntity(world, e);
		
		return index == behaviors.length - 1 && behaviors[index].isSuccess(world, e);
	}


	public boolean isCompleted(World world, Entity e)
	{
		int index = getIndexForEntity(world, e);
		
		return index == behaviors.length - 1 && behaviors[index].isCompleted(world, e);
	}


	public void reset(World world, Entity e)
	{
		for (int i = 0; i < behaviors.length; i++)
		{
			if (behaviors[i].isCompleted(world, e))
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


	public void setBehaviors(IBehavior... behaviours)
	{
		this.behaviors = behaviours;
	}


	public void setBehaviors(List<IBehavior> behavioursList)
	{
		this.behaviors = behavioursList.toArray(new IBehavior[0]);
	}


	public String toString()
	{
		return String.format("[%s@%x, {behaviors: %s}]", getClass().getSimpleName(), hashCode(), behaviors);
	}
}
