
package experiments.artemis.ai.conditions;

import com.artemis.Entity;
import com.artemis.World;

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
