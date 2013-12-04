
package experiments.artemis.ai;

import com.artemis.Entity;
import com.artemis.World;

import experiments.artemis.ai.behaviours.ITask;
import experiments.artemis.ai.behaviours.PositionTask;
import experiments.artemis.ai.strategy.IStrategy;
import experiments.artemis.ai.strategy.CenterOfMassDestination;


public class StrategyPlanner
{

	private IStrategy positionStrategy = new CenterOfMassDestination();


	public IStrategy bestStrategyFor(World world, Entity e, ITask task)
	{
		if (task instanceof PositionTask)
		{
			return positionStrategy;
		}

		return null;
	}

}
