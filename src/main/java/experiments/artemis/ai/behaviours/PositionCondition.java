
package experiments.artemis.ai.behaviours;

import com.artemis.Entity;
import com.artemis.World;


public class PositionCondition extends Condition
{
	private IPositionGoal goal;


	public PositionCondition(IPositionGoal goal)
	{
		this.goal = goal;
	}


	public boolean isCompleted(World world, Entity e)
	{
		return goal.achived(world, e);
	}
}
