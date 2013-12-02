
package experiments.artemis.components.behaviours;

import java.util.Collection;

import experiments.artemis.ai.behaviours.IGoal;
import experiments.artemis.ai.behaviours.Task;


public class Idle extends Task
{
	private IGoal[] goals = new IGoal[0];


	public IGoal[] getGoals()
	{
		return goals;
	}


	public void setGoals(Collection<IGoal> goals)
	{
		this.goals = goals.toArray(new IGoal[0]);
	}
}
