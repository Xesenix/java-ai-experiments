package experiments.ui;

public interface IDebugSprite
{
	void setPosition(double x, double y);


	void setMediator(DebugSpriteMediator mediator);


	void setCloseSightRange(double range);


	void setCloseFarSighRange(double range);
}
