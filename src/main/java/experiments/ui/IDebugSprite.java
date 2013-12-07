
package experiments.ui;

import javafx.scene.paint.Color;

public interface IDebugSprite
{
	void setPosition(double x, double y);


	void setTargetPosition(double x, double y);


	void showTargetPosition();


	void hideTargetPosition();


	void setMediator(DebugSpriteMediator mediator);


	void setCloseSightRange(double range);


	void setCloseFarSighRange(double range);


	void setRangeColor(Color color);


	void setSpeed(double speed);


	void setDirection(double direction);


	void setLabel(String string);


	void setTargetPositionPrecision(double precision);


	void setShape(double[] vertices);
}
