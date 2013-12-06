
package experiments.ui;

import javafx.scene.paint.Color;

public class DebugSpriteMediator
{
	private IDebugSprite sprite;


	public DebugSpriteMediator()
	{
	}


	public void setPosition(double x, double y)
	{
		this.sprite.setPosition(x, y);
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
}
