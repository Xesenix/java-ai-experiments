
package experiments.artemis.ai.strategy;

import com.artemis.Entity;
import com.artemis.World;

import experiments.artemis.ai.behaviours.IGoal;


public interface IStrategy
{
	boolean canPerform(World world, Entity e, IGoal goal);


	boolean perform(World world, Entity e, IGoal goal);
}
