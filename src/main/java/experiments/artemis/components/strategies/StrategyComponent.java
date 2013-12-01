
package experiments.artemis.components.strategies;

import ai.behaviour.IGoal;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.World;

import experiments.artemis.components.IStrategy;


public class StrategyComponent extends Component implements IStrategy
{
	private IStrategy strategy;


	public StrategyComponent(IStrategy strategy)
	{
		this.strategy = strategy;
	}


	public boolean canPerform(World world, Entity e, IGoal goal)
	{
		return strategy.canPerform(world, e, goal);
	}


	public boolean perform(World world, Entity e, IGoal goal)
	{
		return strategy.perform(world, e, goal);
	}


	public void set(IStrategy strategy)
	{
		this.strategy = strategy;
	}


	public String toString()
	{
		return String.format("[%s@%x, {strategy: %s}]", getClass().getSimpleName(), hashCode(), strategy);
	}
}
