
package experiments.artemis.systems;

import org.slf4j.LoggerFactory;

import ai.world.IMetric;
import ai.world.IPosition;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.IntervalEntityProcessingSystem;

import experiments.artemis.ActiveLogger;
import experiments.artemis.ai.world2d.Position;
import experiments.artemis.components.ConsoleDebugComponent;
import experiments.artemis.components.DesiredPositionComponent;
import experiments.artemis.components.MovementDirectionComponent;
import experiments.artemis.components.MovementSpeedComponent;
import experiments.artemis.components.NearDistanceComponent;
import experiments.artemis.components.PositionComponent;


public class NavigationSystem extends IntervalEntityProcessingSystem
{
	private static final ActiveLogger log = new ActiveLogger(LoggerFactory.getLogger(NavigationSystem.class));


	private static final double PRECISION = 1f;


	@Mapper
	ComponentMapper<PositionComponent> positionMapper;


	@Mapper
	ComponentMapper<MovementDirectionComponent> movementDirectionMapper;


	@Mapper
	ComponentMapper<MovementSpeedComponent> movementSpeedMapper;


	@Mapper
	ComponentMapper<NearDistanceComponent> nearDistanceMapper;


	@Mapper
	ComponentMapper<DesiredPositionComponent> desiredPositionMapper;


	@Mapper
	ComponentMapper<ConsoleDebugComponent> consoleDebugMapper;


	private IMetric metric;


	public NavigationSystem(IMetric metric, float interval)
	{
		super(Aspect.getAspectForAll(DesiredPositionComponent.class), interval);
		
		this.metric = metric;
	}


	protected void process(Entity e)
	{
		log.setActive(consoleDebugMapper.get(e) != null && consoleDebugMapper.get(e).navigation);

		log.info("process entity {}", e);
		log.info("retriving entity state..");
		
		PositionComponent worldPosition = positionMapper.get(e);
		DesiredPositionComponent targetComponent = desiredPositionMapper.get(e);
		
		if (targetComponent.getPosition() instanceof Position && worldPosition.getPosition() instanceof Position)
		{
			Position position = (Position) worldPosition.getPosition();
			Position target = (Position) targetComponent.getPosition();
			
			MovementSpeedComponent speed = movementSpeedMapper.get(e);
			
			if (speed == null)
			{
				speed = new MovementSpeedComponent();
				e.addComponent(speed);
			}
			
			MovementDirectionComponent direction = movementDirectionMapper.get(e);
			
			if (direction == null)
			{
				direction = new MovementDirectionComponent();
				e.addComponent(direction);
			}
			
			double dx = ((Position) target).getX() - ((Position) position).getX();
			double dy = ((Position) target).getY() - ((Position) position).getY();
			
			direction.changeDirection(Math.atan2(dy, dx), world.getDelta());
			
			// good place for placing some learning algorithm
			double castLength = dx * Math.cos(direction.getDirectionRadians()) + dy * Math.sin(direction.getDirectionRadians());
			
			speed.changeSpeed(castLength, world.getDelta());
		}
	}


	public boolean nearPoint(Entity e, IPosition target)
	{
		log.setActive(consoleDebugMapper.get(e) != null && consoleDebugMapper.get(e).navigation);

		log.info("nearPoint entity {}", e);
		log.info("retriving entity state..");

		PositionComponent worldPosition = positionMapper.get(e);
		NearDistanceComponent nearDistance = nearDistanceMapper.get(e);

		double near = Math.max(nearDistance != null ? nearDistance.getNear() : PRECISION, PRECISION);

		log.debug("near {}", near);

		if (worldPosition != null)
		{
			IPosition position = worldPosition.getPosition();

			log.debug("entity: {} target: {}", position, target);

			if (position instanceof Position && target instanceof Position)
			{
				return metric.distance(position, target) <= near;
			}
		}

		return false;
	}


	public boolean atPoint(Entity e, IPosition target, Double precision)
	{
		log.setActive(consoleDebugMapper.get(e) != null && consoleDebugMapper.get(e).navigation);

		log.info("atPoint entity {}", e);
		log.info("retriving entity state..");

		PositionComponent worldPosition = positionMapper.get(e);

		double near = Math.max(precision != null ? precision : PRECISION, PRECISION);

		log.debug("near {}", near);

		if (worldPosition != null)
		{
			IPosition position = worldPosition.getPosition();

			log.debug("entity: {} target: {}", position, target);

			if (position instanceof Position && target instanceof Position)
			{
				return metric.distance(position, target) <= near;
			}
		}

		return false;
	}
}
