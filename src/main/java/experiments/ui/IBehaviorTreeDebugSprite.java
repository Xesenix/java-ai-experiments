
package experiments.ui;

import experiments.artemis.ai.graph.ITreeNode;


public interface IBehaviorTreeDebugSprite
{
	void updateTree(ITreeNode root);


	void updateEntityDescription(String description);
}
