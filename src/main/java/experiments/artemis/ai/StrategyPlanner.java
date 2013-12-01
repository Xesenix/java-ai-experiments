package experiments.artemis.ai;

import ai.behaviour.IGoal;

import com.artemis.Entity;

import experiments.artemis.ai.strategy.TranslateStrategy;
import experiments.artemis.components.IStrategy;
import experiments.artemis.components.PositionGoal;

public class StrategyPlanner
{

	private IStrategy[] moveStrategy = new IStrategy[] {
		new TranslateStrategy()
	};

	public IStrategy[] findStrategies(IGoal goal, Entity e)
	{
		if (goal instanceof PositionGoal)
		{
			return moveStrategy;
		}
		
		return null;
	}
	
}
