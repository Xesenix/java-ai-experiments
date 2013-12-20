
package experiments;

import experiments.ui.ActorDebugMediator;
import experiments.ui.BehaviorTreeDebugMediator;


public interface IExperimentViewMediator
{

	ActorDebugMediator createPositionDebugSprite();


	void removePositionDebugSprite(ActorDebugMediator mediator);


	BehaviorTreeDebugMediator createBehaviorSprite();


	void createMessage(String message);
}
