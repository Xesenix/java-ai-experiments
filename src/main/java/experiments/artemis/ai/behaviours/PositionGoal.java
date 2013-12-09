package experiments.artemis.ai.behaviours;

import ai.world.IPosition;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;

import experiments.artemis.ai.world2d.Polygon;
import experiments.artemis.ai.world2d.Position;
import experiments.artemis.components.PositionComponent;

public class PositionGoal extends Goal implements IPositionGoal
{
	private IPositionGoal[] goals;
	
	
	public PositionGoal(IPositionGoal... goals)
	{
		setGoals(goals);
	}


	public void setGoals(IPositionGoal... goals)
	{
		this.goals = goals;
	}
	
	
	public IPositionGoal[] getGoals()
	{
		return goals;
	}
	
	
	public IPosition getTarget(World world, Entity e)
	{
		ComponentMapper<PositionComponent> pm = world.getMapper(PositionComponent.class);
		PositionComponent worldPosition = pm.get(e);
		Position position = null;
		
		if (worldPosition.getPosition() instanceof Position)
		{
			position = (Position) worldPosition.getPosition();
		}
		
		double x = 0, y = 0;
		int n = 0;
		
		for (IPositionGoal goal : getGoals())
		{
			IPosition pos = goal.getTarget(world, e);

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

}
