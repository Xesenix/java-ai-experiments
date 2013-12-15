
package experiments.artemis.ai.behaviours;

public class Succeeder extends Decorator
{
	public Succeeder(IBehavior behavior)
	{
		super(behavior);
	}


	public boolean isSuccess()
	{
		return super.isCompleted();
	}
}
