
package experiments.artemis.components;

import ai.world.IPosition;

import com.artemis.Component;


public class DesiredPositionComponent extends Component
{
	private IPosition position;


	private double precision;


	public DesiredPositionComponent(IPosition position)
	{
		this.setPosition(position);
	}


	public IPosition getPosition()
	{
		return position;
	}


	public void setPosition(IPosition position)
	{
		this.position = position;
	}


	public double getPrecision()
	{
		return precision;
	}


	public void setPrecision(double precision)
	{
		this.precision = precision;
	}


	public String toString()
	{
		return String.format("[%s@%x {position: %s, precision: %s}]", getClass().getSimpleName(), hashCode(), position, precision);
	}
}
