
package experiments.artemis.components.behaviours;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.World;

import experiments.artemis.ai.behaviours.IBehavior;
import experiments.artemis.ai.behaviours.ITask;


public class BehaviorComponent extends Component implements IBehavior
{
	private IBehavior behaviour;


	public BehaviorComponent()
	{
	}


	public BehaviorComponent(IBehavior behaviour)
	{
		this.behaviour = behaviour;
	}


	public ITask chooseTask(World world, Entity e)
	{
		return this.behaviour.chooseTask(world, e);
	}


	public String toString()
	{
		return String.format("[%s@%x, {behavior: %s}]", getClass().getSimpleName(), hashCode(), behaviour);
	}
}
