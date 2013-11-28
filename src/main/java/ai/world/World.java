
package ai.world;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class World implements IWorld
{
	@XmlAnyElement
	private Map<String, IWorldObject> worldObjects = new HashMap<String, IWorldObject>();


	public Map<String, IWorldObject> getWorldObjects()
	{
		return worldObjects;
	}


	public void setWorldObjects(Map<String, IWorldObject> worldObjects)
	{
		this.worldObjects = worldObjects;
	}
}
