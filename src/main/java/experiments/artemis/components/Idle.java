package experiments.artemis.components;

import java.util.Collection;

import ai.behaviour.IGoal;

public class Idle extends TaskComponent
{
	private IGoal[] goals;


	public IGoal[] IGoal()
	{
		return goals;
	}


	public void setGoals(Collection<IGoal> goals)
	{
		this.goals = goals.toArray(new IGoal[0]);
	}
}
