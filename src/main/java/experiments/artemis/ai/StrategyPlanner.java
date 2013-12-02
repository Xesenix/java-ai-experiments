
package experiments.artemis.ai;

import com.artemis.Entity;
import com.artemis.World;

import experiments.artemis.ai.behaviours.IGoal;
import experiments.artemis.ai.behaviours.ITask;
import experiments.artemis.ai.behaviours.Task;
import experiments.artemis.ai.strategy.IStrategy;
import experiments.artemis.ai.strategy.Translate;
import experiments.artemis.components.PositionGoal;


public class StrategyPlanner
{

	private IStrategy[] moveStrategy = new IStrategy[] { new Translate() };


	public IStrategy[] findStrategies(World world, Entity e, IGoal goal)
	{
		if (goal instanceof PositionGoal)
		{
			return moveStrategy;
		}

		return null;
	}

}
