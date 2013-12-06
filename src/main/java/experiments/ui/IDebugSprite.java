
package experiments.ui;

import javafx.scene.paint.Color;

public interface IDebugSprite
{
	void setPosition(double x, double y);


	void setMediator(DebugSpriteMediator mediator);


	void setCloseSightRange(double range);


	void setCloseFarSighRange(double range);


	void setRangeColor(Color color);
}
