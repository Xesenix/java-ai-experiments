
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
public class CompositBehavior implements IActorAware
{
	@XmlAnyElement(lax = true)
	protected IBehavior behaviors[];


	protected transient Bag<Integer> indexForEntity = new Bag<Integer>();


	protected transient World world;


	protected transient Entity entity;


	public CompositBehavior()
	{
		super();
	}
	
	
	public CompositBehavior(IBehavior... behaviors)
	{
		setBehaviors(behaviors);
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


	public void actorAdded(Entity entity)
	{
		indexForEntity.set(entity.getId(), 0);
		
		IBehavior[] behaviors = getBehaviours();

		for (int i = 0; i < behaviors.length; i++)
		{
			behaviors[i].actorAdded(entity);
		}
	}


	public void actorRemoved(Entity entity)
	{
		indexForEntity.set(entity.getId(), null);
		
		IBehavior[] behaviors = getBehaviours();

		for (int i = 0; i < behaviors.length; i++)
		{
			behaviors[i].actorRemoved(entity);
		}
	}

}