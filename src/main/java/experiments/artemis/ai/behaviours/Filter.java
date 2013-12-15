
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


	public boolean isReady()
	{
		return filterCondition() && super.isReady();
	}


	public boolean isSuccess()
	{
		return filterCondition() && super.isSuccess();
	}


	abstract public boolean filterCondition();
}
