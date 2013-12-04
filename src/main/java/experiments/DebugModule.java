
package experiments;

import com.google.inject.AbstractModule;

import experiments.ui.IDebugSprite;
import experiments.ui.PositionDebugSprite;


public class DebugModule extends AbstractModule
{

	public void configure()
	{
		bind(IDebugSprite.class).to(PositionDebugSprite.class);
	}

}
