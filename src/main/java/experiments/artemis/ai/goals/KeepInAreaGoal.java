
package experiments.artemis.ai.goals;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import ai.world.IPosition;

import com.artemis.ComponentMapper;

import experiments.artemis.ai.world2d.Polygon;
import experiments.artemis.ai.world2d.Position;
import experiments.artemis.components.PositionComponent;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class KeepInAreaGoal extends PositionGoal
{
	@XmlElement
	private Polygon polygon;


	public KeepInAreaGoal()
	{
	}


	public KeepInAreaGoal(double... coordinates) throws Exception
	{
		this(new Polygon(coordinates));
	}


	public KeepInAreaGoal(Polygon polygon)
	{
		this.polygon = polygon;
	}


	public boolean achived()
	{
		ComponentMapper<PositionComponent> pm = world.getMapper(PositionComponent.class);
		PositionComponent worldPosition = pm.get(entity);

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


	public IPosition getTarget()
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


	public String toString()
	{
		return String.format("[%s@%x {target: %s, area: %s}]", getClass().getSimpleName(), hashCode(), getTarget(), getArea());
	}

}
