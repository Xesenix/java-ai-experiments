
package ai;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.bind.annotation.XmlRootElement;

import ai.actions.IAction;
import ai.actions.Move;
import ai.actors.IActor;
import ai.actors.NPC;
import ai.behaviour.IBehaviour;
import ai.behaviour.Scene;

import com.google.inject.Inject;
import com.google.inject.Injector;

@XmlRootElement(name = "enviroment")
@XmlAccessorType(XmlAccessType.FIELD)
public class AI
{
	@Inject
	private transient Injector injector;


	@XmlElementWrapper(name = "actions")
	@XmlAnyElement(lax = true)
	private ArrayList<IAction> actions = new ArrayList<IAction>();


	@XmlElementWrapper(name = "actors")
	@XmlAnyElement(lax = true)
	private ArrayList<IActor> actors = new ArrayList<IActor>();


	@XmlElementWrapper(name = "behaviours")
	@XmlAnyElement(lax = true)
	private ArrayList<IBehaviour> behaviours = new ArrayList<IBehaviour>();


	public ArrayList<IAction> getActions()
	{
		return actions;
	}


	public void setActions(ArrayList<IAction> actions)
	{
		this.actions = actions;
	}


	public ArrayList<IActor> getActors()
	{
		return actors;
	}


	public void setActors(ArrayList<IActor> actors)
	{
		this.actors = actors;
	}


	public Move createMoveAction()
	{
		Move move = injector.getInstance(Move.class);

		actions.add(move);

		move.setInstanceId(String.format("move%02d", actions.indexOf(move)));

		return move;
	}


	public NPC createNpcActor()
	{
		NPC npc = injector.getInstance(NPC.class);

		actors.add(npc);

		npc.setInstanceId(String.format("npc%02d", actors.indexOf(npc)));

		return npc;
	}


	public String toString()
	{
		return String.format("{actions: %s; actors: %s; behaviours: %s}", actions, actors, behaviours);
	}


	public Scene createSceneBehaviour()
	{
		Scene scene = injector.getInstance(Scene.class);
		
		behaviours.add(scene);
		
		scene.setInstanceId(String.format("scene%02d", behaviours.indexOf(scene)));
		
		return scene;
	}
}
