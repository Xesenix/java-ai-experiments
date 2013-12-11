
package experiments.artemis.ai.tasks;

import com.artemis.Entity;
import com.artemis.World;

import experiments.artemis.ai.behaviours.IBehavior;
import experiments.artemis.ai.goals.IGoal;


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
	TaskState getState();


	void setState(TaskState state);
}
