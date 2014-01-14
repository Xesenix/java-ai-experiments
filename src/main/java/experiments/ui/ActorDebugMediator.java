
package experiments.ui;


public class ActorDebugMediator
{
	private IActorDebugSprite sprite;


	public ActorDebugMediator()
	{
	}


	public void setLabel(String string)
	{
		this.sprite.setLabel(string);
	}


	public void setPosition(double x, double y)
	{
		this.sprite.setPosition(x, y);
	}


	public void setTargetPosition(double x, double y)
	{
		this.sprite.setTargetPosition(x, y);
	}


	public void setTargetPositionPrecision(double precision)
	{
		this.sprite.setTargetPositionPrecision(precision);
	}


	public void setCloseSightRange(double range)
	{
		this.sprite.setCloseSightRange(range);
	}


	public void setCloseFarSighRange(double range)
	{
		this.sprite.setCloseFarSighRange(range);
	}


	public void setRangeColor(String string)
	{
		this.sprite.setRangeColor(string);
	}


	public void setSpeed(double speed)
	{
		this.sprite.setSpeed(speed);
	}


	public void setDirection(double direction)
	{
		this.sprite.setDirection(direction);
	}


	public void setView(IActorDebugSprite sprite)
	{
		this.sprite = sprite;
		this.sprite.setMediator(this);
	}


	public void showTargetPosition()
	{
		this.sprite.showTargetPosition();
	}


	public void hideTargetPosition()
	{
		this.sprite.hideTargetPosition();
	}


	public void setShape(double[] vertices)
	{
		this.sprite.setShape(vertices);
	}


	public void setHealth(double precentage)
	{
		this.sprite.setHealth(precentage);
	}


	public IActorDebugSprite getSprite()
	{
		return this.sprite;
	}
}
