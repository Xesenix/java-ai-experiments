
package experiments.artemis.components.tasks;

import java.util.Collection;

import experiments.artemis.ai.behaviours.IGoal;
import experiments.artemis.ai.behaviours.IPositionGoal;

public class MoveTo extends TaskComponent
{
	private IPositionGoal[] goals = new IPositionGoal[1];


	public IGoal[] getGoals()
	{
		return goals;
	}


	public void setGoals(Collection<IGoal> goals)
	{
		for (IGoal goal : goals)
		{
			if (goal instanceof IPositionGoal)
			{
				this.setTarget((IPositionGoal) goals);

				return;
			}
		}
	}


	public void setTarget(IPositionGoal target)
	{
		this.goals[0] = target;
	}
	
	
	public String toString()
	{
		return String.format("[%s@%x, {goal: %s}]", getClass().getSimpleName(), hashCode(), this.goals[0].getTarget());
	}
}
