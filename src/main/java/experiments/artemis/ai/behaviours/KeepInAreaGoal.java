package experiments.artemis.ai.behaviours;

import ai.world.IPosition;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;

import experiments.artemis.ai.world2d.Polygon;
import experiments.artemis.ai.world2d.Position;
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
	
	
	public Polygon getArea()
	{
		return polygon;
	}


	public IPosition getTarget(World world, Entity e)
	{
		double x = 0, y = 0;
		double[] vertices = polygon.getVertices();
		int n = vertices.length / 2;
		
		for (int i = 0; i < n; i++)
		{
			x += vertices[2 * i];
			y += vertices[2 * i + 1];
		}
		
		return new Position(x / (double) n, y / (double) n);
	}

}
