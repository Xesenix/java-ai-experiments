
package ai.behaviour;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;

import ai.actors.IActor;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Scene implements IBehaviour
{
	@XmlID
	@XmlAttribute(name = "id")
	private String instanceId;


	@XmlElementWrapper(name = "actors")
	@XmlAnyElement
	private ArrayList<IActor> actors = new ArrayList<IActor>();


	public void setInstanceId(String id)
	{
		instanceId = id;
	}


	public String getInstanceId()
	{
		return instanceId;
	}


	public List<IActor> getActors()
	{
		return actors;
	}


	public void setActors(List<IActor> actors)
	{
		this.actors = new ArrayList<IActor>(actors);
	}


	public String toString()
	{
		return String.format("behaviour{actors: %s}", actors);
	}


	public void addActor(IActor actor)
	{
		actors.add(actor);
	}
}
