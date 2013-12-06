
package experiments.ui;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;


public class PositionDebugSprite extends Pane implements IDebugSprite
{
	private DebugSpriteMediator mediator;


	private Circle spritePosition;


	private Circle closeSight;


	private Circle farShight;


	private Line velocity;


	private double speed;


	private double direction;


	public PositionDebugSprite()
	{
		spritePosition = new Circle(0, 0, 5, Color.BLACK);
		closeSight = new Circle(0, 0, 5, Color.color(0, 1f, 0, 0.3f));
		farShight = new Circle(0, 0, 5, Color.color(1f, 1f, 0, 0.3f));
		velocity = new Line(0, 0, 0, 0);

		getChildren().add(farShight);
		getChildren().add(closeSight);
		getChildren().add(spritePosition);
		getChildren().add(velocity);
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


	public void setRangeColor(Color color)
	{
		closeSight.setFill(color);
	}


	public void setSpeed(double speed)
	{
		this.speed = speed;
		updateVelocity();
	}


	public void setDirection(double direction)
	{
		this.direction = direction;
		updateVelocity();
	}


	private void updateVelocity()
	{
		velocity.setEndX(speed * Math.cos(direction));
		velocity.setEndY(speed * Math.sin(direction));
	}
}
