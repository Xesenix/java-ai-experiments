
package experiments.artemis.world;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;

import ai.world.IWorldObjectDescriptor;

import com.artemis.Component;


@XmlRootElement(name = "entity")
@XmlAccessorType(XmlAccessType.FIELD)
public class EntityDescriptor implements IWorldObjectDescriptor
{
	@XmlElementWrapper(name = "components")
	@XmlAnyElement(lax = true)
	public ArrayList<Component> components = new ArrayList<Component>();


	@XmlID
	@XmlAttribute
	public String id;


	@XmlAttribute
	public ArrayList<String> groups = new ArrayList<String>();
}
