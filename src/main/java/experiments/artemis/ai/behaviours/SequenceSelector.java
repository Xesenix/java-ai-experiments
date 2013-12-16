
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
		if (behaviors != null)
		{
			for (int i = indexForEntity.get(actor.getId()); i < behaviors.length; i++)
			{
				indexForEntity.set(actor.getId(), i);
				
				if (behaviors[i].isCompleted())
				{
					if (behaviors[i].isSuccess())
					{
						continue;
					}
					
					return;
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
	}


	public void reset()
	{
		if (!isReady() || isCompleted())
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
		if (behaviors == null)
		{
			return true;
		}
		
		int index = indexForEntity.get(actor.getId());
		
		return index < behaviors.length - 1 || behaviors[index].isReady();
	}


	public boolean isRunning()
	{
		if (behaviors == null)
		{
			return false;
		}
		
		int index = indexForEntity.get(actor.getId());
		
		return index < behaviors.length - 1 && behaviors[index].isRunning() || index == behaviors.length - 1 && !behaviors[index].isCompleted();
	}


	public boolean isSuccess()
	{
		if (behaviors == null)
		{
			return false;
		}
		
		int index = indexForEntity.get(actor.getId());
		
		return index == behaviors.length - 1 && behaviors[index].isSuccess();
	}


	public boolean isCompleted()
	{
		if (behaviors == null)
		{
			return true;
		}
		
		int index = indexForEntity.get(actor.getId());
		
		return (index == behaviors.length - 1 || !behaviors[index].isSuccess()) && behaviors[index].isCompleted();
	}
}
