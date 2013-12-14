
package experiments;

import experiments.ui.DebugSpriteMediator;
import experiments.ui.IDebugSprite;


public interface IExperimentView
{

	DebugSpriteMediator createPositionDebugSprite();

	void removePositionDebugSprite(DebugSpriteMediator mediator);
}
