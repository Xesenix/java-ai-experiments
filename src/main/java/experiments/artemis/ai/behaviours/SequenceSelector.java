
package experiments.artemis.ai.behaviours;

import java.util.List;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.utils.Bag;


public class SequenceSelector implements IBehavior
{
	private IBehavior behaviors[];


	private transient Bag<Integer> indexForEntity = new Bag<Integer>();


	private World world;


	private Entity entity;
	
	
	public SequenceSelector(IBehavior... behaviors)
	{
		setBehaviors(behaviors);
	}


	public void run()
	{
		for (int i = getIndexForEntity(); i < behaviors.length; i++)
		{
			indexForEntity.set(entity.getId(), i);
			
			if (behaviors[i].isSuccess())
			{
				continue;
			}
			
			behaviors[i].run();
			
			if (!behaviors[i].isSuccess())
			{
				return;
			}
			else if (behaviors[i].isRunning())
			{
				return;
			}
		}
	}


	public void reset()
	{
		if (!isRunning())
		{
			indexForEntity.set(entity.getId(), 0);

			for (int i = 0; i < behaviors.length; i++)
			{
				behaviors[i].reset();
			}
		}
	}


	public boolean isReady()
	{
		int index = getIndexForEntity();
		
		return index == 0 && behaviors[index].isReady();
	}


	public boolean isRunning()
	{
		int index = getIndexForEntity();
		
		return behaviors[index].isRunning() || behaviors[index].isSuccess() && index < behaviors.length - 1;
	}


	public boolean isSuccess()
	{
		int index = getIndexForEntity();
		
		return index == behaviors.length - 1 && behaviors[index].isSuccess();
	}


	public boolean isCompleted()
	{
		int index = getIndexForEntity();
		
		return index == behaviors.length - 1 && behaviors[index].isCompleted();
	}


	public void setContext(World world, Entity entity)
	{
		this.world = world;
		this.entity = entity;
		
		for (int i = 0; i < behaviors.length; i++)
		{
			behaviors[i].setContext(world, entity);
		}
	}


	private int getIndexForEntity()
	{
		Integer index = indexForEntity.get(entity.getId());

		if (index == null)
		{
			index = 0;
			indexForEntity.set(entity.getId(), index);
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
