
package experiments.artemis.components;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.artemis.Component;


@XmlRootElement(name ="nearDistance")
@XmlAccessorType(XmlAccessType.FIELD)
public class NearDistanceComponent extends Component
{
	@XmlAttribute
	private double near = 0;
	
	
	public NearDistanceComponent()
	{
	}


	public NearDistanceComponent(double near)
	{
		setNear(near);
	}


	public double getNear()
	{
		return near;
	}


	public void setNear(double near)
	{
		this.near = near;
	}


	public String toString()
	{
		return String.format("[%s@%x {near: %.2f}]", getClass().getSimpleName(), hashCode(), near);
	}
}
