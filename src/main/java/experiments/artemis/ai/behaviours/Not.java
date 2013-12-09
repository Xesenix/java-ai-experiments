
package experiments.artemis.ai.behaviours;

import com.artemis.Entity;
import com.artemis.World;


public class Not extends Condition
{
	public Not(IGoal goals)
	{
		super(goals);
	}
	
	
	public boolean run(World world, Entity e)
	{
		return !super.run(world, e);
	}
	
	
	public boolean isSuccess(World world, Entity e)
	{
		return !super.isSuccess(world, e);
	}
}
