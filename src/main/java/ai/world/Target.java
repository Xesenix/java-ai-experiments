package ai.world;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "target")
@XmlAccessorType(XmlAccessType.FIELD)
public class Target implements IWorldObject
{
	@XmlElement
	private Position position;


	public Position getPosition()
	{
		return position;
	}


	public void setPosition(Position position)
	{
		this.position = position;
	}

	
	public String toString()
	{
		return String.format("{target: %s}", position);
	}
}
