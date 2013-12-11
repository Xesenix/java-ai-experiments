
package experiments.artemis.ai.goals;

import com.artemis.Entity;
import com.artemis.World;

import experiments.artemis.ai.behaviours.IContextAware;


public class Goal implements IGoal, IContextAware
{
	private IGoal[] goals;


	protected World world;


	protected Entity entity;


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


	public IGoal[] getGoals()
	{
		return goals;
	}


	public void setGoals(IGoal... goals)
	{
		this.goals = goals;
	}
}
