package experiments.artemis.ai.behaviours;

import com.artemis.Entity;
import com.artemis.World;

public class Goal implements IGoal
{
	private IGoal[] goals;
	
	
	public void setGoals(IGoal... goals)
	{
		this.goals = goals;
	}
	
	
	public IGoal[] getGoals()
	{
		return goals;
	}
	
	
	public boolean achived(World world, Entity e)
	{
		IGoal[] goals = getGoals();
		
		for (int i = 0; i < goals.length; i++)
		{
			if (!goals[i].achived(world, e))
			{
				return false;
			}
		}
		
		return false;
	}
}
