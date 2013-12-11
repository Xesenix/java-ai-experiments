
package experiments.artemis.components;

import javafx.scene.paint.Color;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.artemis.Component;


@XmlRootElement(name = "behavior")
public class ColorComponent extends Component
{
	private Color color;


	public ColorComponent()
	{
		color = Color.BLACK;
	}


	public ColorComponent(Color color)
	{
		this.setColor(color);
	}


	@XmlTransient
	public Color getColor()
	{
		return color;
	}


	public void setColor(Color color)
	{
		this.color = color;
	}


	@XmlAttribute(name = "color")
	public String getColorName()
	{
		return color.toString();
	}


	public void setColorName(String color)
	{
		this.color = Color.web(color);
	}


	public String toString()
	{
		return String.format("[%s@%x {color: %s}]", getClass().getSimpleName(), hashCode(), color);
	}
}
