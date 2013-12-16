
package experiments.artemis.ai.behaviours;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Inverter extends Decorator implements IBehavior
{
	public Inverter()
	{
	}


	public Inverter(IBehavior behavior)
	{
		super(behavior);
	}


	public boolean isSuccess()
	{
		return !super.isSuccess();
	}
}
