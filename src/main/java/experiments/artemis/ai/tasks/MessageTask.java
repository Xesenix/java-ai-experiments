package experiments.artemis.ai.tasks;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;

import experiments.artemis.ai.goals.IGoal;
import experiments.artemis.ai.goals.IMessageGoal;


@XmlRootElement
public class MessageTask extends Task
{
	public MessageTask()
	{
	}
	
	
	public MessageTask(String name, IMessageGoal goals)
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
