
package experiments.artemis.components;

import com.artemis.Component;


public class MovementSpeedComponent extends Component
{
	private double speed = 0;


	private double max = 0;


	private double min = 0;


	public MovementSpeedComponent()
	{
		this(0, 0, 0);
	}


	public MovementSpeedComponent(double speed)
	{
		this(speed, speed, speed);
	}


	public MovementSpeedComponent(double speed, double max)
	{
		this(speed, speed, max);
	}


	public MovementSpeedComponent(double speed, double min, double max)
	{
		set(speed, min, max);
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
		
		if (min < speed && speed < max)
		{
			this.speed = speed;
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


	public String toString()
	{
		return String.format("[%s@%x {speed: %f, min: %f, max: %f}]", getClass().getSimpleName(), hashCode(), speed, min, max);
	}
}
