package experiments.artemis.ai.conditions;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import experiments.artemis.ai.goals.IGoal;
import experiments.artemis.ai.tasks.Task;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Condition extends Task
{
	public Condition()
	{
	}
	
	
	public Condition(IGoal goals)
	{
		setGoals(goals);
	}
	
	
	public void run() {}


	public boolean isCompleted()
	{
		return true;
	}


	public boolean isSuccess()
	{
		return getGoals().achived();
	}
}
