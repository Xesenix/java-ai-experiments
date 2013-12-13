
package experiments.artemis.ai.behaviours;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.artemis.utils.Bag;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Counter extends Filter
{
	@XmlAttribute
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


	public void reset()
	{
		if (isCompleted() && filterCondition())
		{
			int countForEntity = getCounterForEntity();
			counters.set(entity.getId(), -- countForEntity);

			System.out.println("counter");
			System.out.println(countForEntity);

			super.reset();
		}
	}


	public boolean isRunning()
	{
		return getCounterForEntity() != 0 && super.isRunning();
	}


	public boolean isCompleted()
	{
		return getCounterForEntity() == 0 || super.isCompleted();
	}


	public boolean isSuccess()
	{
		return getCounterForEntity() == 0 || super.isSuccess();
	}


	public boolean filterCondition()
	{
		int countForEntity = getCounterForEntity();

		return countForEntity > 0;
	}


	private int getCounterForEntity()
	{
		Integer counter = counters.get(entity.getId());

		if (counter == null)
		{
			counter = count;
		}

		return counter;
	}


	public int getCount()
	{
		return count;
	}


	public void setCount(int count)
	{
		this.count = count;
	}


	public String toString()
	{
		return super.toString();
	}
}
