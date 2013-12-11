
package experiments.artemis.ai.strategy;

import org.slf4j.LoggerFactory;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;

import experiments.artemis.ActiveLogger;
import experiments.artemis.ai.goals.IPositionGoal;
import experiments.artemis.ai.tasks.ITask;
import experiments.artemis.ai.tasks.PositionTask;
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


	public boolean perform(World world, Entity entity, ITask task)
	{
		log.setActive(entity.getComponent(ConsoleDebugComponent.class) != null && entity.getComponent(ConsoleDebugComponent.class).strategy);

		NavigationSystem navigation = world.getSystem(NavigationSystem.class);
		
		task.setContext(world, entity);

		if (task instanceof PositionTask)
		{
			IPositionGoal goal = ((PositionTask) task).getGoals();
			
			log.debug("goal {}", goal);
			
			Position target = (Position) goal.getTarget();
			
			log.debug("goal target position {}", target);
			
			if (target != null)
			{
				ComponentMapper<DesiredPositionComponent> dpm = world.getMapper(DesiredPositionComponent.class);
				DesiredPositionComponent targetComponent = dpm.get(entity);
				Position oldTarget = null;
				
				if (targetComponent == null)
				{
					log.debug("new desired position");
					
					oldTarget = new Position(target);
					targetComponent = new DesiredPositionComponent(oldTarget);
					entity.addComponent(targetComponent);
					entity.changedInWorld();
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
				
				if (task.isSuccess())
				{
					entity.removeComponent(targetComponent);
					entity.changedInWorld();
					
					return true;
				}
				
				//targetComponent.setPrecision(Math.hypot(target.getX() - x / (double) n, target.getY() - y / (double) n));
				
				oldTarget.set(target.getX(), target.getY());
				
				log.debug("position {}", entity.getComponent(PositionComponent.class));
				log.debug("entity desired position {}", targetComponent);
				
				return navigation.atPoint(entity, oldTarget, targetComponent.getPrecision());
			}
		}

		return true;
	}

	
	public String toString()
	{
		return String.format("[%s@%x]", getClass().getSimpleName(), hashCode());
	}

}
