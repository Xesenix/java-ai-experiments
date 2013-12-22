package experiments.ui;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.StrokeType;
import edu.uci.ics.jung.algorithms.layout.TreeLayout;
import edu.uci.ics.jung.graph.DelegateTree;
import edu.uci.ics.jung.graph.DirectedOrderedSparseMultigraph;
import edu.uci.ics.jung.graph.util.Pair;
import experiments.artemis.ai.behaviours.IBehavior;
import experiments.artemis.ai.graph.ITreeNode;

public class BehaviorTreeDebugSprite extends Group implements IBehaviorTreeDebugSprite
{
	private BehaviorTreeDebugMediator mediator;
	
	
	private Circle spritePosition;
	
	
	private Map<IBehavior, BehaviorTreeNodeDebugSprite> behaviorToSprite = new HashMap<IBehavior, BehaviorTreeNodeDebugSprite>();
	
	
	private Map<Pair<IBehavior>, CubicCurve> edgeToSprite = new HashMap<Pair<IBehavior>, CubicCurve>();


	private DelegateTree<IBehavior, Pair<IBehavior>> tree;


	private TreeLayout<IBehavior, Pair<IBehavior>> layout;


	private Group edgeLayer;


	private Group nodeLayer;


	private Tooltip tip;


	private EventHandler<MouseEvent> mouseEventHandler;


	public BehaviorTreeDebugSprite()
	{
		setPickOnBounds(false);
		
		spritePosition = new Circle(0, 0, 5, Color.BLACK);
		spritePosition.setMouseTransparent(false);
		
		edgeLayer = new Group();
		nodeLayer = new Group();
		
		getChildren().add(spritePosition);
		getChildren().add(nodeLayer);
		getChildren().add(edgeLayer);
		
		tip = new Tooltip("debug");
		tip.setAutoHide(true);
		tip.setAutoFix(true);

		mouseEventHandler = new EventHandler<MouseEvent>() {

			public void handle(MouseEvent event)
			{
				if (event.getEventType().equals(MouseEvent.MOUSE_MOVED))
				{
					tip.show(getParent(), event.getScreenX(), event.getScreenY());
				}
				else if (event.getEventType().equals(MouseEvent.MOUSE_EXITED))
				{
					tip.hide();
				}
			}
		};

		spritePosition.addEventHandler(MouseEvent.ANY, mouseEventHandler);
	}


	public void updateEntityDescription(String description)
	{
		double x = tip.getX();
		double y = tip.getY();
		
		tip.setText(description);
		tip.setX(x);
		tip.setY(y);
	}
	
	
	public void updateTree(ITreeNode root)
	{
		if (tree == null || !tree.getRoot().equals(root))
		{
			clear();
			
			tree = new DelegateTree<IBehavior, Pair<IBehavior>>(new DirectedOrderedSparseMultigraph<IBehavior, Pair<IBehavior>>());
			tree.addVertex((IBehavior) root);
			
			buildSubTree(root);
			layoutNodes();
		}

		updateNodes();
	}


	/**
	 * 
	 */
	public void layoutNodes()
	{
		layout = new TreeLayout<IBehavior, Pair<IBehavior>>(tree, 20, 20);
		
		Point2D nodePosition, parentPosition, childPosition;
		double dx, dy;
		
		for (Pair<IBehavior> pair : tree.getEdges())
		{
			IBehavior parent = pair.getFirst();
			IBehavior child = pair.getSecond();
			
			CubicCurve sprite = edgeToSprite.get(pair);
			
			parentPosition = layout.transform(parent);
			childPosition = layout.transform(child);
			
			dx = childPosition.getX() - parentPosition.getX();
			dy = childPosition.getY() - parentPosition.getY();
			
			sprite.setStartX(parentPosition.getX());
			sprite.setStartY(parentPosition.getY());
			
			sprite.setEndX(childPosition.getX());
			sprite.setEndY(childPosition.getY());

			sprite.setControlX1(parentPosition.getX());
			sprite.setControlY1(parentPosition.getY() + dy / (Math.abs(dx) / 100 + 1));
			
			sprite.setControlX2(childPosition.getX());
			sprite.setControlY2(childPosition.getY() - dy * (Math.abs(dx) / 100 + 0.5));
		}
		
		for (IBehavior behavior : tree.getVertices())
		{
			BehaviorTreeNodeDebugSprite sprite = behaviorToSprite.get(behavior);
			
			nodePosition = layout.transform(behavior);
			
			sprite.setLayoutX(nodePosition.getX());
			sprite.setLayoutY(nodePosition.getY());
		}
	}


	/**
	 * 
	 */
	public void updateNodes()
	{
		BehaviorTreeNodeDebugSprite nodeSprite;
		
		for (Pair<IBehavior> pair : tree.getEdges())
		{
			IBehavior parent = pair.getFirst();
			nodeSprite = behaviorToSprite.get(parent);
			nodeSprite.update(parent);
			
			IBehavior child = pair.getSecond();
			nodeSprite = behaviorToSprite.get(child);
			nodeSprite.update(child);
			
			CubicCurve edgeSprite = edgeToSprite.get(pair);
			
			edgeSprite.setStrokeWidth(1);
			edgeSprite.setStroke(Color.GRAY);
			
			if (parent.isRunning() && child.isRunning())
			{
				edgeSprite.setStrokeWidth(2);
				edgeSprite.setStroke(Color.GREEN);
			}
		}
	}


	/**
	 * 
	 */
	public void clear()
	{
		nodeLayer.getChildren().clear();
		behaviorToSprite.clear();
		
		edgeLayer.getChildren().clear();
		edgeToSprite.clear();
	}


	/**
	 * @param root
	 */
	public void buildSubTree(ITreeNode root)
	{
		if (root instanceof IBehavior)
		{
			IBehavior parent = (IBehavior) root;
			
			buildNodeSprite(parent);
			
			ArrayList<ITreeNode> children = root.getChildren();
			
			if (children != null)
			{
				int i = 0;
				
				for (ITreeNode node : children)
				{
					if (node instanceof IBehavior)
					{
						IBehavior child = (IBehavior) node;
						
						Pair<IBehavior> edge = new Pair<IBehavior>(parent, child);
						
						tree.addChild(edge, parent, child);
						
						buildSubTree(node);
						buildEdgeSprite(edge);
						
						BehaviorTreeNodeDebugSprite sprite = behaviorToSprite.get(child);
						sprite.setComment(String.format("- index in parent: %d", i++));
					}
				}
			}
		}
	}


	/**
	 * @param behavior
	 * @return
	 */
	protected BehaviorTreeNodeDebugSprite buildNodeSprite(IBehavior behavior)
	{
		BehaviorTreeNodeDebugSprite sprite = new BehaviorTreeNodeDebugSprite();
		
		behaviorToSprite.put(behavior, sprite);
		
		edgeLayer.getChildren().add(sprite);
		
		return sprite;
	}


	/**
	 * @param edge
	 * @return
	 */
	public CubicCurve buildEdgeSprite(Pair<IBehavior> edge)
	{
		CubicCurve sprite = new CubicCurve();
		
		sprite.setStrokeType(StrokeType.CENTERED);
		sprite.setFill(null);
		
		edgeToSprite.put(edge, sprite);
		
		nodeLayer.getChildren().add(sprite);
		
		return sprite;
	}


	public void setMediator(BehaviorTreeDebugMediator mediator)
	{
		this.mediator = mediator;
	}

}
