
package experiments.artemis.components;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.artemis.Component;


@XmlRootElement(name ="movementSpeed")
@XmlAccessorType(XmlAccessType.FIELD)
public class MovementSpeedComponent extends Component
{
	@XmlAttribute
	private double speed = 0;


	@XmlAttribute
	private double max = 0;


	@XmlAttribute
	private double min = 0;


	@XmlAttribute
	private double acceleration = 0;


	@XmlAttribute
	private double decceleration = 0;


	public MovementSpeedComponent()
	{
		set(0, 0, 0);
	}


	public MovementSpeedComponent(double max)
	{
		set(max, 0, max);
	}


	public MovementSpeedComponent(double max, double acceleration)
	{
		set(0, 0, max);
		setAcceleration(acceleration, acceleration);
	}


	public MovementSpeedComponent(double min, double max, double acceleration)
	{
		set(0, min, max);
		setAcceleration(acceleration, acceleration);
	}


	public MovementSpeedComponent(double min, double max, double acceleration, double deceleration)
	{
		set(0, min, max);
		setAcceleration(acceleration, deceleration);
	}


	public MovementSpeedComponent(double speed, double min, double max, double acceleration, double deceleration)
	{
		set(speed, min, max);
		setAcceleration(acceleration, deceleration);
	}


	public void set(double speed, double min, double max)
	{
		setMax(max);
		setMin(min);
		setSpeed(speed);
	}


	public void setRange(double min, double max)
	{
		setMax(max);
		setMin(min);
	}


	public void setAcceleration(double acceleration, double deceleration)
	{
		setAcceleration(acceleration);
		setDecceleration(deceleration);
	}


	public double getSpeed()
	{
		return speed;
	}


	public void setSpeed(double speed)
	{
		if (min > speed)
		{
			this.speed = min;
		}
		else if (max < speed)
		{
			this.speed = max;
		}
		else
		{
			this.speed = speed;
		}
	}
	
	
	public void changeSpeed(double newSpeed, double time)
	{
		if (newSpeed > speed)
		{
			setSpeed(Math.min(newSpeed, speed + acceleration * time));
		}
		else
		{
			setSpeed(Math.max(newSpeed, speed - decceleration * time));
		}
	}


	public double getMax()
	{
		return max;
	}


	public void setMax(double max)
	{
		if (max > this.min)
		{
			this.max = max;
		}
	}


	public double getMin()
	{
		return min;
	}


	public void setMin(double min)
	{
		if (this.max > min)
		{
			this.min = min;
		}
	}


	public double getAcceleration()
	{
		return acceleration;
	}


	public void setAcceleration(double acceleration)
	{
		this.acceleration = acceleration;
	}


	public double getDecceleration()
	{
		return decceleration;
	}


	public void setDecceleration(double decceleration)
	{
		this.decceleration = decceleration;
	}


	public String toString()
	{
		return String.format("[%s@%x {speed: %f, min: %f, max: %f, acceleration: %f, decceleration: %f}]", getClass().getSimpleName(), hashCode(), speed, min, max, acceleration, decceleration);
	}
}
