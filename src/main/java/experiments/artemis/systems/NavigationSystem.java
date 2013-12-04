
package experiments.artemis.systems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.world.IMetric;
import ai.world.IPosition;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.systems.VoidEntitySystem;
import com.artemis.utils.ImmutableBag;

import experiments.artemis.ActiveLogger;
import experiments.artemis.ai.behaviours.IPositionGoal;
import experiments.artemis.ai.strategy.TranslateTo;
import experiments.artemis.ai.world2d.EuclideanMetric2D;
import experiments.artemis.ai.world2d.Position;
import experiments.artemis.components.ConsoleDebugComponent;
import experiments.artemis.components.NearDistanceComponent;
import experiments.artemis.components.PositionComponent;


public class NavigationSystem extends VoidEntitySystem
{
	private static final ActiveLogger log = new ActiveLogger(LoggerFactory.getLogger(NavigationSystem.class));
	
	
	private static final double PRECISION = 1f;


	@Mapper
	ComponentMapper<PositionComponent> pm;


	@Mapper
	ComponentMapper<NearDistanceComponent> dm;


	@Mapper
	ComponentMapper<ConsoleDebugComponent> cdm;


	private IMetric metric;


	public NavigationSystem(IMetric metric)
	{
		this.metric = metric;
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


	protected boolean checkProcessing()
	{
		return false;
	}


	protected void processSystem()
	{
	}
}
