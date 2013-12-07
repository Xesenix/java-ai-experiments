
package experiments.ui;

import javafx.scene.paint.Color;

public class DebugSpriteMediator
{
	private IDebugSprite sprite;


	public DebugSpriteMediator()
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


	public void setRangeColor(Color color)
	{
		this.sprite.setRangeColor(color);
	}


	public void setSpeed(double speed)
	{
		this.sprite.setSpeed(speed);
	}


	public void setDirection(double direction)
	{
		this.sprite.setDirection(direction);
	}


	public void setView(IDebugSprite sprite)
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
}
