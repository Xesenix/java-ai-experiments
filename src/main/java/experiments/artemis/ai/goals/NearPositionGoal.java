
package experiments.artemis.ai.goals;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.artemis.World;

import ai.world.IPosition;
import experiments.artemis.ai.world2d.Position;
import experiments.artemis.systems.NavigationSystem;


@XmlRootElement
public class NearPositionGoal extends PositionGoal
{
	private Position position;


	private double precision;


	public NearPositionGoal()
	{
	}


	public NearPositionGoal(Position target)
	{
		this(target, 0);
	}


	public NearPositionGoal(Position targtPositions, double precision)
	{
		setPosition(targtPositions);
		setPrecision(precision);
	}


	public boolean achived()
	{
		World world = actor.getWorld();
		NavigationSystem navigation = world.getSystem(NavigationSystem.class);

		return navigation.atPoint(actor, position, precision);
	}


	@XmlTransient
	public IPosition getTarget()
	{
		return position;
	}


	@XmlElement
	public Position getPosition()
	{
		return position;
	}


	public void setPosition(Position target)
	{
		this.position = target;
	}


	@XmlAttribute
	public double getPrecision()
	{
		return precision;
	}


	public void setPrecision(double precision)
	{
		this.precision = precision;
	}


	public String toString()
	{
		return String.format("[%s@%x {target: %s, precision: %s}]", getClass().getSimpleName(), hashCode(), getTarget(), getPrecision());
	}
}
