package experiments.artemis.systems;

import org.slf4j.LoggerFactory;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.Bag;

import experiments.IExperimentView;
import experiments.artemis.ActiveLogger;
import experiments.artemis.ai.AiManager;
import experiments.artemis.ai.behaviours.IBehavior;
import experiments.artemis.ai.graph.ITreeNode;
import experiments.artemis.components.BehaviorComponent;
import experiments.ui.BehaviorTreeDebugMediator;

public class DebugAiSystem extends EntityProcessingSystem
{
	private static final ActiveLogger log = new ActiveLogger(LoggerFactory.getLogger(DebugAiSystem.class));
	
	
	@Mapper
	ComponentMapper<BehaviorComponent> behaviorMapper;
	
	
	private IExperimentView view;


	private Bag<BehaviorTreeDebugMediator> mediatorByEntity = new Bag<BehaviorTreeDebugMediator>();
	
	
	public DebugAiSystem(IExperimentView view)
	{
		super(Aspect.getAspectForAll(BehaviorComponent.class));
		this.view = view;
	}
	
	
	protected void process(Entity entity)
	{
		BehaviorTreeDebugMediator mediator = mediatorByEntity.get(entity.getId());

		if (mediator == null)
		{
			mediator = view.createBehaviorSprite();
			mediatorByEntity.set(entity.getId(), mediator);
		}
		
		BehaviorComponent behaviorComponent = behaviorMapper.get(entity);
		
		IBehavior behavior = world.getManager(AiManager.class).getBehaviors().get(behaviorComponent.getName());
		
		behavior.setActor(entity);
		
		mediator.updateBehaviorTree((ITreeNode) behavior);
		
		DebugActorSystem debugActorSystem = world.getSystem(DebugActorSystem.class);
		
		if (debugActorSystem != null)
		{
			mediator.updateEntityDescription(debugActorSystem.getEntityDescription(entity));
		}
	}

}
