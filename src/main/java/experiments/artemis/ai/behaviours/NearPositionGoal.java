
package experiments.artemis.ai.behaviours;

import ai.world.IPosition;

import com.artemis.Entity;
import com.artemis.World;

import experiments.artemis.components.PositionComponent;
import experiments.artemis.systems.NavigationSystem;


public class NearPositionGoal extends PositionComponent implements IPositionGoal
{
	private double distance;


	public NearPositionGoal(IPosition position)
	{
		this(position, 0);
	}


	public NearPositionGoal(IPosition position, double distance)
	{
		super(position);
		this.distance = distance;
	}


	public IPosition getTarget(World world, Entity e)
	{
		return getPosition();
	}


	public boolean achived(World world, Entity e)
	{
		NavigationSystem navigation = world.getSystem(NavigationSystem.class);

		return navigation.atPoint(e, getTarget(world, e), distance);
	}
}
