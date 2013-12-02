
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

import experiments.artemis.ai.behaviours.IPositionGoal;
import experiments.artemis.ai.strategy.Translate;
import experiments.artemis.ai.world2d.EuclideanMetric2D;
import experiments.artemis.ai.world2d.Position;
import experiments.artemis.components.NearDistanceComponent;
import experiments.artemis.components.PositionComponent;


public class NavigationSystem extends VoidEntitySystem
{
	private static final Logger log = LoggerFactory.getLogger(NavigationSystem.class);
	
	
	private static final double PRECISION = 1f;


	@Mapper
	ComponentMapper<PositionComponent> pm;


	@Mapper
	ComponentMapper<NearDistanceComponent> dm;


	private IMetric metric;


	public NavigationSystem(IMetric metric)
	{
		this.metric = metric;
	}


	public boolean nearPoint(Entity e, IPosition target)
	{
		PositionComponent worldPosition = pm.get(e);
		NearDistanceComponent nearDistance = dm.get(e);

		double near = nearDistance != null ? nearDistance.getNear() : PRECISION;

		if (worldPosition != null)
		{
			IPosition position = worldPosition.getPosition();

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


	protected boolean checkProcessing()
	{
		return false;
	}


	@Override
	protected void processSystem()
	{
		// TODO Auto-generated method stub

	}
}