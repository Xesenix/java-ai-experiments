
package experiments.artemis.ai.behaviours;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class SequenceSelector extends CompositBehavior implements IBehavior
{
	public SequenceSelector()
	{
	}
	
	
	public SequenceSelector(IBehavior... behaviors)
	{
		super(behaviors);
	}


	public void run()
	{
		for (int i = indexForEntity.get(actor.getId()); i < behaviors.length; i++)
		{
			indexForEntity.set(actor.getId(), i);
			
			if (behaviors[i].isCompleted() && behaviors[i].isSuccess())
			{
				continue;
			}
			
			behaviors[i].run();
			
			if (!behaviors[i].isSuccess())
			{
				return;
			}
			else if (behaviors[i].isRunning())
			{
				return;
			}
		}
	}


	public void reset()
	{
		if (!isReady())
		{
			indexForEntity.set(actor.getId(), 0);

			for (int i = 0; i < behaviors.length; i++)
			{
				behaviors[i].reset();
			}
		}
	}


	public boolean isReady()
	{
		int index = indexForEntity.get(actor.getId());
		
		return index < behaviors.length - 1 || !behaviors[index].isCompleted();
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
