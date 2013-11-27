
package ai;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import ai.actions.IAction;
import ai.actions.Move;
import ai.actors.IActor;
import ai.actors.NPC;

import com.google.inject.Inject;
import com.google.inject.Injector;


@XmlRootElement(name = "enviroment")
@XmlAccessorType(XmlAccessType.FIELD)
public class AIEnviromentFactory
{
	@Inject
	@XmlTransient
	private Injector injector;


	@XmlElementWrapper(name = "actions")
	@XmlAnyElement(lax = true)
	private List<IAction> actions = new ArrayList<IAction>();


	@XmlElementWrapper(name = "actors")
	@XmlAnyElement(lax = true)
	private List<IActor> actors = new ArrayList<IActor>();


	public List<IAction> getActions()
	{
		return actions;
	}


	public void setActions(List<IAction> actions)
	{
		this.actions = actions;
	}


	public List<IActor> getActors()
	{
		return actors;
	}


	public void setActors(List<IActor> actors)
	{
		this.actors = actors;
	}


	public Move creatMoveAction()
	{
		Move move = injector.getInstance(Move.class);

		actions.add(move);

		move.setInstanceId(String.format("move%02d", actions.indexOf(move)));

		return move;
	}


	public NPC creatNpcActor()
	{
		NPC npc = injector.getInstance(NPC.class);

		actors.add(npc);

		npc.setInstanceId(String.format("npc%02d", actors.indexOf(npc)));

		return npc;
	}


	public String toString()
	{
		return String.format("{actions: %s; actors: %s}", actions, actors);
	}
}
