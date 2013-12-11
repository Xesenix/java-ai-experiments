
package experiments.artemis.components;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.artemis.Component;


@XmlRootElement(name ="debug")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConsoleDebugComponent extends Component
{
	@XmlAttribute
	public boolean active = true;


	@XmlAttribute
	public boolean navigation = false;


	@XmlAttribute
	public boolean debug = false;


	@XmlAttribute
	public boolean strategy = true;


	@XmlAttribute
	public boolean behavior = true;


	@XmlAttribute
	public boolean movement = false;
	
	
	public String toString()
	{
		return String.format("[%s@%x]", getClass().getSimpleName(), hashCode());
	}
}
