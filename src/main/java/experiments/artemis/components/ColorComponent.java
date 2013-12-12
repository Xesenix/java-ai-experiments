
package experiments.artemis.components;

import javafx.scene.paint.Color;

import javax.xml.bind.annotation.XmlRootElement;

import com.artemis.Component;


@XmlRootElement(name = "color")
public class ColorComponent extends Component
{
	private String color;


	public ColorComponent()
	{
		color = Color.BLACK.toString();
	}


	public ColorComponent(String color)
	{
		setColor(color);
	}


	public String getColor()
	{
		return color;
	}


	public void setColor(String color)
	{
		this.color = color;
	}


	public String toString()
	{
		return String.format("[%s@%x {color: %s}]", getClass().getSimpleName(), hashCode(), color);
	}
}
