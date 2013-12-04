
package experiments.artemis.ai.behaviours;

import java.util.Collection;


public class PositionTask extends Task
{
	private IPositionGoal[] goals;


	public PositionTask(IPositionGoal... goals)
	{
		this.goals = goals;
	}


	public IPositionGoal[] getGoals()
	{
		return goals;
	}


	public void setGoals(Collection<IGoal> goals)
	{
		setGoals(goals.toArray(new IPositionGoal[goals.size()]));
	}


	public void setGoals(IPositionGoal... goals)
	{
		this.goals = goals;
	}


	public String toString()
	{
		return String.format("[%s@%x {targets: %s}]", getClass().getSimpleName(), hashCode(), this.goals);
	}
}
