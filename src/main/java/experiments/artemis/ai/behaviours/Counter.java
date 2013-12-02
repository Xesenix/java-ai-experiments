
package experiments.artemis.ai.behaviours;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.utils.Bag;


public class Counter extends Filter
{
	private int count;


	private Bag<Integer> counters = new Bag<Integer>();
	
	
	public Counter(ITask task, int count)
	{
		super(task);
		
		this.count = count;
	}


	public boolean isTrue(World world, Entity e)
	{
		int countForEntity = getCountFor(e);
		
		return countForEntity > 0;
	}
	
	
	private int getCountFor(Entity e)
	{
		Integer counter = counters.get(e.getId());
		
		if (counter == null)
		{
			counter = count;
		}
		
		return counter;
	}
	
	
	public void setCompleted(World world, Entity e, boolean completed)
	{
		if (completed)
		{
			int countForEntity = getCountFor(e);
			counters.set(e.getId(), -- countForEntity);
		}
		
		super.setCompleted(world, e, completed);
	}


	public void setCount(int count)
	{
		this.count = count;
	}


	public int getCount()
	{
		return this.count;
	}
	
	
	public String toString()
	{
		return super.toString();
	}
}
