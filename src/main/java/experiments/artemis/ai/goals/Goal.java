
package experiments.artemis.ai.goals;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.artemis.Entity;
import com.artemis.World;

import experiments.artemis.ai.behaviours.IActorAware;
import experiments.artemis.ai.behaviours.IContextAware;


@XmlRootElement
public class Goal implements IGoal, IContextAware
{
	private IGoal[] goals;


	protected transient World world;


	protected transient Entity entity;


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


	public void setContext(World world, Entity entity)
	{
		this.world = world;
		this.entity = entity;
		
		IGoal[] goals = getGoals();
		
		if (goals != null)
		{
			for (int i = 0; i < goals.length; i++)
			{
				if (goals[i] instanceof IContextAware)
				{
					((IContextAware) goals[i]).setContext(world, entity);
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
}
