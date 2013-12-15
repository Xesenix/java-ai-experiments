
package experiments.artemis.ai.conditions;

import javax.xml.bind.annotation.XmlRootElement;

import experiments.artemis.ai.goals.IGoal;
import experiments.artemis.ai.tasks.Task;


@XmlRootElement
public class Condition extends Task
{
	public Condition()
	{
	}


	public Condition(String name)
	{
		super(name);
	}


	public Condition(IGoal goals)
	{
		super(goals);
	}


	public Condition(String name, IGoal goals)
	{
		super(name, goals);
	}


	public void run()
	{
	}


	public void reset()
	{
	}


	public boolean isReady()
	{
		return false;
	}


	public boolean isRunning()
	{
		return false;
	}


	public boolean isCompleted()
	{
		return true;
	}


	public boolean isSuccess()
	{
		return getGoals().achived();
	}


	public IGoal getGoals()
	{
		return super.getGoals();
	}


	public void setGoals(IGoal goals)
	{
		super.setGoals(goals);
	}
}
