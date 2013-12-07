
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
import experiments.artemis.ai.world2d.Polygon;
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
				
				if (pos instanceof Polygon)
				{
					double[] vertices = ((Polygon) pos).getVertices();
					int count = vertices.length / 2;
					
					for (int i = 0; i < count; i++)
					{
						x += vertices[2 * i];
						y += vertices[2 * i + 1];
						n ++;
					}
				}
			}

			if (n > 0)
			{
				ComponentMapper<DesiredPositionComponent> dpm = world.getMapper(DesiredPositionComponent.class);
				DesiredPositionComponent targetComponent = dpm.get(e);
				Position target = null;
				
				if (targetComponent == null)
				{
					log.debug("new desired position");
					
					target = new Position();
					targetComponent = new DesiredPositionComponent(target);
					
					target.set(x / (double) n, y / (double) n);
					
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
				
				
				if (task.isSuccess(world, e))
				{
					e.removeComponent(targetComponent);
					e.changedInWorld();
					
					return true;
				}
				
				if (navigation.atPoint(e, target, precision))
				{
					// finished strategy step
				}
				
				targetComponent.setPrecision(precision);
				target.set(x / (double) n + (0.5 - Math.random()) * near, y / (double) n + (0.5 - Math.random()) * near);
				
				log.debug("target position {}", target);
				log.debug("entity desired position {}", targetComponent);
				
				return false;
			}
		}

		return true;
	}

	
	public String toString()
	{
		return String.format("[%s@%x {precision: %s}]", getClass().getSimpleName(), hashCode(), precision);
	}
}
