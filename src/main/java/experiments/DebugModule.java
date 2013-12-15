
package experiments;

import com.google.inject.AbstractModule;

import experiments.ui.BehaviorTreeDebugSprite;
import experiments.ui.IActorDebugSprite;
import experiments.ui.IBehaviorTreeDebugSprite;
import experiments.ui.ActorDebugSprite;


public class DebugModule extends AbstractModule
{

	public void configure()
	{
		bind(IActorDebugSprite.class).to(ActorDebugSprite.class);
		bind(IBehaviorTreeDebugSprite.class).to(BehaviorTreeDebugSprite.class);
	}

}
