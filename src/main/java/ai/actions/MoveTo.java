
package ai.actions;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;

import xml.IReferenceable;
import ai.world.IPosition;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MoveTo implements IAction, IReferenceable
{
	@XmlID
	@XmlAttribute(name = "id")
	private String instanceId;


	@XmlAnyElement(lax = true)
	private IPosition target;


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


	public IPosition getTarget()
	{
		return target;
	}


	public void setTarget(IPosition target)
	{
		this.target = target;
	}


	public String toString()
	{
		return String.format("{move: %s}", target);
	}
}
