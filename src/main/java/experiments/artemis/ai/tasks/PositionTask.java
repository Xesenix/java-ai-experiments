
package experiments.artemis.ai.tasks;

import experiments.artemis.ai.goals.IPositionGoal;



public class PositionTask extends Task
{
	private IPositionGoal goals;
	
	
	private String name;


	public PositionTask(IPositionGoal goals)
	{
		this.goals = goals;
	}


	public PositionTask(String name, IPositionGoal goals)
	{
		this.setName(name);
		this.setGoals(goals);
	}


	public String getName()
	{
		return name;
	}


	public void setName(String name)
	{
		this.name = name;
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
