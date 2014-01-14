
package experiments.artemis.systems;

import org.slf4j.LoggerFactory;

import ai.world.IPosition;

import com.artemis.Aspect;
import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.managers.GroupManager;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;

import experiments.IExperimentViewMediator;
import experiments.artemis.ActiveLogger;
import experiments.artemis.ai.world2d.Polygon;
import experiments.artemis.ai.world2d.Position;
import experiments.artemis.components.ColorComponent;
import experiments.artemis.components.ConsoleDebugComponent;
import experiments.artemis.components.DesiredPositionComponent;
import experiments.artemis.components.HealthComponent;
import experiments.artemis.components.MovementDirectionComponent;
import experiments.artemis.components.MovementSpeedComponent;
import experiments.artemis.components.NearDistanceComponent;
import experiments.artemis.components.PositionComponent;
import experiments.artemis.components.ShapeComponent;
import experiments.ui.ActorDebugMediator;


public class DebugActorSystem extends EntityProcessingSystem
{
	private static final ActiveLogger log = new ActiveLogger(LoggerFactory.getLogger(DebugActorSystem.class));


	@Mapper
	ComponentMapper<PositionComponent> positionMapper;


	@Mapper
	ComponentMapper<DesiredPositionComponent> desiredPositionMapper;


	@Mapper
	ComponentMapper<NearDistanceComponent> nearDistanceMapper;


	@Mapper
	ComponentMapper<ShapeComponent> shapeMapper;


	@Mapper
	ComponentMapper<ColorComponent> colorMapper;


	@Mapper
	ComponentMapper<MovementDirectionComponent> movementDirectionMapper;


	@Mapper
	ComponentMapper<MovementSpeedComponent> movementSpeedMapper;


	@Mapper
	ComponentMapper<HealthComponent> healthMapper;


	@Mapper
	ComponentMapper<ConsoleDebugComponent> consoleDebugMapper;


	private IExperimentViewMediator view;


	private Bag<ActorDebugMediator> mediatorByEntity = new Bag<ActorDebugMediator>();


	public DebugActorSystem(IExperimentViewMediator view)
	{
		super(Aspect.getAspectForAll(PositionComponent.class));

		this.view = view;
	}


	protected void process(Entity entity)
	{
		log.setActive(consoleDebugMapper.get(entity) != null && consoleDebugMapper.get(entity).debug);

		log.info("processing entity {}", entity);
		log.info("retriving entity state..");

		ActorDebugMediator mediator = getMediatorForEntity(entity);

		debugInfo(mediator, entity);
		debugPosition(mediator, entity);
		debugDesiredPosition(mediator, entity);
		debugShape(mediator, entity);
		debugNearDistance(mediator, entity);
		debugColor(mediator, entity);
		debugSpeed(mediator, entity);
		debugDirection(mediator, entity);
		debugHealth(mediator, entity);
	}


	/**
	 * @param entity
	 * @return
	 */
	public ActorDebugMediator getMediatorForEntity(Entity entity)
	{
		ActorDebugMediator mediator = mediatorByEntity.get(entity.getId());

		if (mediator == null)
		{
			mediator = view.createPositionDebugSprite();
			mediatorByEntity.set(entity.getId(), mediator);
		}
		
		return mediator;
	}


	/**
	 * @param mediator
	 * @param entity
	 */
	public void debugInfo(ActorDebugMediator mediator, Entity entity)
	{
		mediator.setLabel(getEntityDescription(entity));
	}


	private void debugHealth(ActorDebugMediator mediator, Entity entity)
	{
		HealthComponent health = healthMapper.get(entity);
		
		if (health != null)
		{
			double current = health.getCurrent();
			double max = health.getMax();
	
			mediator.setHealth(current / max);
		}
	}


