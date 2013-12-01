
package ai.actors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import xml.IReferenceable;
import ai.world.navigation.IPosition;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class NPC implements IActor, IReferenceable
{
	@XmlID
	@XmlAttribute(name = "id")
	private String instanceId;


	@XmlTransient
	private IPosition position;


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


	public void setPosition(IPosition position)
	{
		this.position = position;

	}


	public IPosition getPosition()
	{
		return position;
	}


	public String toString()
	{
		return String.format("{npc: %s}", name);
	}
}
