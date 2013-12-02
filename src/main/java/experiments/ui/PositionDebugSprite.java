
package experiments.ui;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class PositionDebugSprite extends Pane implements IDebugSprite
{
	private DebugSpriteMediator mediator;


	private Circle spritePosition;


	private Circle closeSight;


	private Circle farShight;


	public PositionDebugSprite()
	{
		spritePosition = new Circle(0, 0, 5, Color.BLACK);
		closeSight = new Circle(0, 0, 5, Color.color(0, 1f, 0, 0.3f));
		farShight = new Circle(0, 0, 5, Color.color(1f, 1f, 0, 0.3f));

		getChildren().add(farShight);
		getChildren().add(closeSight);
		getChildren().add(spritePosition);
	}


	public void setPosition(double x, double y)
	{
		setTranslateX(x);
		setTranslateY(y);
	}


	public void setMediator(DebugSpriteMediator mediator)
	{
		this.mediator = mediator;
	}


	public void setCloseSightRange(double range)
	{
		closeSight.setRadius(range);
	}


	public void setCloseFarSighRange(double range)
	{
		farShight.setRadius(range);
	}
}
