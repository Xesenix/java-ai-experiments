
package experiments.artemis.ai.behaviours;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.artemis.Entity;
import com.artemis.utils.Bag;

import experiments.artemis.ai.graph.ITreeNode;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CompositBehavior implements IActorAware, ITreeNode
{
	@XmlAnyElement(lax = true)
	protected IBehavior behaviors[];


	protected transient Bag<Integer> indexForEntity = new Bag<Integer>();


	protected transient Entity actor;


	public CompositBehavior()
	{
		super();
	}
	
	
	public CompositBehavior(IBehavior... behaviors)
	{
		setBehaviors(behaviors);
	}


	public void setActor(Entity actor)
	{
		this.actor = actor;

		for (int i = 0; i < behaviors.length; i++)
		{
			behaviors[i].setActor(actor);
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


	public List<ITreeNode> getChildren()
	{
		List<ITreeNode> children = new ArrayList<ITreeNode>();
		IBehavior[] behaviors = getBehaviours();
		
		for (int i = 0; i < behaviors.length; i ++)
		{
			children.add((ITreeNode) behaviors[i]);
		}
		
		return children;
	}


	public String toString()
	{
		return String.format("[%s@%x, {behaviors: %s}]", getClass().getSimpleName(), hashCode(), getBehaviours());
	}

}