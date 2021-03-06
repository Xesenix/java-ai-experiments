package experiments.artemis.components;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.artemis.Component;

import experiments.artemis.damage.Damage;


@XmlRootElement(name = "health")
public class HealthComponent extends Component
{
	private double max;
	
	
	private double current;
	
	
	private transient List<Damage> dmgRequests = new ArrayList<Damage>();
	
	
	public HealthComponent()
	{
	}
	
	
	public HealthComponent(double max)
	{
		setMax(max);
		setCurrent(max);
	}


	@XmlAttribute
	public double getMax()
	{
		return max;
	}


	public void setMax(double max)
	{
		this.max = max;
	}


	@XmlAttribute
	public double getCurrent()
	{
		return current;
	}


	public void setCurrent(double current)
	{
		this.current = current;
	}
	
	
	public void dealDamage(Damage dmg)
	{
		dmgRequests.add(dmg);
	}
	
	
	public List<Damage> getDamageRequests()
	{
		return dmgRequests;
	}


	public String toString()
	{
		return String.format("[%s@%x {current: %.2f; max: %.2f}]", getClass().getSimpleName(), hashCode(), getCurrent(), getMax());
	}
}
