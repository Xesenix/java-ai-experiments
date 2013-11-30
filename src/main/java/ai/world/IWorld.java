package ai.world;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import ai.actions.IAction;
import ai.actions.MoveTo;
import ai.actors.IActor;
import ai.actors.NPC;
import ai.world.navigation.Target;

@XmlJavaTypeAdapter(xml.AnyTypeAdapter.class)
public interface IWorld
{

	NPC createNpcActor();

	Collection<? extends IActor> getActors();

	void setActors(List<IActor> actors);

	Map<String,IWorldObject> getWorldObjects();

	void setWorldObjects(Map<String,IWorldObject> objects);

	Collection<IAction> getActions();

	void setActions(List<IAction> actions);

	Target createTarget(String name);

	MoveTo createMoveAction();

}
