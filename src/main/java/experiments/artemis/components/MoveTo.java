package experiments.artemis.components;

import java.util.Collection;

import ai.behaviour.IGoal;

public class MoveTo extends TaskComponent
{
	private IGoal[] goals = new IGoal[1];


	public IGoal[] getGoals()
	{
		return goals;
	}


	public void setGoals(Collection<IGoal> goals)
	{
		for (IGoal goal : goals)
		{
			if (goal instanceof PositionGoal)
			{
				this.setTarget((PositionGoal) goals);
				
				return;
			}
		}
	}
	
	
	public void setTarget(PositionGoal target)
	{
		this.goals[0] = target;
	}
}
