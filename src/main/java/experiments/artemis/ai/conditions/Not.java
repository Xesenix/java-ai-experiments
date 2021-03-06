
package experiments.artemis.ai.conditions;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;

import experiments.artemis.ai.goals.IGoal;


@XmlRootElement
public class Not extends Condition
{
	public Not()
	{
	}


	public Not(IGoal goals)
	{
		super(goals);
	}


	public Not(String name, IGoal goals)
	{
		super(name, goals);
	}
	
	
	@XmlAnyElement(lax = true)
	public IGoal getGoals()
	{
		return super.getGoals();
	}
	
	
	public void setGoals(IGoal goals)
	{
		super.setGoals(goals);
	}


	public boolean isSuccess()
	{
		return !super.isSuccess();
	}
}
