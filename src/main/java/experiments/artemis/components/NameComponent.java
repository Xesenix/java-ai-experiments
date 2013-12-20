
package experiments.artemis.components;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.artemis.Component;


@XmlRootElement(name = "name")
public class NameComponent extends Component
{
	private String name;


	public NameComponent()
	{
	}


	public NameComponent(String name)
	{
		setName(name);
	}


	@XmlAttribute
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
		return String.format("[%s@%x {name: %s}]", getClass().getSimpleName(), hashCode(), getName());
	}
}
