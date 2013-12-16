
package experiments.artemis.ai.behaviours;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Succeeder extends Decorator
{
	public Succeeder()
	{
		// TODO Auto-generated constructor stub
	}
	
	
	public Succeeder(IBehavior behavior)
	{
		super(behavior);
	}


	public boolean isSuccess()
	{
		return super.isCompleted();
	}
}
