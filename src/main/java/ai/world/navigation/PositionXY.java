
package ai.world.navigation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PositionXY implements IPosition
{
	@XmlAttribute
	private double x;


	@XmlAttribute
	private double y;


	public PositionXY()
	{
	}


	public PositionXY(double x, double y)
	{
		this.x = x;
		this.y = y;
	}


	public double getX()
	{
		return x;
	}


	public void setX(double x)
	{
		this.x = x;
	}


	public double getY()
	{
		return y;
	}


	public void setY(double y)
	{
		this.y = y;
	}


	public String toString()
	{
		return String.format("{x: %.2f; y: %.2f}", x, y);
	}
}