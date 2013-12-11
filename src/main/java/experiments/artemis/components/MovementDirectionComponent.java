
package experiments.artemis.components;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.artemis.Component;


@XmlRootElement(name ="movementDirection")
@XmlAccessorType(XmlAccessType.FIELD)
public class MovementDirectionComponent extends Component
{
	@XmlAttribute
	private double direction = 0;
	
	
	@XmlAttribute
	private double rotationRate = Math.PI;


	public MovementDirectionComponent()
	{
	}
	
	
	public MovementDirectionComponent(double direction)
	{
		setDirection(direction);
	}
	
	
	public MovementDirectionComponent(double direction, double rotationRate)
	{
		setDirection(direction);
		setRotationRate(rotationRate);
	}


	public double getDirection()
	{
		return direction;
	}


	public void setDirection(double direction)
	{
		this.direction = Math.IEEEremainder(direction, 2 * Math.PI);
	}


	public double getRotationRate()
	{
		return rotationRate;
	}


	public void setRotationRate(double rotationRate)
	{
		this.rotationRate = rotationRate;
	}
	
	
	public void changeDirection(double newDirection, double time)
	{
		double directionChange = Math.IEEEremainder(newDirection - direction, 2 * Math.PI);
		
		if (directionChange > 0)
		{
			setDirection(direction + Math.min(directionChange, rotationRate * time));
		}
		else
		{
			setDirection(direction + Math.max(directionChange, - rotationRate * time));
		}
	}


	public String toString()
	{
		return String.format("[%s@%x {rotation: %.2f°}]", getClass().getSimpleName(), hashCode(), Math.toDegrees(direction));
	}
}
