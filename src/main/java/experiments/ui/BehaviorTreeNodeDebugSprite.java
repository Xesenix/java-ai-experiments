
package experiments.ui;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import experiments.artemis.ai.behaviours.IBehavior;


public class BehaviorTreeNodeDebugSprite extends Group
{
	private Circle circle = new Circle(5);


	private Tooltip tip;


	private EventHandler<MouseEvent> mouseEventHandler;


	private String comment;


	public BehaviorTreeNodeDebugSprite()
	{
		setPickOnBounds(false);

		circle.setMouseTransparent(false);

		getChildren().add(circle);

		tip = new Tooltip("debug");
		tip.setAutoHide(true);
		tip.setAutoFix(true);

		mouseEventHandler = new EventHandler<MouseEvent>() {

			public void handle(MouseEvent event)
			{
				if (event.getEventType().equals(MouseEvent.MOUSE_MOVED))
				{
					tip.show(getParent(), event.getScreenX(), event.getScreenY());
				}
				else if (event.getEventType().equals(MouseEvent.MOUSE_EXITED))
				{
					tip.hide();
				}
			}
		};

		addEventHandler(MouseEvent.ANY, mouseEventHandler);
	}


	public void setComment(String comment)
	{
		this.comment = comment;
	}


	public void update(IBehavior behavior)
	{
		circle.setFill(behavior.isCompleted()
			? (behavior.isSuccess() ? Color.GREEN : Color.RED)
			: behavior.isRunning()
				? Color.YELLOW
				: behavior.isReady()
					? Color.BLUE
					: Color.BLACK
		);

		if (behavior.isCompleted())
		{
			circle.setStroke(Color.BLACK);
			circle.setStrokeWidth(1);
		}
		else if (behavior.isRunning())
		{
			circle.setStroke(Color.GREEN);
			circle.setStrokeWidth(1);
		}
		else
		{
			circle.setStroke(null);
		}

		tip.setText(String.format("%s:\n%s\n%s\n%s\n%s\ncomment:\n%s",
			behavior,
			behavior.isReady() ? "ready" : "not ready",
			behavior.isRunning() ? "running" : "not running",
			behavior.isCompleted() ? "completed" : "not completed",
			behavior.isSuccess() ? "success" : "fail",
			comment
		));
	}
}
