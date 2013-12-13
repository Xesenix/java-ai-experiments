package experiments.artemis.ai.goals;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;

import ai.world.IPosition;

import com.artemis.ComponentMapper;

import experiments.artemis.ai.world2d.Position;
import experiments.artemis.components.PositionComponent;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PositionGoal extends Goal implements IPositionGoal
{
	@XmlAnyElement(lax = true)
	private IPositionGoal[] goals;
	
	
	public PositionGoal()
	{
	}
	
	
	public PositionGoal(IPositionGoal... goals)
	{
		setGoals(goals);
	}
	
	
	public IPositionGoal[] getGoals()
	{
		return goals;
	}


	public void setGoals(IPositionGoal... goals)
	{
		this.goals = goals;
	}
	
	
	public IPosition getTarget()
	{
		ComponentMapper<PositionComponent> pm = world.getMapper(PositionComponent.class);
		PositionComponent worldPosition = pm.get(entity);
		Position position = null;
		
		if (worldPosition.getPosition() instanceof Position)
		{
			position = (Position) worldPosition.getPosition();
		}
		
		double x = 0, y = 0;
		int n = 0;
		
		for (IPositionGoal goal : getGoals())
		{
			IPosition pos = goal.getTarget();

			if (pos instanceof Position)
			{
				x += ((Position) pos).getX();
				y += ((Position) pos).getY();
				n ++;
			}
		}
		
		if (n > 0)
		{
			return new Position(x / (double) n, y / (double) n);
		}
		
		return null;
	}


	public String toString()
	{
		return String.format("[%s@%x {goals: %s}]", getClass().getSimpleName(), hashCode(), getGoals());
	}

}
