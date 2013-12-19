
package experiments.artemis.ai.strategy;

import org.slf4j.LoggerFactory;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;

import experiments.artemis.ActiveLogger;
import experiments.artemis.ai.goals.IPositionGoal;
import experiments.artemis.ai.tasks.ITask;
import experiments.artemis.ai.tasks.NavigationTask;
import experiments.artemis.ai.world2d.Position;
import experiments.artemis.components.ConsoleDebugComponent;
import experiments.artemis.components.DesiredPositionComponent;
import experiments.artemis.components.PositionComponent;
import experiments.artemis.systems.NavigationSystem;


public class NearCenterOfMassDestination implements IStrategy
{
	private static final ActiveLogger log = new ActiveLogger(LoggerFactory.getLogger(NearCenterOfMassDestination.class));
	
	
	private double near;


	private double precision;
	
	
	public NearCenterOfMassDestination(double near, double precision)
	{
		this.near = near;
		this.precision = precision;
	}


	public boolean canPerform(World world, Entity e, ITask task)
	{
		ComponentMapper<PositionComponent> pm = world.getMapper(PositionComponent.class);

		PositionComponent position = pm.get(e);

		return position != null && task instanceof NavigationTask;
	}


	public boolean perform(World world, Entity actor, ITask task)
	{
		log.setActive(actor.getComponent(ConsoleDebugComponent.class) != null && actor.getComponent(ConsoleDebugComponent.class).strategy);

		NavigationSystem navigation = world.getSystem(NavigationSystem.class);
		
		task.setActor(actor);
		
		if (task instanceof NavigationTask)
		{
			IPositionGoal goal = (IPositionGoal) ((NavigationTask) task).getGoals();
			
			log.debug("goal {}", goal);
			
			Position target = (Position) goal.getTarget();
			
			log.debug("goal target position {}", target);

			if (target != null)
			{
				ComponentMapper<DesiredPositionComponent> dpm = world.getMapper(DesiredPositionComponent.class);
				DesiredPositionComponent targetComponent = dpm.get(actor);
				Position oldTarget = null;
				
				if (targetComponent == null)
				{
					log.debug("new desired position");
					
					oldTarget = new Position(target);
					targetComponent = new DesiredPositionComponent(oldTarget);
					actor.addComponent(targetComponent);
					actor.changedInWorld();
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
					actor.removeComponent(targetComponent);
					actor.changedInWorld();
					
					return true;
				}
				
				if (navigation.atPoint(actor, oldTarget, precision))
				{
					// finished strategy step
				}
				
				targetComponent.setPrecision(precision);
				
				oldTarget.set(target.getX() + (0.5 - Math.random()) * near, target.getY() + (0.5 - Math.random()) * near);
				
				log.debug("position {}", actor.getComponent(PositionComponent.class));
				log.debug("entity desired position {}", targetComponent);
				
				return navigation.atPoint(actor, oldTarget, targetComponent.getPrecision());
			}
		}

		return true;
	}

	
	public String toString()
	{
		return String.format("[%s@%x {precision: %s}]", getClass().getSimpleName(), hashCode(), precision);
	}
}
