package ai.world;

import java.util.Collection;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import ai.actors.IActor;
import ai.actors.NPC;

@XmlJavaTypeAdapter(xml.AnyTypeAdapter.class)
public interface IWorld
{

	NPC createNpcActor();

	Collection<? extends IActor> getActors();

	Map<String,IWorldObject> getWorldObjects();
	

}
