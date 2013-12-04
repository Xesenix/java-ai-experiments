
package experiments.artemis.components;

import ai.world.IPosition;

import com.artemis.Entity;
import com.artemis.World;

import experiments.artemis.ai.behaviours.IPositionGoal;
import experiments.artemis.systems.NavigationSystem;


public class NearPositionGoal extends PositionComponent implements IPositionGoal
{
	public NearPositionGoal(IPosition position)
	{
		super(position);
	}


	public IPosition getTarget(World world, Entity e)
	{
		return getPosition();
	}


	public boolean achived(World world, Entity e)
	{
		NavigationSystem navigation = world.getSystem(NavigationSystem.class);

		return navigation.nearPoint(e, getTarget(world, e));
	}
}
