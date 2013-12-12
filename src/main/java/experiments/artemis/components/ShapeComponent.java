
package experiments.artemis.components;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.artemis.Component;

import experiments.artemis.ai.world2d.Polygon;


@XmlRootElement(name ="shape")
@XmlAccessorType(XmlAccessType.FIELD)
public class ShapeComponent extends Component
{
	@XmlElement
	private Polygon shape;


	public ShapeComponent()
	{
	}


	public ShapeComponent(Polygon shape)
	{
		setShape(shape);
	}


	public Polygon getShape()
	{
		return shape;
	}


	public void setShape(Polygon shape)
	{
		this.shape = shape;
	}


	public String toString()
	{
		return String.format("[%s@%x {shape: %s}]", getClass().getSimpleName(), hashCode(), shape);
	}
}
