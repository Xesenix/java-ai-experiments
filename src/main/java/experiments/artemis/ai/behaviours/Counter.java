
package experiments.artemis.ai.behaviours;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.utils.Bag;


public class Counter extends Filter
{
	private int count;


	private Bag<Integer> counters = new Bag<Integer>();


	public Counter(IBehavior behavior, int count)
	{
		super(behavior);

		this.count = count;
	}


	public boolean isRunning(World world, Entity e)
	{
		return getCountFor(e) != 0 && super.isRunning(world, e);
	}


	public boolean isCompleted(World world, Entity e)
	{
		return getCountFor(e) == 0 || super.isCompleted(world, e);
	}


	public boolean isSuccess(World world, Entity e)
	{
		return getCountFor(e) == 0 || super.isSuccess(world, e);
	}


	public void reset(World world, Entity e)
	{
		if (isCompleted(world, e) && filterCondition(world, e))
		{
			int countForEntity = getCountFor(e);
			counters.set(e.getId(), --countForEntity);

			System.out.println("counter");
			System.out.println(countForEntity);

			super.reset(world, e);
		}
	}


	public boolean filterCondition(World world, Entity e)
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
