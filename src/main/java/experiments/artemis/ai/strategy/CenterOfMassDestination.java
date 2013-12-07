
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
import experiments.artemis.components.DesiredPositionComponent;
import experiments.artemis.components.PositionComponent;
import experiments.artemis.systems.NavigationSystem;


public class CenterOfMassDestination implements IStrategy
{
	private static final ActiveLogger log = new ActiveLogger(LoggerFactory.getLogger(CenterOfMassDestination.class));


	public boolean canPerform(World world, Entity e, ITask task)
	{
		ComponentMapper<PositionComponent> pm = world.getMapper(PositionComponent.class);

		PositionComponent position = pm.get(e);

		return position != null && task instanceof PositionTask;
	}


	public boolean perform(World world, Entity e, ITask task)
	{
		log.setActive(e.getComponent(ConsoleDebugComponent.class) != null && e.getComponent(ConsoleDebugComponent.class).strategy);

		NavigationSystem navigation = world.getSystem(NavigationSystem.class);

		if (task instanceof PositionTask)
		{
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
				boolean result = false;
				
				ComponentMapper<DesiredPositionComponent> dpm = world.getMapper(DesiredPositionComponent.class);
				DesiredPositionComponent targetComponent = dpm.get(e);
				Position target = null;
				
				if (targetComponent == null)
				{
					log.debug("new desired position");
					
					target = new Position(x / (double) n, y / (double) n);
					targetComponent = new DesiredPositionComponent(target);
					e.addComponent(targetComponent);
					e.changedInWorld();
				}
				
				if (!(targetComponent.getPosition() instanceof Position))
				{
					target = new Position();
					targetComponent.setPosition(target);
				}
				else
				{
					target = (Position) targetComponent.getPosition();
				}
				
				ComponentMapper<PositionComponent> pm = world.getMapper(PositionComponent.class);
				PositionComponent worldPosition = pm.get(e);
				Position position = null;
				
				if (worldPosition.getPosition() instanceof Position)
				{
					position = (Position) targetComponent.getPosition();
				}
				
				if (task.isSuccess(world, e))
				{
					e.removeComponent(targetComponent);
					e.changedInWorld();
					
					return true;
				}
				
				targetComponent.setPrecision(Math.hypot(target.getX() - x / (double) n, target.getY() - y / (double) n) / world.getDelta());
				
				if (navigation.atPoint(e, target, targetComponent.getPrecision()))
				{
					// finished strategy step
					result = true;
				}
				
				
				target.set(x / (double) n, y / (double) n);
				
				log.debug("position {}", e.getComponent(PositionComponent.class));
				log.debug("entity desired position {}", targetComponent);
				
				return result;
			}
		}

		return true;
	}

	
	public String toString()
	{
		return String.format("[%s@%x]", getClass().getSimpleName(), hashCode());
	}

}
