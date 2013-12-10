
package experiments.artemis.ai.behaviours;

import com.artemis.Entity;
import com.artemis.World;


abstract public class Filter extends Decorator
{
	public Filter(IBehavior behavior)
	{
		super(behavior);
	}


	public void run()
	{
		if (this.filterCondition())
		{
			super.run();
		}
	}


	abstract public boolean filterCondition();
}
