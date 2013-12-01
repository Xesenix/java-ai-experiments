
package experiments.artemis.components;

import ai.behaviour.IGoal;

import com.artemis.Entity;
import com.artemis.World;


public interface IStrategy
{
	boolean canPerform(World world, Entity e, IGoal goal);


	boolean perform(World world, Entity e, IGoal goal);
}
