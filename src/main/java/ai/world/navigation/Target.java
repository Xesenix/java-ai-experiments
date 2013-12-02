
package ai.world.navigation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;

import ai.world.IPosition;
import ai.world.IWorldObject;


@XmlRootElement(name = "target")
@XmlAccessorType(XmlAccessType.FIELD)
public class Target implements IWorldObject
{
	@XmlAnyElement(lax = true)
	private IPosition position;


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
		return String.format("{target: %s}", position);
	}
}
