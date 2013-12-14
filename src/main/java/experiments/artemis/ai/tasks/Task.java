
package experiments.artemis.ai.tasks;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.utils.Bag;

import experiments.artemis.ai.goals.IGoal;
import experiments.artemis.systems.TasksSystem;


@XmlRootElement
public class Task implements ITask
{
	private String name;


	protected IGoal goals;


	protected transient Entity actor;


	private transient Bag<TaskState> stateByEntity = new Bag<TaskState>();


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
	public TaskState getState()
	{
		return stateByEntity.get(actor.getId());
	}


	public void setState(TaskState state)
	{
		stateByEntity.set(actor.getId(), state);
	}


	public void run()
	{
		World world = actor.getWorld();
		TasksSystem system = world.getSystem(TasksSystem.class);

		system.runTask(actor, this);
	}


	public void reset()
	{
		setState(TaskState.READY);
	}


	public boolean isReady()
	{
		return getState() == TaskState.READY;
	}


	public boolean isRunning()
	{
		return getState() == TaskState.RUNNING;
	}


	public boolean isSuccess()
	{
		return getState() == TaskState.SUCCESS;
	}


	public boolean isCompleted()
	{
		TaskState state = getState();

		return state == TaskState.SUCCESS || state == TaskState.FAILURE;
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


	public String toString()
	{
		return String.format("[%s@%x {name: %s, goals: %s}]", getClass().getSimpleName(), hashCode(), getName(), getGoals());
	}
}
