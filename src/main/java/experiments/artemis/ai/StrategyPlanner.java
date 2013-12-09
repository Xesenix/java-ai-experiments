
package experiments.artemis.ai;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.utils.Bag;

import experiments.artemis.ai.behaviours.ITask;
import experiments.artemis.ai.behaviours.PositionTask;
import experiments.artemis.ai.strategy.CenterOfMassDestination;
import experiments.artemis.ai.strategy.IStrategy;


public class StrategyPlanner
{

	private IStrategy[] positionStrategies = new IStrategy[] {
		new CenterOfMassDestination(),
		// new NearCenterOfMassDestination(30, 5),
		// new NearCenterOfMassDestination(30, 10),
		// new NearCenterOfMassDestination(30, 15),
	};
	
	
	private Bag<Integer> indexForEntity = new Bag<Integer>();


	public IStrategy bestStrategyFor(World world, Entity e, ITask task)
	{
		IStrategy startegy = null;
		
		if (task instanceof PositionTask)
		{
			int index = 0;
			
			if (indexForEntity.get(e.getId()) != null)
			{
				index = indexForEntity.get(e.getId());
			}
			
			startegy = positionStrategies[index ++];
			
			indexForEntity.set(e.getId(), index % positionStrategies.length);
		}

		return startegy;
	}

}
