package experiments.artemis.ai.goals;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;

import ai.world.IPosition;

import com.artemis.ComponentMapper;
import com.artemis.World;

import experiments.artemis.ai.world2d.Position;
import experiments.artemis.components.PositionComponent;


@XmlRootElement
public class PositionGoal extends Goal implements IPositionGoal
{
	public PositionGoal()
	{
	}
	
	
	public PositionGoal(IPositionGoal... goals)
	{
		setGoals(goals);
	}
	
	
	public IPosition getTarget()
	{
		World world = actor.getWorld();
		ComponentMapper<PositionComponent> pm = world.getMapper(PositionComponent.class);
		PositionComponent worldPosition = pm.get(actor);
		Position position = null;
		
		if (worldPosition.getPosition() instanceof Position)
		{
			position = (Position) worldPosition.getPosition();
		}
		
		double x = 0, y = 0;
		int n = 0;
		
		for (IGoal goal : getGoals())
		{
			if (goal instanceof IPositionGoal)
			{
				IPosition pos = ((IPositionGoal) goal).getTarget();
	
				if (pos instanceof Position)
				{
					x += ((Position) pos).getX();
					y += ((Position) pos).getY();
					n ++;
				}
			}
		}
		
		if (n > 0)
		{
			return new Position(x / (double) n, y / (double) n);
		}
		
		return null;
	}
}
