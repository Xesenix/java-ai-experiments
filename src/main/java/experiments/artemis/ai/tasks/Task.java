
package experiments.artemis.ai.tasks;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.utils.Bag;

import experiments.artemis.ai.goals.IGoal;
import experiments.artemis.systems.TasksSystem;


public class Task implements ITask
{
	private Bag<TaskState> stateByEntity = new Bag<TaskState>();
	
	
	private IGoal goals;


	private World world;


	private Entity entity;


	public Task()
	{
	}


	public void run()
	{
		TasksSystem system = world.getSystem(TasksSystem.class);
		
		system.runTask(entity, this);
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


	public void setContext(World world, Entity entity)
	{
		this.world = world;
		this.entity = entity;
		
		if (getGoals() != null)
		{
			getGoals().setContext(world, entity);
		}
	}


	public TaskState getState()
	{
		return stateByEntity.get(entity.getId());
	}


	public void setState(TaskState state)
	{
		stateByEntity.set(entity.getId(), state);
	}


	public IGoal getGoals()
	{
		return goals;
	}


	public void setGoals(IGoal goals)
	{
		this.goals = goals;
		
		if (this.goals != null)
		{
			this.goals.setContext(world, entity);
		}
	}


	public String toString()
	{
		return String.format("[%s@%x]", getClass().getSimpleName(), hashCode());
	}
}
