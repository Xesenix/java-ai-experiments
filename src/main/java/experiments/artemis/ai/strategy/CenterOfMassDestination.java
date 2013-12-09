
package experiments.artemis.ai.strategy;

import org.slf4j.LoggerFactory;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;

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
			IPositionGoal goal = ((PositionTask) task).getGoals();
			
			log.debug("goal {}", goal);
			
			Position target = (Position) goal.getTarget(world, e);
			
			log.debug("goal target position {}", target);
			
			if (target != null)
			{
				boolean result = false;
				
				ComponentMapper<DesiredPositionComponent> dpm = world.getMapper(DesiredPositionComponent.class);
				DesiredPositionComponent targetComponent = dpm.get(e);
				Position oldTarget = null;
				
				if (targetComponent == null)
				{
					log.debug("new desired position");
					
					oldTarget = new Position(target);
					targetComponent = new DesiredPositionComponent(oldTarget);
					e.addComponent(targetComponent);
					e.changedInWorld();
				}
				
				if (!(targetComponent.getPosition() instanceof Position))
				{
					oldTarget = new Position();
					targetComponent.setPosition(oldTarget);
				}
				else
				{
					oldTarget = (Position) targetComponent.getPosition();
				}
				
				if (task.isSuccess(world, e))
				{
					e.removeComponent(targetComponent);
					e.changedInWorld();
					
					return true;
				}
				
				//targetComponent.setPrecision(Math.hypot(target.getX() - x / (double) n, target.getY() - y / (double) n));
				
				oldTarget.set(target.getX(), target.getY());
				
				log.debug("position {}", e.getComponent(PositionComponent.class));
				log.debug("entity desired position {}", targetComponent);
				
				return navigation.atPoint(e, oldTarget, targetComponent.getPrecision());
			}
		}

		return true;
	}

	
	public String toString()
	{
		return String.format("[%s@%x]", getClass().getSimpleName(), hashCode());
	}

}
