
package ai.actions;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;

import ai.world.Position;
import xml.IReferenceable;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Move implements IAction, IReferenceable
{
	@XmlID
	@XmlAttribute(name = "id")
	private String instanceId;


	@XmlElement
	private Position target;


	public void execute()
	{
		// TODO Auto-generated method stub

	}


	public void setInstanceId(String id)
	{
		this.instanceId = id;

	}


	public String getInstanceId()
	{
		return null;
	}


	public Position getTarget()
	{
		return target;
	}


	public void setTarget(Position target)
	{
		this.target = target;
	}


	public String toString()
	{
		return String.format("{move: %s}", target);
	}
}
