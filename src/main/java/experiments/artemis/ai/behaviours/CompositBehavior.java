
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
import experiments.artemis.ai.tasks.BehaviorState;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class CompositBehavior implements IActorAware, ITreeNode, IBehavior
{
	@XmlAnyElement(lax = true)
	protected IBehavior behaviors[];


	protected transient Bag<Integer> indexForEntity = new Bag<Integer>();


	/**
	 * For speedup of state queries.
	 */
	private transient Bag<BehaviorState> stateByEntity = new Bag<BehaviorState>();


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
		
		IBehavior[] behaviors = getBehaviours();
		
		if (behaviors != null)
		{
			for (int i = 0; i < behaviors.length; i++)
			{
				behaviors[i].setActor(actor);
			}
		}
	}


	public BehaviorState getState()
	{
		return stateByEntity.get(actor.getId());
	}


	/**
	 * Applies only to this node.
	 * 
	 * @param state
	 */
	public void setState(BehaviorState state)
	{
		this.stateByEntity.set(actor.getId(), state);
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
		IBehavior[] behaviors = getBehaviours();

		if (behaviors != null)
		{
			indexForEntity.set(entity.getId(), behaviors.length - 1);
			
			for (int i = 0; i < behaviors.length; i++)
			{
				behaviors[i].actorAdded(entity);
			}
		}
	}


	public void actorRemoved(Entity entity)
	{
		indexForEntity.set(entity.getId(), null);
		
		IBehavior[] behaviors = getBehaviours();

		if (behaviors != null)
		{
			for (int i = 0; i < behaviors.length; i++)
			{
				behaviors[i].actorRemoved(entity);
			}
		}
	}


	public ArrayList<ITreeNode> getChildren()
	{
		ArrayList<ITreeNode> children = new ArrayList<ITreeNode>();
		IBehavior[] behaviors = getBehaviours();
		
		if (behaviors != null)
		{
			for (int i = 0; i < behaviors.length; i ++)
			{
				children.add((ITreeNode) behaviors[i]);
			}
		}
		
		return children;
	}


	public void start()
	{
	}


	abstract public void run();


	public void end()
	{
	}


	abstract public void reset();


	public boolean isReady()
	{
		BehaviorState state = getState();
		
		return state == BehaviorState.READY;
	}


	public boolean isRunning()
	{
		BehaviorState state = getState();
		
		return state == BehaviorState.RUNNING;
	}


	public boolean isSuccess()
	{
		BehaviorState state = getState();
		
		return state == BehaviorState.SUCCESS;
	}


	public boolean isCompleted()
	{
		BehaviorState state = getState();
		
		return state == BehaviorState.SUCCESS || state == BehaviorState.FAILURE;
	}


	public String toString()
	{
		return String.format("[%s@%x, {behaviors: %s, index: %d}]", getClass().getSimpleName(), hashCode(), getBehaviours(), indexForEntity.get(actor.getId()));
	}

}