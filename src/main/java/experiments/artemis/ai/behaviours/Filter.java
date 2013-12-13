
package experiments.artemis.ai.behaviours;



abstract public class Filter extends Decorator
{
	public Filter()
	{
	}
	
	
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
