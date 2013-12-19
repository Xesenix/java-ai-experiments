
package experiments.artemis.ai.behaviours;

import javax.xml.bind.annotation.XmlRootElement;

import experiments.artemis.ai.tasks.BehaviorState;


@XmlRootElement
public class PrioritySelector extends CompositBehavior
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
				setState(BehaviorState.RUNNING);
				return;
			}
			
			if (behaviors[i].isSuccess())
			{
				setState(BehaviorState.SUCCESS);
			}
			else
			{
				setState(BehaviorState.FAILURE);
			}
		}
		
		setState(BehaviorState.SUCCESS);
	}


	public void reset()
	{
		for (int i = 0; i < behaviors.length; i++)
		{
			if (!behaviors[i].isReady())
			{
				behaviors[i].reset();
			}
		}
	}


	public boolean isReady()
	{
		if (behaviors == null)
		{
			return false;
		}
		
		int index = indexForEntity.get(actor.getId());
		
		return index < behaviors.length - 1 || !behaviors[index].isCompleted();
	}


	public boolean isRunning()
	{
		if (behaviors == null)
		{
			return false;
		}
		
		int index = indexForEntity.get(actor.getId());
		
		return behaviors[index].isRunning() || behaviors[index].isSuccess() && index < behaviors.length - 1;
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
		
		return index == behaviors.length - 1 && behaviors[index].isCompleted();
	}
}
