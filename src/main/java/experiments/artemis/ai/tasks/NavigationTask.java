
package experiments.artemis.ai.tasks;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;

import experiments.artemis.ai.goals.IGoal;
import experiments.artemis.ai.goals.IPositionGoal;


@XmlRootElement
public class NavigationTask extends Task
{
	public NavigationTask()
	{
	}


	public NavigationTask(IPositionGoal goals)
	{
		this.goals = goals;
	}


	public NavigationTask(String name, IPositionGoal goals)
	{
		this.setName(name);
		this.setGoals(goals);
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
}
