
package experiments.artemis.ai.strategy;

import org.slf4j.LoggerFactory;

import ai.world.IPosition;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.utils.Bag;

import experiments.artemis.ActiveLogger;
import experiments.artemis.ai.behaviours.IPositionGoal;
import experiments.artemis.ai.behaviours.ITask;
import experiments.artemis.ai.behaviours.PositionTask;
import experiments.artemis.ai.world2d.Position;
import experiments.artemis.components.ConsoleDebugComponent;
import experiments.artemis.components.MovementSpeedComponent;
import experiments.artemis.components.PositionComponent;
import experiments.artemis.systems.NavigationSystem;


public class TranslateToCenterOfMassSpeedAvare implements IStrategy
{
	private static final ActiveLogger log = new ActiveLogger(LoggerFactory.getLogger(TranslateToCenterOfMassSpeedAvare.class));
	
	
	private Bag<Position> targetForEntity = new Bag<Position>();


	public boolean canPerform(World world, Entity e, ITask task)
	{
		ComponentMapper<PositionComponent> pm = world.getMapper(PositionComponent.class);

		PositionComponent position = pm.get(e);

		return position != null && task instanceof PositionTask;
	}


	public boolean perform(World world, Entity e, ITask task)
	{
		log.setActive(e.getComponent(ConsoleDebugComponent.class) != null && e.getComponent(ConsoleDebugComponent.class).strategy);

		ComponentMapper<MovementSpeedComponent> msm = world.getMapper(MovementSpeedComponent.class);

		NavigationSystem navigation = world.getSystem(NavigationSystem.class);

		if (task instanceof PositionTask)
		{
			MovementSpeedComponent movementSpeed = msm.get(e);

			double speed = movementSpeed != null ? movementSpeed.getSpeed() : 0;

			log.debug("speed: {}, delta: {}", speed, world.delta);

			double x = 0, y = 0;
			int n = 0;

			for (IPositionGoal goal : ((PositionTask) task).getGoals())
			{
				IPosition pos = goal.getTarget(world, e);

				if (pos instanceof Position)
				{
					x += ((Position) pos).getX();
					y += ((Position) pos).getY();
					n ++;
				}
			}

			if (n > 0)
			{
				Position target = targetForEntity.get(e.getId());
				
				if (target == null)
				{
					target = new Position();
					targetForEntity.set(e.getId(), target);
				}
				
				target.set(x / (double) n, y / (double) n);

				if (!navigation.atPoint(e, target, null))
				{
					navigation.translateTo(e, target, speed * world.delta);
					
					return false;
				}
			}
		}

		return true;
	}

}
