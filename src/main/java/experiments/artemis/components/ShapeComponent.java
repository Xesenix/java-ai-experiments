
package experiments.artemis.components;

import com.artemis.Component;

import experiments.artemis.ai.world2d.Polygon;


public class ShapeComponent extends Component
{
	private Polygon shape;


	public ShapeComponent()
	{
	}


	public ShapeComponent(Polygon shape)
	{
		this.shape = shape;
	}


	public Polygon getShape()
	{
		return shape;
	}


	public String toString()
	{
		return String.format("[%s@%x {shape: %s}]", getClass().getSimpleName(), hashCode(), shape);
	}
}
