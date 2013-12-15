package experiments.ui;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import experiments.artemis.ai.graph.ITreeNode;

public class BehaviorTreeDebugSprite extends Group implements IBehaviorTreeDebugSprite
{
	private BehaviorTreeDebugMediator mediator;
	
	
	private Circle spritePosition;


	public BehaviorTreeDebugSprite()
	{
		setPickOnBounds(false);
		
		spritePosition = new Circle(0, 0, 5, Color.BLACK);
		spritePosition.setMouseTransparent(false);
		
		getChildren().add(spritePosition);
	}
	
	
	public void buildTree(ITreeNode root)
	{

	}


	public void setMediator(BehaviorTreeDebugMediator mediator)
	{
		this.mediator = mediator;
	}

}
