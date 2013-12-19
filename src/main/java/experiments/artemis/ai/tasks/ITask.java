
package experiments.artemis.ai.tasks;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import experiments.artemis.ai.behaviours.IBehavior;
import experiments.artemis.ai.goals.IGoal;


/**
 * Task its leaf node in behavior tree. Task has goals to achieve.
 * 
 * @author Xesenix
 */
@XmlJavaTypeAdapter(xml.AnyTypeAdapter.class)
public interface ITask extends IBehavior
{
	IGoal getGoals();


	void setGoals(IGoal goals);
}
