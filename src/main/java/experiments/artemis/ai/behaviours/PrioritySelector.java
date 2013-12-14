
package experiments.artemis.ai.behaviours;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class PrioritySelector extends CompositBehavior implements IBehavior
{
	public PrioritySelector()
	{
	}
	
	
	public PrioritySelector(IBehavior... behaviors)
	{
		super(behaviors);
	}


	public void run()
	{
		for (int i = 0; i < behaviors.length; i++)
		{
			indexForEntity.set(actor.getId(), i);
			
			behaviors[i].run();
			
			if (behaviors[i].isRunning())
			{
				return;
			}
		}
	}


	public void reset()
	{
		for (int i = 0; i < behaviors.length; i++)
		{
			if (behaviors[i].isCompleted())
			{
				behaviors[i].reset();
			}
		}
	}


	public boolean isReady()
	{
		int index = indexForEntity.get(actor.getId());
		
		return index == 0 && behaviors[index].isReady();
	}


	public boolean isRunning()
	{
		int index = indexForEntity.get(actor.getId());
		
		return behaviors[index].isRunning() || behaviors[index].isSuccess() && index < behaviors.length - 1;
	}


	public boolean isSuccess()
	{
		int index = indexForEntity.get(actor.getId());
		
		return index == behaviors.length - 1 && behaviors[index].isSuccess();
	}


	public boolean isCompleted()
	{
		int index = indexForEntity.get(actor.getId());
		
		return index == behaviors.length - 1 && behaviors[index].isCompleted();
	}
}
