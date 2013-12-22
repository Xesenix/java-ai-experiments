
package experiments.ui;

import experiments.artemis.ai.graph.ITreeNode;

public class BehaviorTreeDebugMediator
{
	private IBehaviorTreeDebugSprite sprite;


	public void setView(IBehaviorTreeDebugSprite sprite)
	{
		this.sprite = sprite;
	}


	public void updateBehaviorTree(ITreeNode root)
	{
		sprite.updateTree(root);
	}


	public void updateEntityDescription(String description)
	{
		sprite.updateEntityDescription(description);
	}
}
