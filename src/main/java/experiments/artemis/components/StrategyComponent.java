package experiments.artemis.components;

import ai.behaviour.IGoal;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.World;

public class StrategyComponent extends Component implements IStrategy
{
	private IStrategy strategy;

	public StrategyComponent(IStrategy strategy)
	{
		this.strategy = strategy;
	}

	@Override
	public boolean canPerform(World world, Entity e, IGoal goal)
	{
		return strategy.canPerform(world, e, goal);
	}

	@Override
	public boolean perform(World world, Entity e, IGoal goal)
	{
		return strategy.perform(world, e, goal);
	}


	
	
}
