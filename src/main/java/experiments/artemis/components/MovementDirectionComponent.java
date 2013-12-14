
package experiments.artemis.components;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.artemis.Component;


@XmlRootElement(name ="movementDirection")
public class MovementDirectionComponent extends Component
{
	private double direction = 0;
	
	
	private double rotationRate = Math.PI;


	public MovementDirectionComponent()
	{
	}
	
	
	public MovementDirectionComponent(double direction)
	{
		setDirectionRadians(direction);
	}
	
	
	public MovementDirectionComponent(double direction, double rotationRate)
	{
		setDirectionRadians(direction);
		setRotationRateRadians(rotationRate);
	}


	@XmlTransient
	public double getDirectionRadians()
	{
		return direction;
	}


	public void setDirectionRadians(double angle)
	{
		this.direction = Math.IEEEremainder(angle, 2 * Math.PI);
	}


	@XmlAttribute
	public double getDirection()
	{
		return Math.toDegrees(direction);
	}


	public void setDirection(double direction)
	{
		this.direction = Math.IEEEremainder(Math.toRadians(direction), 2 * Math.PI);
	}


	@XmlTransient
	public double getRotationRatRadianse()
	{
		return rotationRate;
	}


	public void setRotationRateRadians(double rotationRate)
	{
		this.rotationRate = rotationRate;
	}


	@XmlAttribute
	public double getRotationRate()
	{
		return Math.toDegrees(rotationRate);
	}


	public void setRotationRate(double rotationRate)
	{
		this.rotationRate = Math.toRadians(rotationRate);
	}
	
	
	public void changeDirection(double newDirection, double time)
	{
		double directionChange = Math.IEEEremainder(newDirection - direction, 2 * Math.PI);
		
		if (directionChange > 0)
		{
			setDirectionRadians(direction + Math.min(directionChange, rotationRate * time));
		}
		else
		{
			setDirectionRadians(direction + Math.max(directionChange, - rotationRate * time));
		}
	}


	public String toString()
	{
		return String.format("[%s@%x {rotation: %.2f°}]", getClass().getSimpleName(), hashCode(), Math.toDegrees(direction));
	}
}
