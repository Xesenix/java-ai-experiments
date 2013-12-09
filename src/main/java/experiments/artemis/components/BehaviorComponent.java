
package experiments.artemis.components;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.World;

import experiments.artemis.ai.behaviours.IBehavior;


public class BehaviorComponent extends Component
{
	private IBehavior root;


	public BehaviorComponent()
	{
	}


	public BehaviorComponent(IBehavior behaviour)
	{
		root = behaviour;
	}


	public void run(World world, Entity e)
	{
		root.run(world, e);
	}


	public boolean isRunning(World world, Entity e)
	{
		return root.isRunning(world, e);
	}


	public void reset(World world, Entity e)
	{
		root.reset(world, e);
	}


	public String toString()
	{
		return String.format("[%s@%x {behavior: %s}]", getClass().getSimpleName(), hashCode(), root);
	}
}
