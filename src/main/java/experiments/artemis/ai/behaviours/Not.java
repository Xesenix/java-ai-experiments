
package experiments.artemis.ai.behaviours;

import com.artemis.Entity;
import com.artemis.World;


public class Not extends Condition
{

	private Condition condition;


	public Not(Condition condition)
	{
		this.condition = condition;
	}


	public boolean isCompleted(World world, Entity e)
	{
		return !condition.isCompleted(world, e);
	}
	
	
	public boolean isSuccess(World world, Entity e)
	{
		return !condition.isSuccess(world, e);
	}
}
