
package experiments.artemis.ai.behaviours;

import com.artemis.Entity;
import com.artemis.World;


abstract public class Filter extends Decorator
{
	public Filter(ITask task)
	{
		super(task);
	}


	public ITask chooseTask(World world, Entity e)
	{
		if (this.isTrue(world, e))
		{
			return super.chooseTask(world, e);
		}

		return null;
	}


	abstract public boolean isTrue(World world, Entity e);
}
