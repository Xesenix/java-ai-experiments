package experiments.artemis.components;

import com.artemis.Component;

public class NearDistanceComponent extends Component
{
	private double near = 0;

	public double getNear()
	{
		return near;
	}

	public void setNear(double near)
	{
		this.near = near;
	}
}
