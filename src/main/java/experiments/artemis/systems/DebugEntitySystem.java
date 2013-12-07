
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

import experiments.IExperimentView;
import experiments.artemis.ActiveLogger;
import experiments.artemis.ai.world2d.Position;
import experiments.artemis.components.ColorComponent;
import experiments.artemis.components.ConsoleDebugComponent;
import experiments.artemis.components.DesiredPositionComponent;
import experiments.artemis.components.MovementDirectionComponent;
import experiments.artemis.components.MovementSpeedComponent;
import experiments.artemis.components.NearDistanceComponent;
import experiments.artemis.components.PositionComponent;
import experiments.ui.DebugSpriteMediator;


public class DebugEntitySystem extends EntityProcessingSystem
{
	private static final ActiveLogger log = new ActiveLogger(LoggerFactory.getLogger(DebugEntitySystem.class));


	@Mapper
	ComponentMapper<PositionComponent> pm;


	@Mapper
	ComponentMapper<DesiredPositionComponent> dpm;


	@Mapper
	ComponentMapper<NearDistanceComponent> ndm;


	@Mapper
	ComponentMapper<ColorComponent> cm;


	@Mapper
	ComponentMapper<MovementDirectionComponent> mdm;


	@Mapper
	ComponentMapper<MovementSpeedComponent> msm;


	@Mapper
	ComponentMapper<ConsoleDebugComponent> cdm;


	private IExperimentView view;


	private Bag<DebugSpriteMediator> mediatorByEntity = new Bag<DebugSpriteMediator>();


	public DebugEntitySystem(IExperimentView view)
	{
		super(Aspect.getAspectForAll(PositionComponent.class));

		this.view = view;
	}


	protected void process(Entity e)
	{
		log.setActive(cdm.get(e) != null && cdm.get(e).debug);

		log.info("processing entity {}", e);
		log.info("retriving entity state..");

		DebugSpriteMediator mediator = mediatorByEntity.get(e.getId());

		if (mediator == null)
		{
			mediator = view.createPositionDebugSprite();
			mediatorByEntity.set(e.getId(), mediator);
		}
		
		mediator.setLabel(getEntityDescription(e));

		PositionComponent position = pm.get(e);

		if (position != null)
		{
			IPosition coordinates = position.getPosition();

			if (coordinates instanceof Position)
			{
				mediator.setPosition(((Position) coordinates).getX(), ((Position) coordinates).getY());
			}
		}
		
		DesiredPositionComponent targetPosition = dpm.get(e);
		
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

		NearDistanceComponent nearDistance = ndm.get(e);

		if (nearDistance != null)
		{
			mediator.setCloseSightRange(nearDistance.getNear());
		}

		ColorComponent color = cm.get(e);

		if (color != null)
		{
			mediator.setRangeColor(color.getColor());
		}
		
		MovementSpeedComponent speed = msm.get(e);

		if (speed != null)
		{
			mediator.setSpeed(speed.getSpeed());
		}
		
		MovementDirectionComponent direction = mdm.get(e);

		if (direction != null)
		{
			mediator.setDirection(direction.getDirection());
		}
	}


	private String getEntityDescription(Entity e)
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
}
