
package experiments;

import experiments.ui.DebugSpriteMediator;


public interface IExperimentView
{

	DebugSpriteMediator createPositionDebugSprite();

	void removePositionDebugSprite(DebugSpriteMediator mediator);
}
