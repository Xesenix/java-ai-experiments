package experiments.artemis.ai.behaviours;

import ai.world.IPosition;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;

import experiments.artemis.ai.world2d.Polygon;
import experiments.artemis.ai.world2d.Position;
import experiments.artemis.components.ConsoleDebugComponent;
import experiments.artemis.components.PositionComponent;

public class KeepInAreaGoal implements IPositionGoal
{
	private Polygon polygon;


	public KeepInAreaGoal()
	{
		// TODO Auto-generated constructor stub
	}
	
	
	public KeepInAreaGoal(double... coordinates) throws Exception
	{
		this(new Polygon(coordinates));
	}
	
	
	public KeepInAreaGoal(Polygon polygon)
	{
		this.polygon = polygon;
	}
	

	public boolean achived(World world, Entity e)
	{
		ComponentMapper<PositionComponent> pm = world.getMapper(PositionComponent.class);
		
		PositionComponent worldPosition = pm.get(e);
		
		if (worldPosition == null)
		{
			return false;
		}
		
		if (!(worldPosition.getPosition() instanceof Position))
		{
			return false;
		}
		
		Position position = (Position) worldPosition.getPosition();
		
		return Polygon.insidePolygon(position.getX(), position.getY(), polygon.getVertices());
	}


	public IPosition getTarget(World world, Entity e)
	{
		return polygon;
	}

}
