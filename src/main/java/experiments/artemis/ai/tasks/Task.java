
package experiments.artemis.ai.tasks;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.utils.Bag;

import experiments.artemis.ai.goals.Goal;
import experiments.artemis.ai.goals.IGoal;
import experiments.artemis.ai.graph.ITreeNode;
import experiments.artemis.systems.TasksSystem;


@XmlRootElement
public class Task implements ITask, ITreeNode, Cloneable
{
	private String name;


	protected IGoal goals;


	protected transient Entity actor;


	private transient Bag<BehaviorState> stateByEntity = new Bag<BehaviorState>();


	public Task()
	{
	}


	public Task(String name)
	{
		setName(name);
	}


	public Task(IGoal goals)
	{
		setGoals(goals);
	}


	public Task(String name, IGoal goals)
	{
		setName(name);
		setGoals(goals);
	}


	@XmlAttribute
	public String getName()
	{
		return name;
	}


	public void setName(String name)
	{
		this.name = name;
	}


	@XmlAnyElement(lax = true)
	public IGoal getGoals()
	{
		return goals;
	}


	public void setGoals(IGoal goals)
	{
		this.goals = goals;

		if (this.goals != null)
		{
			this.goals.setActor(actor);
		}
	}


	@XmlTransient
	public BehaviorState getState()
	{
		return stateByEntity.get(actor.getId());
	}


	public void setState(BehaviorState state)
	{
		stateByEntity.set(actor.getId(), state);
	}


	public void start()
	{
		World world = actor.getWorld();
		TasksSystem system = world.getSystem(TasksSystem.class);
		
		system.startTask(actor, this);
	}


	public void run()
	{
		World world = actor.getWorld();
		TasksSystem system = world.getSystem(TasksSystem.class);

		system.runTask(actor, this);
	}


	public void end()
	{
		World world = actor.getWorld();
		TasksSystem system = world.getSystem(TasksSystem.class);
		
		system.endTask(actor, this);
	}


	public void reset()
	{
		setState(BehaviorState.READY);
	}


	public boolean isReady()
	{
		return getState() == BehaviorState.READY;
	}


	public boolean isRunning()
	{
		return getState() == BehaviorState.RUNNING;
	}


	public boolean isSuccess()
	{
		return getState() == BehaviorState.SUCCESS;
	}


	public boolean isCompleted()
	{
		BehaviorState state = getState();

		return state == BehaviorState.SUCCESS || state == BehaviorState.FAILURE;
	}


	public void setActor(Entity actor)
	{
		this.actor = actor;

		if (getGoals() != null)
		{
			getGoals().setActor(actor);
		}
	}


	public void actorAdded(Entity entity)
	{
		stateByEntity.set(entity.getId(), null);
	}


	public void actorRemoved(Entity entity)
	{
		stateByEntity.set(entity.getId(), null);
	}


	public ArrayList<ITreeNode> getChildren()
	{
		return null;
	}


	public String toString()
	{
		return String.format("[%s@%x {name: %s, goals: %s, bag: %x}]", getClass().getSimpleName(), hashCode(), getName(), getGoals(), stateByEntity.hashCode());
	}
	
	
	public Task clone() throws CloneNotSupportedException
	{
		Task clone = (Task) super.clone();
		
		clone.stateByEntity = new Bag<BehaviorState>();
		
		clone.name = getName();
		
		Goal goal = (Goal) getGoals();
		
		clone.goals = goal.clone();
		
		return (Task) clone;
	}
}
