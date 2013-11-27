
package experiment.ai.actors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import experiment.world.Position;
import experiment.xml.IReferenceable;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class NPC implements IActor, IReferenceable
{
	@XmlID
	@XmlAttribute(name = "id")
	private String instanceId;


	@XmlTransient
	private Position position;
	
	
	@XmlAttribute
	private String name;


	public void setInstanceId(String id)
	{
		instanceId = id;
	}


	public String getInstanceId()
	{
		return instanceId;
	}


	public String getName()
	{
		return name;
	}


	public void setName(String name)
	{
		this.name = name;
	}


	public void setPosition(Position position)
	{
		this.position = position;

	}


	public Position getPosition()
	{
		return null;
	}


	public String toString()
	{
		return String.format("{npc: %s}", name);
	}
}
