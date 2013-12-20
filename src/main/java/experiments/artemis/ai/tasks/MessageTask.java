package experiments.artemis.ai.tasks;

import experiments.artemis.ai.goals.IMessageGoal;
import experiments.artemis.ai.goals.IPositionGoal;

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
}
