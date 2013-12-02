
package experiments.artemis.components;

import java.util.Stack;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.World;

import experiments.artemis.ai.behaviours.IBehavior;
import experiments.artemis.ai.behaviours.ITask;


public class BehaviorComponent extends Component implements IBehavior
{
	private IBehavior root;
	
	
	private Stack<IBehavior> parents;


	public BehaviorComponent()
	{
	}


	public BehaviorComponent(IBehavior behaviour)
	{
		this.root = behaviour;
	}


	public ITask chooseTask(World world, Entity e)
	{
		
		return this.root.chooseTask(world, e);
	}


	public boolean isRunning(World world, Entity e)
	{
		return this.root.isRunning(world, e);
	}


	public void reset(World world, Entity e)
	{
		this.root.reset(world, e);
	}


	public String toString()
	{
		return String.format("[%s@%x, {behavior: %s}]", getClass().getSimpleName(), hashCode(), root);
	}
}
