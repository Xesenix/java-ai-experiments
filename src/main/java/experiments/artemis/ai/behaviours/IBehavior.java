
package experiments.artemis.ai.behaviours;

import com.artemis.Entity;
import com.artemis.World;


public interface IBehavior
{
	boolean run(World world, Entity e);


	boolean isReady(World world, Entity e);


	boolean isRunning(World world, Entity e);


	void reset(World world, Entity e);


	/**
	 * All goals achieved.
	 * 
	 * @param world
	 * @param e
	 * @return true if achieved all goals
	 */
	boolean isSuccess(World world, Entity e);





	/**
	 * Strategy finished execution.
	 * 
	 * @param world
	 * @param e
	 * @return
	 */
	boolean isCompleted(World world, Entity e);
}
