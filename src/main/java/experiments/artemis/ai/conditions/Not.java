
package experiments.artemis.ai.conditions;

import experiments.artemis.ai.goals.IGoal;


public class Not extends Condition
{
	public Not(IGoal goals)
	{
		super(goals);
	}
	
	
	public boolean isSuccess()
	{
		return !super.isSuccess();
	}
}
