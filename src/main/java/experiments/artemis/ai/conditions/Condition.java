package experiments.artemis.ai.conditions;

import experiments.artemis.ai.goals.IGoal;
import experiments.artemis.ai.tasks.Task;

public class Condition extends Task
{
	public Condition(IGoal goals)
	{
		setGoals(goals);
	}


	public boolean isSuccess()
	{
		return getGoals().achived();
	}
	
	
	public void run() {}


	public boolean isCompleted()
	{
		return true;
	}
}
