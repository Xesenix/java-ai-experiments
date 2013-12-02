
package experiments.artemis.ai.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;

import experiments.artemis.ai.behaviours.IGoal;
import experiments.artemis.ai.behaviours.IPositionGoal;
import experiments.artemis.components.PositionComponent;
import experiments.artemis.components.PositionGoal;


public class Translate implements IStrategy
{
	private static final Logger log = LoggerFactory.getLogger(Translate.class);
	
	
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
		
		log.debug("current position: {}, target position: {}", position, goal);

		if (position != null && goal instanceof IPositionGoal)
		{
			position.setPosition(((IPositionGoal) goal).getTarget());

			return true;
		}

		return false;
	}

}
