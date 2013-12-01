
package ai.behaviour;

import java.util.Collection;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


@XmlJavaTypeAdapter(xml.AnyTypeAdapter.class)
public interface ITask
{
	IGoal[] getGoals();


	void setGoals(Collection<IGoal> goals);
}
