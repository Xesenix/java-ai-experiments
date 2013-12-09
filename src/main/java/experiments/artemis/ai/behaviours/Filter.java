
package experiments.artemis.ai.behaviours;

import com.artemis.Entity;
import com.artemis.World;


abstract public class Filter extends Decorator
{
	public Filter(IBehavior behavior)
	{
		super(behavior);
	}


	public boolean run(World world, Entity e)
	{
		if (this.filterCondition(world, e))
		{
			return super.run(world, e);
		}

		return false;
	}


	abstract public boolean filterCondition(World world, Entity e);
}
