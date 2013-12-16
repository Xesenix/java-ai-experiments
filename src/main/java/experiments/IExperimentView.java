
package experiments;

import experiments.ui.ActorDebugMediator;
import experiments.ui.BehaviorTreeDebugMediator;


public interface IExperimentView
{

	ActorDebugMediator createPositionDebugSprite();

	void removePositionDebugSprite(ActorDebugMediator mediator);

	BehaviorTreeDebugMediator createBehaviorSprite();
}
