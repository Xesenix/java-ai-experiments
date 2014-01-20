
package experiments.ui;

public class ActorDebugMediator
{
	private IActorDebugSprite sprite;


	public ActorDebugMediator()
	{
	}


	public void setLabel(String string)
	{
		sprite.setLabel(string);
	}


	public void setPosition(double x, double y)
	{
		sprite.setPosition(x, y);
	}


	public void setTargetPosition(double x, double y)
	{
		sprite.setTargetPosition(x, y);
	}


	public void setTargetPositionPrecision(double precision)
	{
		sprite.setTargetPositionPrecision(precision);
	}


	public void setCloseSightRange(double range)
	{
		sprite.setCloseSightRange(range);
	}


	public void setCloseFarSighRange(double range)
	{
		sprite.setCloseFarSighRange(range);
	}


	public void setRangeColor(String string)
	{
		sprite.setRangeColor(string);
	}


	public void setSpeed(double speed)
	{
		sprite.setSpeed(speed);
	}


	public void setDirection(double direction)
	{
		sprite.setDirection(direction);
	}


	public void setView(IActorDebugSprite sprite)
	{
		this.sprite = sprite;
		this.sprite.setMediator(this);
	}


	public void showTargetPosition()
	{
		sprite.showTargetPosition();
	}


	public void hideTargetPosition()
	{
		sprite.hideTargetPosition();
	}


	public void setShape(double[] vertices)
	{
		sprite.setShape(vertices);
	}


	public void setHealth(double precentage)
	{
		sprite.setHealth(precentage);
	}


	public void showDamageTaken(Double dmg)
	{
		
	}


	public IActorDebugSprite getSprite()
	{
		return this.sprite;
	}
}
