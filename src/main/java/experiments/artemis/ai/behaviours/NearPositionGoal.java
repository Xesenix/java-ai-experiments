
package experiments.artemis.ai.behaviours;

import ai.world.IPosition;

import com.artemis.Entity;
import com.artemis.World;

import experiments.artemis.systems.NavigationSystem;


public class NearPositionGoal extends Goal implements IPositionGoal
{
	private IPosition target;
	
	
	private double precision;


	public NearPositionGoal(IPosition target)
	{
		this(target, 0);
	}


	public NearPositionGoal(IPosition target, double precision)
	{
		this.target = target;
		this.precision = precision;
	}


	public IPosition getTarget(World world, Entity e)
	{
		return target;
	}


	public double getPrecision(World world, Entity e)
	{
		return precision;
	}


	public boolean achived(World world, Entity e)
	{
		NavigationSystem navigation = world.getSystem(NavigationSystem.class);

		return navigation.atPoint(e, target, precision);
	}
}
