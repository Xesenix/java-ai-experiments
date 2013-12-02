package experiments.artemis.ai.world2d;

import ai.world.IPosition;

public class Position implements IPosition
{
	private double x;


	private double y;


	public Position()
	{
	}


	public Position(double x, double y)
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


	public String toString()
	{
		return String.format("[%s@%x, {x: %f; y: %f}]", getClass().getSimpleName(), hashCode(), x, y);
	}
}
