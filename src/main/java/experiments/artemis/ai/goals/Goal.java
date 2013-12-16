
package experiments.artemis.ai.goals;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.artemis.Entity;

import experiments.artemis.ai.behaviours.IActorAware;


@XmlRootElement
public class Goal implements IGoal, Cloneable
{
	private IGoal[] goals;


	protected transient Entity actor;


	@XmlAnyElement(lax = true)
	public IGoal[] getGoals()
	{
		return goals;
	}


	public void setGoals(IGoal... goals)
	{
		this.goals = goals;
	}


	public boolean achived()
	{
		IGoal[] goals = getGoals();

		for (int i = 0; i < goals.length; i++)
		{
			if (!goals[i].achived())
			{
				return false;
			}
		}

		return false;
	}


	public void setActor(Entity actor)
	{
		this.actor = actor;
		
		IGoal[] goals = getGoals();
		
		if (goals != null)
		{
			for (int i = 0; i < goals.length; i++)
			{
				if (goals[i] instanceof IActorAware)
				{
					((IActorAware) goals[i]).setActor(actor);
				}
			}
		}
	}


	public void actorAdded(Entity entity)
	{
		IGoal[] goals = getGoals();
		
		if (goals != null)
		{
			for (int i = 0; i < goals.length; i++)
			{
				if (goals[i] instanceof IActorAware)
				{
					((IActorAware) goals[i]).actorAdded(entity);
				}
			}
		}
	}


	public void actorRemoved(Entity entity)
	{
		IGoal[] goals = getGoals();
		
		if (goals != null)
		{
			for (int i = 0; i < goals.length; i++)
			{
				if (goals[i] instanceof IActorAware)
				{
					((IActorAware) goals[i]).actorRemoved(entity);
				}
			}
		}
	}


	public String toString()
	{
		return String.format("[%s@%x {goals: %s}]", getClass().getSimpleName(), hashCode(), getGoals());
	}
	
	
	public Goal clone() throws CloneNotSupportedException
	{
		Goal clone = (Goal) super.clone();
		
		IGoal[] goals = getGoals();
		
		if (goals != null)
		{
			IGoal[] goalsClone = new IGoal[goals.length];
			
			for (int i = 0; i < goalsClone.length; i++)
			{
				if (goals[i] instanceof Goal)
				{
					goalsClone[i] = ((Goal) goals[i]).clone();
				}
			}
			
			clone.setGoals(goalsClone);
		}
		
		return clone;
	}
}
