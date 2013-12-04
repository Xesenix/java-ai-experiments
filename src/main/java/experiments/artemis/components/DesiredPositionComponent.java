package experiments.artemis.components;

import ai.world.IPosition;

import com.artemis.Component;

public class DesiredPositionComponent extends Component
{
	private IPosition position;


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


	public String toString()
	{
		return String.format("[%s@%x {position: %s}]", getClass().getSimpleName(), hashCode(), position);
	}
}
