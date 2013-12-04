
package experiments.artemis.ai.behaviours;

import java.util.Collection;

import com.artemis.Entity;
import com.artemis.World;


/**
 * Task its leaf node in behavior tree. Task has goals to achieve.
 * 
 * @author Xesenix
 */
public interface ITask extends IBehavior
{
	IGoal[] getGoals();


	void setGoals(Collection<IGoal> goals);


	/**
	 * All goals achieved.
	 * 
	 * @param world
	 * @param e
	 * @return true if achieved all goals
	 */
	boolean isSuccess(World world, Entity e);


	void setCompleted(World world, Entity e, boolean completed);


	/**
	 * Strategy finished execution.
	 * 
	 * @param world
	 * @param e
	 * @return
	 */
	boolean isCompleted(World world, Entity e);
}
