
package experiments.ui;

import javafx.event.EventHandler;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
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


	private Line targetVector;


	private Tooltip tip;


	private EventHandler<MouseEvent> mouseEventHandler;


	private Circle target;


	public PositionDebugSprite()
	{
		// transparent to mouse
		setPickOnBounds(false);
		
		spritePosition = new Circle(0, 0, 5, Color.BLACK);
		spritePosition.setMouseTransparent(false);
		
		closeSight = new Circle(0, 0, 5, Color.color(0, 1f, 0, 0.3f));
		closeSight.setMouseTransparent(true);
		
		farShight = new Circle(0, 0, 5, Color.color(1f, 1f, 0, 0.3f));
		farShight.setMouseTransparent(true);
		
		velocity = new Line(0, 0, 0, 0);
		velocity.setMouseTransparent(true);
		
		targetVector = new Line(0, 0, 0, 0);
		targetVector.setMouseTransparent(true);
		targetVector.setStroke(Color.color(0, 0, 1f, 0.3f));
		targetVector.setStrokeWidth(3);
		
		target = new Circle(0, 0, 1, Color.color(0, 1f, 1f, 0.5f));
		target.setMouseTransparent(true);
		target.setStroke(Color.BLUE);

		getChildren().add(farShight);
		getChildren().add(closeSight);
		getChildren().add(target);
		getChildren().add(velocity);
		getChildren().add(targetVector);
		getChildren().add(spritePosition);
		
		tip = new Tooltip("debug");

		mouseEventHandler = new EventHandler<MouseEvent>() {

			public void handle(MouseEvent event)
			{
				if (event.getEventType().equals(MouseEvent.MOUSE_MOVED))
				{
					tip.show(spritePosition, event.getScreenX(), event.getScreenY());
				}
				else if (event.getEventType().equals(MouseEvent.MOUSE_EXITED))
				{
					tip.hide();
				}
			}
		};

		spritePosition.addEventHandler(MouseEvent.ANY, mouseEventHandler);
	}


	public void setLabel(String string)
	{
		tip.setText(string);
	}


	public void setPosition(double x, double y)
	{
		setTranslateX(x);
		setTranslateY(y);
	}


	public void setTargetPosition(double x, double y)
	{
		targetVector.setEndX(x - getTranslateX());
		targetVector.setEndY(y - getTranslateY());
		
		target.setCenterX(x - getTranslateX());
		target.setCenterY(y - getTranslateY());
	}


	public void setTargetPositionPrecision(double precision)
	{
		target.setRadius(precision);
	}


	public void showTargetPosition()
	{
		target.setVisible(true);
		targetVector.setVisible(true);
	}


	public void hideTargetPosition()
	{
		target.setVisible(false);
		targetVector.setVisible(false);
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
