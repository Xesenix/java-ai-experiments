
package experiments.artemis.ai.behaviours;

import com.artemis.Entity;
import com.artemis.World;


public interface IBehavior
{
	/**
	 * 
	 * @param world
	 * @param e
	 * @return
	 */
	void run(World world, Entity e);


	/**
	 * True if not yet started.
	 * 
	 * @param world
	 * @param e
	 * @return
	 */
	boolean isReady(World world, Entity e);


	/**
	 * True if behavior still running.
	 * 
	 * @param world
	 * @param e
	 * @return
	 */
	boolean isRunning(World world, Entity e);


	/**
	 * Reset state of behavior.
	 * 
	 * @param world
	 * @param e
	 */
	void reset(World world, Entity e);


	/**
	 * True if goals achived.
	 * 
	 * @param world
	 * @param e
	 * @return true if achieved all goals
	 */
	boolean isSuccess(World world, Entity e);


	/**
	 * Strategy finished execution eighter success or failure.
	 * 
	 * @param world
	 * @param e
	 * @return
	 */
	boolean isCompleted(World world, Entity e);
}
