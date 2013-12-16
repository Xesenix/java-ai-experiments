
package experiments.artemis.ai.behaviours;

public class Inverter extends Decorator implements IBehavior
{

	public Inverter(IBehavior behavior)
	{
		super(behavior);
	}


	public boolean isSuccess()
	{
		return !super.isSuccess();
	}
}
