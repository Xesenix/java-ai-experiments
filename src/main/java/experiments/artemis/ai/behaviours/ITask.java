
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
	 * All goals achived.
	 * @param world
	 * @param e
	 * @return true if achived all goals
	 */
	boolean finished(World world, Entity e);


	void setCompleted(World world, Entity e, boolean completed);


	boolean isCompleted(World world, Entity e);
}
