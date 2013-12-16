
package experiments.artemis.ai.behaviours;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.artemis.Entity;
import com.artemis.utils.Bag;


@XmlRootElement
public class Limiter extends Filter
{
	private int limit;


	private transient Bag<Integer> counters = new Bag<Integer>();
	
	
	public Limiter()
	{
	}


	public Limiter(IBehavior behavior, int limit)
	{
		super(behavior);

		this.limit = limit;
	}


	@XmlAttribute
	public int getLimit()
	{
		return limit;
	}


	public void setLimit(int count)
	{
		this.limit = count;
	}


	public void reset()
	{
		if (!isReady() && !isRunning() && filterCondition())
		{
			int countForEntity = counters.get(actor.getId());
			counters.set(actor.getId(), -- countForEntity);
		}

		super.reset();
	}


	public boolean filterCondition()
	{
		return counters.get(actor.getId()) >= 0;
	}
	
	
	public void actorAdded(Entity entity)
	{
		counters.set(entity.getId(), limit);
		
		super.actorAdded(entity);
	}
	
	
	public void actorRemoved(Entity entity)
	{
		counters.set(entity.getId(), null);
		
		super.actorRemoved(entity);
	}


	public String toString()
	{
		return String.format("[%s@%x, {limit: %d}]", getClass().getSimpleName(), hashCode(), counters.get(actor.getId()));
	}
}