	/**
	 * @param mediator
	 * @param entity
	 */
	public void debugPosition(ActorDebugMediator mediator, Entity entity)
	{
		PositionComponent position = positionMapper.get(entity);
	
		if (position != null)
		{
			IPosition coordinates = position.getPosition();
	
			if (coordinates instanceof Position)
			{
				mediator.setPosition(((Position) coordinates).getX(), ((Position) coordinates).getY());
			}
		}
	}


	/**
	 * @param mediator
	 * @param entity
	 */
	public void debugDesiredPosition(ActorDebugMediator mediator, Entity entity)
	{
		DesiredPositionComponent targetPosition = desiredPositionMapper.get(entity);
	
		if (targetPosition != null)
		{
			IPosition coordinates = targetPosition.getPosition();
	
			if (coordinates instanceof Position)
			{
				mediator.showTargetPosition();
				mediator.setTargetPosition(((Position) coordinates).getX(), ((Position) coordinates).getY());
			}
			else
			{
				mediator.hideTargetPosition();
			}
	
			mediator.setTargetPositionPrecision(targetPosition.getPrecision());
		}
		else
		{
			mediator.hideTargetPosition();
		}
	}


	/**
	 * @param mediator
	 * @param entity
	 */
	public void debugShape(ActorDebugMediator mediator, Entity entity)
	{
		ShapeComponent shapeComponent = shapeMapper.get(entity);
	
		if (shapeComponent != null)
		{
			IPosition coordinates = shapeComponent.getShape();
	
			if (coordinates instanceof Polygon)
			{
				mediator.setShape(((Polygon) coordinates).getVertices());
			}
		}
	}


	/**
	 * @param mediator
	 * @param entity
	 */
	public void debugNearDistance(ActorDebugMediator mediator, Entity entity)
	{
		NearDistanceComponent nearDistance = nearDistanceMapper.get(entity);
	
		if (nearDistance != null)
		{
			mediator.setCloseSightRange(nearDistance.getNear());
		}
	}


	/**
	 * @param mediator
	 * @param entity
	 */
	public void debugColor(ActorDebugMediator mediator, Entity entity)
	{
		ColorComponent color = colorMapper.get(entity);
	
		if (color != null)
		{
			mediator.setRangeColor(color.getColor());
		}
	}


	/**
	 * @param mediator
	 * @param entity
	 */
	public void debugSpeed(ActorDebugMediator mediator, Entity entity)
	{
		MovementSpeedComponent speed = movementSpeedMapper.get(entity);
	
		if (speed != null)
		{
			mediator.setSpeed(speed.getSpeed());
		}
	}


	/**
	 * @param mediator
	 * @param entity
	 */
	public void debugDirection(ActorDebugMediator mediator, Entity entity)
	{
		MovementDirectionComponent direction = movementDirectionMapper.get(entity);

		if (direction != null)
		{
			mediator.setDirection(direction.getDirectionRadians());
		}
	}


	public String getEntityDescription(Entity e)
	{
		StringBuilder entityComponentsDescription = new StringBuilder();
		Bag<Component> entityComponents = e.getComponents(new Bag<Component>());

		for (Component component : entityComponents)
		{
			entityComponentsDescription.append("\n- ");
			entityComponentsDescription.append(component);
		}

		StringBuilder entityGroupsDescription = new StringBuilder();
		ImmutableBag<String> groups = world.getManager(GroupManager.class).getGroups(e);

		for (String group : groups)
		{
			entityGroupsDescription.append("\n- ");
			entityGroupsDescription.append(group);
		}

		return String.format("entity: %s\ncomponents:%s\ngroups:%s", e.getId(), entityComponentsDescription, entityGroupsDescription);
	}
	
	
	protected void removed(Entity entity)
	{
		ActorDebugMediator mediator = mediatorByEntity.get(entity.getId());
		
		if (mediator != null)
		{
			view.removePositionDebugSprite(mediator);
			
			mediatorByEntity.set(entity.getId(), null);
		}
	}
}
