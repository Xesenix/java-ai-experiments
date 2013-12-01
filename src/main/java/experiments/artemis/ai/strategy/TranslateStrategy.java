package experiments.artemis.ai.strategy;

import ai.behaviour.IGoal;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;

import experiments.artemis.components.IStrategy;
import experiments.artemis.components.PositionComponent;
import experiments.artemis.components.PositionGoal;
import experiments.artemis.components.PositionUtils;

public class TranslateStrategy implements IStrategy
{

	public boolean canPerform(World world, Entity e, IGoal goal)
	{
		ComponentMapper<PositionComponent> pm = world.getMapper(PositionComponent.class);
		
		PositionComponent position = pm.get(e);
		
		return position != null && goal instanceof PositionGoal;
	}


	public boolean perform(World world, Entity e, IGoal goal)
	{
		ComponentMapper<PositionComponent> pm = world.getMapper(PositionComponent.class);
		
		PositionComponent position = pm.get(e);
		
		if (position != null && goal instanceof PositionGoal)
		{
			if (PositionUtils.distance(position, (PositionGoal) goal) < 10)
			{
				return true;
			}
			
			
		}
		
		return false;
	}

}
