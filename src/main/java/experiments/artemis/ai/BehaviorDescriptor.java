
package experiments.artemis.ai;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import experiments.artemis.ai.behaviours.IBehavior;


@XmlRootElement(name = "behavior")
@XmlAccessorType(XmlAccessType.FIELD)
public class BehaviorDescriptor
{
	@XmlAttribute
	public String name;
	
	
	@XmlAnyElement(lax = true)
	public IBehavior behavior;
}
