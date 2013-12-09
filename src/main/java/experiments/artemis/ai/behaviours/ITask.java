
package experiments.artemis.ai.behaviours;

import com.artemis.Entity;
import com.artemis.World;


/**
 * Task its leaf node in behavior tree. Task has goals to achieve.
 * 
 * @author Xesenix
 */
public interface ITask extends IBehavior
{
	IGoal getGoals();


	void setGoals(IGoal goals);


	/**
	 * Strategy finished execution.
	 * 
	 * @param world
	 * @param e
	 * @return
	 */
	TaskState getState(World world, Entity e);


	void setState(World world, Entity e, TaskState state);
}
