
package ai.world2d;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import ai.world.IPosition;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Position implements IPosition
{
	@XmlAttribute
	private double x;


	@XmlAttribute
	private double y;


	public Position()
	{
	}


	public Position(double x, double y)
	{
		set(x, y);
	}
	
	
	public void set(double x, double y)
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
