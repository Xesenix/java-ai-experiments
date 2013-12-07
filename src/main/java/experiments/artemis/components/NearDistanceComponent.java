
package experiments.artemis.components;

import com.artemis.Component;


public class NearDistanceComponent extends Component
{
	private double near = 0;


	public NearDistanceComponent(double near)
	{
		setNear(near);
	}


	public double getNear()
	{
		return near;
	}


	public void setNear(double near)
	{
		this.near = near;
	}


	public String toString()
	{
		return String.format("[%s@%x {near: %.2f}]", getClass().getSimpleName(), hashCode(), near);
	}
}
