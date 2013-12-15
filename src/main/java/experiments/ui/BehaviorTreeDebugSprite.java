package experiments.ui;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.TreeLayout;
import edu.uci.ics.jung.graph.DelegateTree;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Tree;
import edu.uci.ics.jung.graph.util.Pair;
import experiments.artemis.ai.behaviours.IBehavior;
import experiments.artemis.ai.graph.ITreeNode;

public class BehaviorTreeDebugSprite extends Group implements IBehaviorTreeDebugSprite
{
	private BehaviorTreeDebugMediator mediator;
	
	
	private Circle spritePosition;
	
	
	private Map<IBehavior, Node> map = new HashMap<IBehavior, Node>();


	public BehaviorTreeDebugSprite()
	{
		setPickOnBounds(false);
		
		spritePosition = new Circle(0, 0, 5, Color.BLACK);
		spritePosition.setMouseTransparent(false);
		
		getChildren().add(spritePosition);
	}
	
	
	public void buildTree(ITreeNode root)
	{
		DelegateTree<IBehavior, Pair<IBehavior>> tree = new DelegateTree<IBehavior, Pair<IBehavior>>();
		tree.addVertex((IBehavior) root);
		
		buildSubTree(tree, root);
		
		TreeLayout<IBehavior, Pair<IBehavior>> layout = new TreeLayout<IBehavior, Pair<IBehavior>>(tree, 50, 50);
		layout.initialize();
		
		for (IBehavior behavior : tree.getVertices())
		{
			Node node = map.get(behavior);
			
			if (node == null)
			{
				node = new Circle(20, Color.RED);
				
				map.put(behavior, node);
				
				getChildren().add(node);
			}
			
			Point2D pos = layout.transform(behavior);
			
			((Circle) node).setFill(behavior.isReady() ? Color.BLUE : behavior.isRunning() ? Color.YELLOW : behavior.isSuccess() ? Color.GREEN : Color.RED);
			node.setLayoutX(pos.getX());
			node.setLayoutY(pos.getY());
		}
	}


	/**
	 * @param tree
	 * @param root
	 */
	public void buildSubTree(DelegateTree<IBehavior, Pair<IBehavior>> tree, ITreeNode root)
	{
		if (root instanceof IBehavior)
		{
			List<ITreeNode> children = root.getChildren();
			
			if (children != null)
			{
				for (ITreeNode node : children)
				{
					if (node instanceof IBehavior)
					{
						tree.addChild(new Pair<IBehavior>((IBehavior) root, (IBehavior) node), (IBehavior) root, (IBehavior) node);
						
						buildSubTree(tree, node);
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
