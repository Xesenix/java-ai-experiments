
package experiments.artemis.components;

import com.artemis.Component;


public class MovementDirectionComponent extends Component
{
	private double rotation = 0;


	public double getRotation()
	{
		return rotation;
	}


	public void setRotation(double rotation)
	{
		this.rotation = rotation;
	}


	public String toString()
	{
		return String.format("[%s@%x {rotation: %f}]", getClass().getSimpleName(), hashCode(), rotation);
	}
}
