
package experiments.artemis.ai;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.utils.Bag;

import experiments.artemis.ai.strategy.CenterOfMassDestination;
import experiments.artemis.ai.strategy.IStrategy;
import experiments.artemis.ai.strategy.MessageToConsole;
import experiments.artemis.ai.strategy.NearCenterOfMassDestination;
import experiments.artemis.ai.tasks.ITask;
import experiments.artemis.ai.tasks.MessageTask;
import experiments.artemis.ai.tasks.NavigationTask;


public class StrategyPlanner
{

	private IStrategy[] navigationStrategies = new IStrategy[] {
		new CenterOfMassDestination(),
		new NearCenterOfMassDestination(30, 5),
		// new NearCenterOfMassDestination(30, 10),
		// new NearCenterOfMassDestination(30, 15),
	};


	private IStrategy[] messageStrategies = new IStrategy[] {
		new MessageToConsole(),
		// new NearCenterOfMassDestination(30, 10),
		// new NearCenterOfMassDestination(30, 15),
	};
	
	
	private Bag<Integer> navigationIndexForEntity = new Bag<Integer>();
	
	
	private Bag<Integer> messageIndexForEntity = new Bag<Integer>();


	public IStrategy bestStrategyFor(World world, Entity e, ITask task)
	{
		IStrategy startegy = null;
		
		if (task instanceof NavigationTask)
		{
			int index = 0;
			
			if (navigationIndexForEntity.get(e.getId()) != null)
			{
				index = navigationIndexForEntity.get(e.getId());
			}
			
			startegy = navigationStrategies[index ++];
			
			navigationIndexForEntity.set(e.getId(), index % navigationStrategies.length);
		}
		
		if (task instanceof MessageTask)
		{
			int index = 0;
			
			if (messageIndexForEntity.get(e.getId()) != null)
			{
				index = messageIndexForEntity.get(e.getId());
			}
			
			startegy = messageStrategies[index ++];
			
			messageIndexForEntity.set(e.getId(), index % messageStrategies.length);
		}

		return startegy;
	}

}
