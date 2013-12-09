
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
		this.set(x, y);
	}


	public Position(Position target)
	{
		this.set(target.getX(), target.getY());
	}


	public void set(double x, double y)
	{
		this.x = x;
		this.y = y;
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
