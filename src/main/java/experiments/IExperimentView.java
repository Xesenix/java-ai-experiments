
package experiments;

import experiments.ui.BehaviorTreeDebugMediator;
import experiments.ui.ActorDebugMediator;


public interface IExperimentView
{

	ActorDebugMediator createPositionDebugSprite();

	void removePositionDebugSprite(ActorDebugMediator mediator);

	BehaviorTreeDebugMediator createBehaviorSprite();
}
