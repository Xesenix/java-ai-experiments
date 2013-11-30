
package experiments.artemis.components;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.World;


public class BehaviourComponent extends Component
{
	private BehaviourComponent behaviour;
	
	public BehaviourComponent()
	{
	}

	public BehaviourComponent(BehaviourComponent behaviour)
	{
		this.behaviour = behaviour;
	}
	
	public TaskComponent chooseTask(World world, Entity e)
	{
		return this.behaviour.chooseTask(world, e);
	}
}
