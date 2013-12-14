package experiments.artemis.world;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import ai.world.IWorldDescriptor;

@XmlRootElement(name = "world")
@XmlAccessorType(XmlAccessType.FIELD)
public class WorldChangeDescriptor implements IWorldDescriptor
{
	@XmlElementWrapper(name = "entities")
	@XmlElement(name = "entity")
	public ArrayList<EntityChangeDescriptor> entities = new ArrayList<EntityChangeDescriptor>();
	
	
	@XmlElementWrapper(name = "remove")
	@XmlElement(name = "entity")
	public ArrayList<EntityChangeDescriptor> removeEntities;
}
