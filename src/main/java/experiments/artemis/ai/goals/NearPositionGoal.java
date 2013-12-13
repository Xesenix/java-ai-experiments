
package experiments.artemis.ai.goals;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import ai.world.IPosition;
import experiments.artemis.systems.NavigationSystem;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class NearPositionGoal extends Goal implements IPositionGoal
{
	@XmlAnyElement(lax = true)
	private IPosition target;


	@XmlAttribute
	private double precision;


	public NearPositionGoal()
	{
	}


	public NearPositionGoal(IPosition target)
	{
		this(target, 0);
	}


	public NearPositionGoal(IPosition target, double precision)
	{
		setTarget(target);
		setPrecision(precision);
	}


	public boolean achived()
	{
		NavigationSystem navigation = world.getSystem(NavigationSystem.class);

		return navigation.atPoint(entity, target, precision);
	}


	public IPosition getTarget()
	{
		return target;
	}


	public void setTarget(IPosition target)
	{
		this.target = target;
	}


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
