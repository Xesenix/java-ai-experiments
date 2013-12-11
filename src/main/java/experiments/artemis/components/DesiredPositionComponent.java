
package experiments.artemis.components;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import ai.world.IPosition;

import com.artemis.Component;


@XmlRootElement(name ="desiredPosition")
@XmlAccessorType(XmlAccessType.FIELD)
public class DesiredPositionComponent extends Component
{
	@XmlAnyElement
	private IPosition position;


	@XmlAttribute
	private double precision;
	
	
	public DesiredPositionComponent()
	{
	}


	public DesiredPositionComponent(IPosition position)
	{
		this.setPosition(position);
	}


	public IPosition getPosition()
	{
		return position;
	}


	public void setPosition(IPosition position)
	{
		this.position = position;
	}


	public double getPrecision()
	{
		return precision;
	}


	public void setPrecision(double precision)
	{
		this.precision = precision;
	}


	public String toString()
	{
		return String.format("[%s@%x {position: %s, precision: %s}]", getClass().getSimpleName(), hashCode(), position, precision);
	}
}
