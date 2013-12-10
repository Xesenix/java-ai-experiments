
package experiments.artemis.ai.behaviours;

import com.artemis.Entity;
import com.artemis.World;


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
