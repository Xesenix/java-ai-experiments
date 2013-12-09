
package experiments.artemis.ai.behaviours;

import com.artemis.Entity;
import com.artemis.World;


abstract public class Filter extends Decorator
{
	public Filter(ITask task)
	{
		super(task);
	}


	public boolean run(World world, Entity e)
	{
		if (this.isTrue(world, e))
		{
			return super.run(world, e);
		}

		return false;
	}


	abstract public boolean isTrue(World world, Entity e);
}
