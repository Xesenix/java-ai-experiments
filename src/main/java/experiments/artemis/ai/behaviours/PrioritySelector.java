
package experiments.artemis.ai.behaviours;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.utils.Bag;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PrioritySelector implements IBehavior
{
	@XmlAnyElement(lax = true)
	private IBehavior behaviors[];


	private transient Bag<Integer> indexForEntity = new Bag<Integer>();


	protected transient World world;


	protected transient Entity entity;
	
	
	public PrioritySelector()
	{
	}
	
	
	public PrioritySelector(IBehavior... behaviors)
	{
		setBehaviors(behaviors);
	}


	public void run()
	{
		for (int i = 0; i < behaviors.length; i++)
		{
			indexForEntity.set(entity.getId(), i);
			
			behaviors[i].run();
			
			if (behaviors[i].isRunning())
			{
				return;
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


	public void reset()
	{
		for (int i = 0; i < behaviors.length; i++)
		{
			if (behaviors[i].isCompleted())
			{
				behaviors[i].reset();
			}
		}
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
