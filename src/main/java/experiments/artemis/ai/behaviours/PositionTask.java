
package experiments.artemis.ai.behaviours;

import java.util.Collection;

public class PositionTask extends Task
{
	private IPositionGoal[] goals;


	public PositionTask(IPositionGoal... goals)
	{
		this.goals = goals;
	}


	public IGoal[] getGoals()
	{
		return goals;
	}


	public void setGoals(Collection<IGoal> goals)
	{
		setGoals(goals.toArray(new IGoal[goals.size()]));
	}


	public void setGoals(IGoal... goals)
	{
		int size = 0;
		
		for (IGoal goal : goals)
		{
			if (goal instanceof IPositionGoal)
			{
				size ++;
			}
		}
		
		this.goals = new IPositionGoal[size];
		
		int index = 0;
		
		for (IGoal goal : goals)
		{
			if (goal instanceof IPositionGoal)
			{
				this.goals[index++] = (IPositionGoal) goal;
			}
		}
	}
	
	
	public String toString()
	{
		return String.format("[%s@%x, {goal: %s}]", getClass().getSimpleName(), hashCode(), this.goals);
	}
}
