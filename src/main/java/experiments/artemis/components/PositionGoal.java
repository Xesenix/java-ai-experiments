
package experiments.artemis.components;

import com.artemis.Entity;
import com.artemis.World;

import ai.world.IPosition;
import experiments.artemis.ai.behaviours.IPositionGoal;
import experiments.artemis.systems.NavigationSystem;


public class PositionGoal extends PositionComponent implements IPositionGoal
{
	public PositionGoal(IPosition position)
	{
		super(position);
	}


	public boolean achived()
	{
		return false;
	}


	public IPosition getTarget()
	{
		return getPosition();
	}


	public boolean achived(World world, Entity e)
	{
		NavigationSystem navigation = world.getSystem(NavigationSystem.class);
		
		return navigation.nearPoint(getTarget(), e);
	}
}
