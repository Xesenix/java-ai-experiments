
package experiments.artemis.ai.behaviours;

import java.util.List;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.utils.Bag;


public class TaskSelector implements IBehavior
{
	private IBehavior behaviors[];


	private transient Bag<Integer> indexForEntity = new Bag<Integer>();
	
	
	public TaskSelector(IBehavior... behaviors)
	{
		setBehaviors(behaviors);
	}


	public boolean run(World world, Entity e)
	{
		for (int i = getIndexForEntity(world, e); i < behaviors.length; i++)
		{
			System.out.println("list");
			System.out.println(i);
			indexForEntity.set(e.getId(), i);
			
			if (behaviors[i].isSuccess(world, e))
			{
				continue;
			}
			
			if (behaviors[i].run(world, e))
			{
				if (behaviors[i].isRunning(world, e))
				{
					return true;
				}
			}
			else
			{
				//indexForEntity.set(e.getId(), 0);
				
				return false;
			}
		}
		
		return true;
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
		System.out.println("reset?");
		if (!isRunning(world, e))
		{
			System.out.println("reset");
			
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
			System.out.println("new index");
			index = 0;
			indexForEntity.set(e.getId(), index);
		}
		
		System.out.println(index);

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
