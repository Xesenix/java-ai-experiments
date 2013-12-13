
package experiments.artemis.ai.conditions;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import experiments.artemis.ai.goals.IGoal;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Not extends Condition
{
	public Not()
	{
	}


	public Not(IGoal goals)
	{
		super(goals);
	}


	public boolean isSuccess()
	{
		return !super.isSuccess();
	}
}
