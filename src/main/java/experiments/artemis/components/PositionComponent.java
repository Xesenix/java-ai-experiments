
package experiments.artemis.components;

import com.artemis.Component;


public class PositionComponent extends Component
{
	private double x;


	private double y;


	public PositionComponent()
	{
	}


	public PositionComponent(double x, double y)
	{
		this.setX(x);
		this.setY(y);
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
}
