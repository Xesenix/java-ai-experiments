package experiments.artemis.components;

import javafx.scene.paint.Color;

import com.artemis.Component;

public class ColorComponent extends Component
{
	private Color color;
	
	
	public ColorComponent(Color color)
	{
		this.setColor(color);
	}


	public Color getColor()
	{
		return color;
	}


	public void setColor(Color color)
	{
		this.color = color;
	}
}
