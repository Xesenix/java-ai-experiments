
package experiments;

import experiments.ui.PositionDebugSprite;
import experiments.ui.DebugSpriteMediator;
import javafx.scene.Node;

public interface IExperimentView
{

	DebugSpriteMediator createPositionDebugSprite();

}
