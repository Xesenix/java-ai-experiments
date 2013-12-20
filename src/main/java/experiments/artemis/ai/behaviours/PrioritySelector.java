
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
				return;
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
		
		setState(BehaviorState.READY);
	}
}
