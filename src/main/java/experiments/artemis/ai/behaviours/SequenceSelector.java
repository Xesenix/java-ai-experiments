
package experiments.artemis.ai.behaviours;

import javax.xml.bind.annotation.XmlRootElement;

import experiments.artemis.ai.tasks.BehaviorState;


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
				
				if (behaviors[i].isReady())
				{
					behaviors[i].start();
				}
				
				behaviors[i].run();
				
				// is running ?
				if (behaviors[i].isRunning())
				{
					// return with running state
					setState(BehaviorState.RUNNING);
					return;
				}
				
				behaviors[i].end();
				
				if (!behaviors[i].isSuccess())
				{
					// return with fail state
					setState(BehaviorState.FAILURE);
					return;
				}
			}
			
			setState(BehaviorState.SUCCESS);
		}
	}


	public void reset()
	{
		BehaviorState state = getState();
		
		if (behaviors != null)
		{
			if (state != BehaviorState.RUNNING)
			{
				indexForEntity.set(actor.getId(), 0);
				
				for (int i = 0; i < behaviors.length; i++)
				{
					behaviors[i].reset();
				}
				
				setState(BehaviorState.READY);
			}
		}
	}
}
