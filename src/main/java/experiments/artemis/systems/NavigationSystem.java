
package experiments.artemis.systems;

import org.slf4j.LoggerFactory;

import ai.world.IMetric;
import ai.world.IPosition;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.systems.VoidEntitySystem;

import experiments.artemis.ActiveLogger;
import experiments.artemis.ai.world2d.Position;
import experiments.artemis.components.ConsoleDebugComponent;
import experiments.artemis.components.DesiredPositionComponent;
import experiments.artemis.components.MovementDirectionComponent;
import experiments.artemis.components.MovementSpeedComponent;
import experiments.artemis.components.NearDistanceComponent;
import experiments.artemis.components.PositionComponent;


public class NavigationSystem extends EntityProcessingSystem
{
	private static final ActiveLogger log = new ActiveLogger(LoggerFactory.getLogger(NavigationSystem.class));


	private static final double PRECISION = 1f;


	@Mapper
	ComponentMapper<PositionComponent> pm;


	@Mapper
	ComponentMapper<MovementDirectionComponent> mdm;


	@Mapper
	ComponentMapper<MovementSpeedComponent> msm;


	@Mapper
	ComponentMapper<NearDistanceComponent> dm;


	@Mapper
	ComponentMapper<DesiredPositionComponent> dpm;


	@Mapper
	ComponentMapper<ConsoleDebugComponent> cdm;


	private IMetric metric;


	public NavigationSystem(IMetric metric)
	{
		super(Aspect.getAspectForAll(DesiredPositionComponent.class));
		
		this.metric = metric;
	}


	protected void process(Entity e)
	{
		log.setActive(cdm.get(e) != null);

		log.info("process entity {}", e);
		log.info("retriving entity state..");
		
		PositionComponent worldPosition = pm.get(e);
		DesiredPositionComponent targetComponent = dpm.get(e);
		
		if (targetComponent.getPosition() instanceof Position && worldPosition.getPosition() instanceof Position)
		{
			Position position = (Position) worldPosition.getPosition();
			Position target = (Position) targetComponent.getPosition();
			
			MovementSpeedComponent speed = msm.get(e);
			
			if (speed == null)
			{
				speed = new MovementSpeedComponent();
				e.addComponent(speed);
			}
			
			MovementDirectionComponent direction = mdm.get(e);
			
			if (direction == null)
			{
				direction = new MovementDirectionComponent();
				e.addComponent(direction);
			}
			
			double dx = ((Position) target).getX() - ((Position) position).getX();
			double dy = ((Position) target).getY() - ((Position) position).getY();
			
			direction.setRotation(Math.atan2(dy, dx));
			speed.setSpeed(Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2)));
		}
	}


	public boolean nearPoint(Entity e, IPosition target)
	{
		log.setActive(cdm.get(e) != null);

		log.info("nearPoint entity {}", e);
		log.info("retriving entity state..");

		PositionComponent worldPosition = pm.get(e);
		NearDistanceComponent nearDistance = dm.get(e);

		double near = nearDistance != null ? nearDistance.getNear() : PRECISION;

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
		log.setActive(cdm.get(e) != null);

		log.info("atPoint entity {}", e);
		log.info("retriving entity state..");

		PositionComponent worldPosition = pm.get(e);

		double near = precision != null ? precision : PRECISION;

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


	/**
	 * 
	 * @param e
	 * @param target
	 * @param max - max distance to move towards target
	 * @return
	 */
	public boolean translateTo(Entity e, IPosition target, double max)
	{
		log.setActive(cdm.get(e) != null && cdm.get(e).navigation);

		log.info("translateTo entity {}", e);
		log.info("retriving entity state..");

		PositionComponent worldPosition = pm.get(e);

		log.debug("worldPosition: {}", worldPosition);

		if (worldPosition != null)
		{
			IPosition position = worldPosition.getPosition();

			log.debug("coordinates: {}", position);

			if (position instanceof Position && target instanceof Position)
			{
				double distance = metric.distance(position, target);

				log.debug("distance: {}/{}", distance, max);

				if (max <= 0 || distance < max)
				{
					((Position) position).set(((Position) target).getX(), ((Position) target).getY());

					return true;
				}
				else
				{
					double step = max / distance;

					log.debug("step: {}", step);

					// TODO get world navigation space position closest to position on path between current position and target position
					// simplified:
					double dx = (((Position) target).getX() - ((Position) position).getX()) * step + ((Position) position).getX();
					double dy = (((Position) target).getY() - ((Position) position).getY()) * step + ((Position) position).getY();

					((Position) position).set(dx, dy);
				}
			}
		}

		return false;
	}


	/**
	 * 
	 * @param e
	 * @param target
	 * @param max - max distance to move towards target
	 * @return
	 */
	public boolean translateFrom(Entity e, IPosition target, double max)
	{
		log.setActive(cdm.get(e) != null && cdm.get(e).navigation);

		log.info("translateFrom entity {}", e);
		log.info("retriving entity state..");

		PositionComponent worldPosition = pm.get(e);

		log.debug("worldPosition: {}", worldPosition);

		if (worldPosition != null)
		{
			IPosition position = worldPosition.getPosition();

			log.debug("coordinates: {}", position);

			if (position instanceof Position && target instanceof Position)
			{
				double step = max;

				// TODO get world navigation space position closest to position on path between current position and target position
				// simplified:
				double dx = (((Position) position).getX() - ((Position) target).getX()) * max + ((Position) position).getX();
				double dy = (((Position) position).getY() - ((Position) target).getY()) * max + ((Position) position).getY();

				((Position) position).set(dx, dy);
			}
		}

		return false;
	}
}
