package ai.behaviour;

import java.util.Collection;

public interface ITask
{
	Collection<IGoal> getGoals();
	
	
	void setGoals(Collection<IGoal> goals);
	
	
	
}
