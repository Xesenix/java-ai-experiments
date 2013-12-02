
package experiments.artemis.components.behaviours;

import java.util.Collection;

import experiments.artemis.ai.behaviours.IGoal;
import experiments.artemis.components.tasks.TaskComponent;


public class Idle extends TaskComponent
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
