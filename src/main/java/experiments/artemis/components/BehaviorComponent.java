
package experiments.artemis.components;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.World;

import experiments.artemis.ai.behaviours.IBehavior;
import experiments.artemis.ai.behaviours.IContextAware;


public class BehaviorComponent extends Component implements IContextAware
{
	private IBehavior root;


	public BehaviorComponent()
	{
	}


	public BehaviorComponent(IBehavior behaviour)
	{
		root = behaviour;
	}


	public void run()
	{
		root.run();
	}


	public void reset()
	{
		root.reset();
	}


	public void setContext(World world, Entity entity)
	{
		root.setContext(world, entity);
	}


	public String toString()
	{
		return String.format("[%s@%x {behavior: %s}]", getClass().getSimpleName(), hashCode(), root);
	}
}
