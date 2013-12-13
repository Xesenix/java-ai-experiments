
package experiments.artemis.ai.tasks;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;

import experiments.artemis.ai.goals.IPositionGoal;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PositionTask extends Task
{
	@XmlAnyElement(lax = true)
	private IPositionGoal goals;
	
	
	public PositionTask()
	{
	}


	public PositionTask(IPositionGoal goals)
	{
		this.goals = goals;
	}


	public PositionTask(String name, IPositionGoal goals)
	{
		this.setName(name);
		this.setGoals(goals);
	}


	public IPositionGoal getGoals()
	{
		return goals;
	}


	public void setGoals(IPositionGoal goals)
	{
		this.goals = goals;
	}


	public String toString()
	{
		return String.format("[%s@%x {name: %s, targets: %s}]", getClass().getSimpleName(), hashCode(), getName(), getGoals());
	}
}
