
package experiments.artemis.components;

import com.artemis.Component;


public class MovementSpeedComponent extends Component
{
	private double speed = 0;


	public MovementSpeedComponent(double speed)
	{
		this.speed = speed;
	}


	public double getSpeed()
	{
		return speed;
	}


	public void setSpeed(double speed)
	{
		this.speed = speed;
	}
}
