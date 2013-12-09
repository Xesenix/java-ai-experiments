package experiments.artemis.ai.behaviours;

import com.artemis.Entity;
import com.artemis.World;

public class Condition extends Task
{
	public Condition(IGoal goals)
	{
		setGoals(goals);
	}


	public boolean isSuccess(World world, Entity e)
	{
		return getGoals().achived(world, e);
	}
	
	
	public boolean run(World world, Entity e)
	{
		return getGoals().achived(world, e);
	}


	public boolean isCompleted(World world, Entity e)
	{
		return true;
	}
}
