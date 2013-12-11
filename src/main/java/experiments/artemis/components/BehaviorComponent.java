
package experiments.artemis.components;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.artemis.Component;



@XmlRootElement(name ="behavior")
@XmlAccessorType(XmlAccessType.FIELD)
public class BehaviorComponent extends Component
{
	@XmlAttribute
	private String name;


	public BehaviorComponent()
	{
	}


	public BehaviorComponent(String name)
	{
		setName(name);
	}


	public String getName()
	{
		return name;
	}


	public void setName(String name)
	{
		this.name = name;
	}


	public String toString()
	{
		return String.format("[%s@%x {behavior: %s}]", getClass().getSimpleName(), hashCode(), name);
	}
}
