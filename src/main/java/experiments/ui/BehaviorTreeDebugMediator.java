
package experiments.ui;

import experiments.artemis.ai.graph.ITreeNode;

public class BehaviorTreeDebugMediator
{
	private IBehaviorTreeDebugSprite sprite;


	public void setView(IBehaviorTreeDebugSprite sprite)
	{
		this.sprite = sprite;
	}


	public void update(ITreeNode root)
	{
		sprite.buildTree(root);
	}
}
