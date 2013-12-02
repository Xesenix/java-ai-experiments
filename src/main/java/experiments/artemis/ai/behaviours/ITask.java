
package experiments.artemis.ai.behaviours;

import java.util.Collection;


/**
 * Task its leaf node in behavior tree. Task has goals to achieve.
 * 
 * @author Xesenix
 */
public interface ITask extends IBehavior
{

	boolean isRunning();


	IGoal[] getGoals();


	void setGoals(Collection<IGoal> goals);

}
