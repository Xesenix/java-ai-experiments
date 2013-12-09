
package experiments.artemis.ai.behaviours;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.utils.Bag;

import experiments.artemis.systems.TasksSystem;


public class Task implements ITask
{
	private Bag<TaskState> stateByEntity = new Bag<TaskState>();
	
	
	private IGoal goals;


	public Task()
	{
	}


	public TaskState getState(World world, Entity e)
	{
		return stateByEntity.get(e.getId());
	}


	public void setState(World world, Entity e, TaskState state)
	{
		stateByEntity.set(e.getId(), state);
	}


	public IGoal getGoals()
	{
		return goals;
	}


	public void setGoals(IGoal goals)
	{
		this.goals = goals;
	}


	public boolean run(World world, Entity e)
	{
		TasksSystem system = world.getSystem(TasksSystem.class);
		
		return system.runTask(e, this);
		
		/*if (getState(world, e) == TaskState.READY)
		{
			ComponentMapper<TasksComponent> tm = world.getMapper(TasksComponent.class);
			TasksComponent tasks = tm.get(e);
			
			if (tasks == null)
			{
				tasks = new TasksComponent();
				
				e.addComponent(tasks);
				e.changedInWorld();
			}
			
			tasks.addTask(this);
		}
		
		return true;*/
	}


	public boolean isReady(World world, Entity e)
	{
		return getState(world, e) == TaskState.READY;
	}


	public boolean isSuccess(World world, Entity e)
	{
		return getState(world, e) == TaskState.SUCCESS;
	}


	public boolean isCompleted(World world, Entity e)
	{
		TaskState state = getState(world, e);

		return state == TaskState.SUCCESS || state == TaskState.FAILURE;
	}


	public boolean isRunning(World world, Entity e)
	{
		return getState(world, e) == TaskState.RUNNING;
	}


	public void reset(World world, Entity e)
	{
		setState(world, e, TaskState.READY);
	}


	public String toString()
	{
		return String.format("[%s@%x]", getClass().getSimpleName(), hashCode());
	}
}
