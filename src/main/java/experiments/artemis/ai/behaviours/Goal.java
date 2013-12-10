
package experiments.artemis.ai.behaviours;

import com.artemis.Entity;
import com.artemis.World;


public class Goal implements IGoal, IContextAware
{
	private IGoal[] goals;


	protected World world;


	protected Entity entity;


	public void setGoals(IGoal... goals)
	{
		this.goals = goals;
	}


	public IGoal[] getGoals()
	{
		return goals;
	}


	public boolean achived()
	{
		IGoal[] goals = getGoals();

		for (int i = 0; i < goals.length; i++)
		{
			if (!goals[i].achived())
			{
				return false;
			}
		}

		return false;
	}


	public void setContext(World world, Entity entity)
	{
		this.world = world;
		this.entity = entity;
		
		IGoal[] goals = getGoals();
		
		if (goals != null)
		{
			for (int i = 0; i < goals.length; i++)
			{
				if (goals[i] instanceof IContextAware)
				{
					((IContextAware) goals[i]).setContext(world, entity);
				}
			}
		}
	}
}
