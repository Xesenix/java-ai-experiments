
package experiments.artemis.systems;

import org.slf4j.LoggerFactory;

import ai.world.IPosition;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.Bag;

import experiments.IExperimentView;
import experiments.artemis.ActiveLogger;
import experiments.artemis.ai.world2d.Position;
import experiments.artemis.components.ConsoleDebugComponent;
import experiments.artemis.components.NearDistanceComponent;
import experiments.artemis.components.PositionComponent;
import experiments.ui.DebugSpriteMediator;


public class DebugEntitySystem extends EntityProcessingSystem
{
	private static final ActiveLogger log = new ActiveLogger(LoggerFactory.getLogger(DebugEntitySystem.class));


	@Mapper
	ComponentMapper<PositionComponent> pm;


	@Mapper
	ComponentMapper<NearDistanceComponent> ndm;


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

		PositionComponent position = pm.get(e);

		if (position != null)
		{
			IPosition coordinates = position.getPosition();

			if (coordinates instanceof Position)
			{
				mediator.setPosition(((Position) coordinates).getX(), ((Position) coordinates).getY());
			}
		}

		NearDistanceComponent nearDistance = ndm.get(e);

		if (nearDistance != null)
		{
			mediator.setCloseSightRange(nearDistance.getNear());
		}
	}
}
