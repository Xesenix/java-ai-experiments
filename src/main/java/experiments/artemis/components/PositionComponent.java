
package experiments.artemis.components;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;

import ai.world.IPosition;

import com.artemis.Component;


@XmlRootElement(name ="position")
@XmlAccessorType(XmlAccessType.FIELD)
public class PositionComponent extends Component
{
	@XmlAnyElement
	private IPosition position;
	
	
	public PositionComponent()
	{
	}


	public PositionComponent(IPosition position)
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
