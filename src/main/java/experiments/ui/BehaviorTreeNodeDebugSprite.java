
package experiments.ui;

import experiments.artemis.ai.behaviours.IBehavior;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class BehaviorTreeNodeDebugSprite extends Group
{
	private Circle circle = new Circle(5);
	private Tooltip tip;
	private EventHandler<MouseEvent> mouseEventHandler;


	public BehaviorTreeNodeDebugSprite()
	{
		setPickOnBounds(false);
		
		circle.setMouseTransparent(false);

		getChildren().add(circle);
		
		tip = new Tooltip("debug");

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


	public void update(IBehavior behavior)
	{
		//circle.setFill(behavior.isReady() ? Color.BLUE : behavior.isRunning() ? Color.YELLOW : behavior.isSuccess() ? Color.GREEN : Color.RED);
		circle.setFill(behavior.isRunning() ? Color.YELLOW : behavior.isSuccess() ? Color.GREEN : behavior.isReady() ? Color.BLUE : Color.RED);
		
		if (behavior.isCompleted())
		{
			circle.setStroke(Color.BLACK);
			circle.setStrokeWidth(2);
		}
		else
		{
			circle.setStroke(null);
		}
		
		tip.setText(String.format("%s\n%s\n%s\n%s", behavior.isReady() ? "ready" : "not ready", behavior.isRunning() ? "running" : "not running", behavior.isCompleted() ? "completed" : "not completed", behavior.isSuccess() ? "success" : "fail"));
	}
}
