
package experiments;

import com.google.inject.AbstractModule;

import experiments.ui.ActorDebugSprite;
import experiments.ui.BehaviorTreeDebugSprite;
import experiments.ui.IActorDebugSprite;
import experiments.ui.IBehaviorTreeDebugSprite;


public class DebugModule extends AbstractModule
{

	public void configure()
	{
		bind(IActorDebugSprite.class).to(ActorDebugSprite.class);
		bind(IBehaviorTreeDebugSprite.class).to(BehaviorTreeDebugSprite.class);
	}

}
