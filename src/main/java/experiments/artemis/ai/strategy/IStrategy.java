
package experiments.artemis.ai.strategy;

import com.artemis.Entity;
import com.artemis.World;

import experiments.artemis.ai.tasks.ITask;


public interface IStrategy
{
	boolean canPerform(World world, Entity e, ITask task);


	boolean perform(World world, Entity e, ITask task);
}
