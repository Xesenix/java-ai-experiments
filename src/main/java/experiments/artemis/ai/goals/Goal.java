
package experiments.artemis.ai.goals;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.artemis.Entity;
import com.artemis.World;

import experiments.artemis.ai.behaviours.IContextAware;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Goal implements IGoal, IContextAware
{
	@XmlAnyElement(lax = true)
	private IGoal[] goals;


	protected transient World world;


	protected transient Entity entity;


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


	public IGoal[] getGoals()
	{
		return goals;
	}


	public void setGoals(IGoal... goals)
	{
		this.goals = goals;
	}


	public String toString()
	{
		return String.format("[%s@%x {goals: %s}]", getClass().getSimpleName(), hashCode(), getGoals());
	}
}
