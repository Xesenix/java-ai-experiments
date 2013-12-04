
package experiments.artemis.ai;

import com.artemis.Entity;
import com.artemis.World;

import experiments.artemis.ai.behaviours.IGoal;
import experiments.artemis.ai.behaviours.ITask;
import experiments.artemis.ai.behaviours.Task;
import experiments.artemis.ai.strategy.IStrategy;
import experiments.artemis.ai.strategy.TranslateFrom;
import experiments.artemis.ai.strategy.TranslateTo;
import experiments.artemis.components.NearPositionGoal;


public class StrategyPlanner
{

	private IStrategy[] moveToStrategies = new IStrategy[] { new TranslateTo() };
	
	
	private IStrategy[] moveAwayStrategies = new IStrategy[] { new TranslateFrom() };


	public IStrategy[] findStrategies(World world, Entity e, IGoal goal)
	{
		if (goal instanceof NearPositionGoal)
		{
			return moveToStrategies;
		}

		return null;
	}

}
