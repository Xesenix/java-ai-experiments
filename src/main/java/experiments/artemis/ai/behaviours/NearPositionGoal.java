
package experiments.artemis.ai.behaviours;

import ai.world.IPosition;

import com.artemis.Entity;
import com.artemis.World;

import experiments.artemis.components.PositionComponent;
import experiments.artemis.systems.NavigationSystem;


public class NearPositionGoal extends PositionComponent implements IPositionGoal
{
	private double precision;


	public NearPositionGoal(IPosition position)
	{
		this(position, 0);
	}


	public NearPositionGoal(IPosition position, double precision)
	{
		super(position);
		this.precision = precision;
	}


	public IPosition getTarget(World world, Entity e)
	{
		return getPosition();
	}


	public double getPrecision()
	{
		return precision;
	}


	public boolean achived(World world, Entity e)
	{
		NavigationSystem navigation = world.getSystem(NavigationSystem.class);

		return navigation.atPoint(e, getTarget(world, e), precision);
	}
}
