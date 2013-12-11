package experiments.artemis.ai.goals;

import ai.world.IPosition;

import com.artemis.ComponentMapper;

import experiments.artemis.ai.world2d.Position;
import experiments.artemis.components.PositionComponent;

public class PositionGoal extends Goal implements IPositionGoal
{
	private IPositionGoal[] goals;
	
	
	public PositionGoal()
	{
		// TODO Auto-generated constructor stub
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

}