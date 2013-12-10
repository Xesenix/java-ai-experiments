package experiments.artemis.ai.behaviours;

import com.artemis.Entity;
import com.artemis.World;

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
