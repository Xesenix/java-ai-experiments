
package experiments.ui;


public interface IActorDebugSprite
{
	void setPosition(double x, double y);


	void setTargetPosition(double x, double y);


	void showTargetPosition();


	void hideTargetPosition();


	void setMediator(ActorDebugMediator mediator);


	void setCloseSightRange(double range);


	void setCloseFarSighRange(double range);


	void setRangeColor(String color);


	void setSpeed(double speed);


	void setDirection(double direction);


	void setLabel(String string);


	void setTargetPositionPrecision(double precision);


	void setShape(double[] vertices);


	void setHealth(double precentage);
}
