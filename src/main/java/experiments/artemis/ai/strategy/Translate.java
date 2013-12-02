
package experiments.artemis.ai.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.utils.Bag;

import experiments.artemis.ActiveLogger;
import experiments.artemis.ai.behaviours.IGoal;
import experiments.artemis.ai.behaviours.IPositionGoal;
import experiments.artemis.components.ConsoleDebugComponent;
import experiments.artemis.components.MovementSpeedComponent;
import experiments.artemis.components.PositionComponent;
import experiments.artemis.components.PositionGoal;
import experiments.artemis.systems.NavigationSystem;
import experiments.ui.PositionDebugSprite;


public class Translate implements IStrategy
{
	private static final ActiveLogger log = new ActiveLogger(LoggerFactory.getLogger(Translate.class));
	
	
	public boolean canPerform(World world, Entity e, IGoal goal)
	{
		ComponentMapper<PositionComponent> pm = world.getMapper(PositionComponent.class);

		PositionComponent position = pm.get(e);

		return position != null && goal instanceof PositionGoal;
	}


	public boolean perform(World world, Entity e, IGoal goal)
	{
		log.setActive(e.getComponent(ConsoleDebugComponent.class) != null && e.getComponent(ConsoleDebugComponent.class).strategy);
		
		ComponentMapper<MovementSpeedComponent> msm = world.getMapper(MovementSpeedComponent.class);
		
		NavigationSystem navigation = world.getSystem(NavigationSystem.class);
		
		if (goal instanceof IPositionGoal)
		{
			MovementSpeedComponent movementSpeed = msm.get(e);
			
			double speed = movementSpeed != null ? movementSpeed.getSpeed() : 0;
			
			log.debug("speed: {}, delta: {}", speed, world.delta);
			
			return navigation.translateTo(e, ((IPositionGoal) goal).getTarget(), speed * world.delta);
		}

		return false;
	}

}
