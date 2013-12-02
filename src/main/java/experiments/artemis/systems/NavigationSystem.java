package experiments.artemis.systems;

import ai.world.IPosition;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.systems.VoidEntitySystem;
import com.artemis.utils.ImmutableBag;

import experiments.artemis.ai.world2d.Position;
import experiments.artemis.components.NearDistanceComponent;
import experiments.artemis.components.PositionComponent;

public class NavigationSystem extends VoidEntitySystem
{
	@Mapper
	ComponentMapper<PositionComponent> pm;
	
	@Mapper
	ComponentMapper<NearDistanceComponent> dm;


	public boolean nearPoint(IPosition target, Entity e)
	{
		PositionComponent worldPosition = pm.get(e);
		NearDistanceComponent nearDistance = dm.get(e);
		
		double near = nearDistance != null ? nearDistance.getNear() : 1;
		
		if (worldPosition != null)
		{
			IPosition position = worldPosition.getPosition();
			
			if (position instanceof Position && target instanceof Position)
			{
				double dx = ((Position) target).getX() - ((Position) position).getX();
				double dy = ((Position) target).getY() - ((Position) position).getY();
				
				return Math.pow(dx, 2) + Math.pow(dy, 2) < Math.pow(near, 2);
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
