
package experiments.artemis.ai.behaviours;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.artemis.Entity;
import com.artemis.utils.Bag;


@XmlRootElement
public class Counter extends Filter
{
	private int count;


	private transient Bag<Integer> counters = new Bag<Integer>();
	
	
	public Counter()
	{
	}


	public Counter(IBehavior behavior, int count)
	{
		super(behavior);

		this.count = count;
	}


	@XmlAttribute
	public int getCount()
	{
		return count;
	}


	public void setCount(int count)
	{
		this.count = count;
	}


	public void reset()
	{
		if (isCompleted() && filterCondition())
		{
			int countForEntity = counters.get(actor.getId());
			counters.set(actor.getId(), -- countForEntity);

			super.reset();
		}
	}


	public boolean isRunning()
	{
		return counters.get(actor.getId()) != 0 && super.isRunning();
	}


	public boolean isCompleted()
	{
		return counters.get(actor.getId()) == 0 || super.isCompleted();
	}


	public boolean isSuccess()
	{
		return counters.get(actor.getId()) == 0 || super.isSuccess();
	}


	public boolean filterCondition()
	{
		return counters.get(actor.getId()) > 0;
	}
	
	
	public void actorAdded(Entity entity)
	{
		counters.set(entity.getId(), count);
		
		super.actorAdded(entity);
	}
	
	
	public void actorRemoved(Entity entity)
	{
		counters.set(entity.getId(), null);
		
		super.actorRemoved(entity);
	}
}
