package experiments.ui;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import edu.uci.ics.jung.algorithms.layout.TreeLayout;
import edu.uci.ics.jung.graph.DelegateTree;
import edu.uci.ics.jung.graph.util.Pair;
import experiments.artemis.ai.behaviours.IBehavior;
import experiments.artemis.ai.graph.ITreeNode;

public class BehaviorTreeDebugSprite extends Group implements IBehaviorTreeDebugSprite
{
	private BehaviorTreeDebugMediator mediator;
	
	
	private Circle spritePosition;
	
	
	private Map<IBehavior, BehaviorTreeNodeDebugSprite> map = new HashMap<IBehavior, BehaviorTreeNodeDebugSprite>();


	private DelegateTree<IBehavior, Pair<IBehavior>> tree;


	private TreeLayout<IBehavior, Pair<IBehavior>> layout;


	public BehaviorTreeDebugSprite()
	{
		setPickOnBounds(false);
		
		spritePosition = new Circle(0, 0, 5, Color.BLACK);
		spritePosition.setMouseTransparent(false);
		
		getChildren().add(spritePosition);
	}
	
	
	public void buildTree(ITreeNode root)
	{
		if (tree == null || !tree.getRoot().equals(root))
		{
			tree = new DelegateTree<IBehavior, Pair<IBehavior>>();
			tree.addVertex((IBehavior) root);
			
			for (Map.Entry<IBehavior, BehaviorTreeNodeDebugSprite> entry : map.entrySet())
			{
				getChildren().remove(entry.getValue());
			}
			
			map.clear();
			
			buildSubTree(root);
			
			layout = new TreeLayout<IBehavior, Pair<IBehavior>>(tree, 20, 20);
			layout.initialize();
		}
		
		for (IBehavior behavior : tree.getVertices())
		{
			BehaviorTreeNodeDebugSprite node = map.get(behavior);
			
			if (node == null)
			{
				node = new BehaviorTreeNodeDebugSprite();
				
				map.put(behavior, node);
				
				getChildren().add(node);
			}
			
			Point2D pos = layout.transform(behavior);
			
			//((Circle) node).setFill(behavior.isReady() ? Color.BLUE : behavior.isRunning() ? Color.YELLOW : behavior.isSuccess() ? Color.GREEN : Color.RED);
			//((Circle) node).setFill(behavior.isRunning() ? Color.YELLOW : behavior.isSuccess() ? Color.GREEN : behavior.isReady() ? Color.BLUE : Color.RED);
			
			node.update(behavior);
			node.setLayoutX(pos.getX());
			node.setLayoutY(pos.getY());
		}
	}


	/**
	 * @param root
	 */
	public void buildSubTree(ITreeNode root)
	{
		if (root instanceof IBehavior)
		{
			ArrayList<ITreeNode> children = root.getChildren();
			
			if (children != null)
			{
				for (ITreeNode node : children)
				{
					if (node instanceof IBehavior)
					{
						tree.addChild(new Pair<IBehavior>((IBehavior) root, (IBehavior) node), (IBehavior) root, (IBehavior) node);
						
						buildSubTree(node);
					}
				}
			}
		}
	}


	public void setMediator(BehaviorTreeDebugMediator mediator)
	{
		this.mediator = mediator;
	}

}
