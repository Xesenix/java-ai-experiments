package experiments.ui;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class PositionDebugSprite extends Pane
{
	public PositionDebugSprite()
	{
		final Circle circle = new Circle(0, 0, 15, Color.color(0, 1f, 0, 0.3f));
		//circle.setStroke(Color.web("#000"));
		circle.setStrokeWidth(2f);
		
		getChildren().add(circle);
	}
}
