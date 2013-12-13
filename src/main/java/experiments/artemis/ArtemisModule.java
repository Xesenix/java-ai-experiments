
package experiments.artemis;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import ai.AI;
import ai.actions.MoveTo;
import ai.actors.NPC;
import ai.world.IPosition;
import ai.world.IWorldDescriptor;
import ai.world.IWorldObjectDescriptor;

import com.artemis.World;
import com.google.inject.AbstractModule;

import experiments.artemis.ai.AiDescriptor;
import experiments.artemis.ai.BehaviorDescriptor;
import experiments.artemis.ai.behaviours.Counter;
import experiments.artemis.ai.behaviours.PrioritySelector;
import experiments.artemis.ai.behaviours.SequenceSelector;
import experiments.artemis.ai.conditions.Condition;
import experiments.artemis.ai.conditions.Not;
import experiments.artemis.ai.goals.Goal;
import experiments.artemis.ai.goals.KeepInAreaGoal;
import experiments.artemis.ai.goals.NearPositionGoal;
import experiments.artemis.ai.goals.PositionGoal;
import experiments.artemis.ai.tasks.PositionTask;
import experiments.artemis.ai.tasks.Task;
import experiments.artemis.ai.world2d.Polygon;
import experiments.artemis.ai.world2d.Position;
import experiments.artemis.components.BehaviorComponent;
import experiments.artemis.components.ColorComponent;
import experiments.artemis.components.ConsoleDebugComponent;
import experiments.artemis.components.DesiredPositionComponent;
import experiments.artemis.components.MovementDirectionComponent;
import experiments.artemis.components.MovementSpeedComponent;
import experiments.artemis.components.NearDistanceComponent;
import experiments.artemis.components.PositionComponent;
import experiments.artemis.components.ShapeComponent;
import experiments.artemis.components.TasksComponent;
import experiments.artemis.world.EntityDescriptor;
import experiments.artemis.world.WorldDescriptor;


public class ArtemisModule extends AbstractModule
{
	public void configure()
	{
		bindActions();
		bindActors();
		bindEnviroment();
	}


	private void bindEnviroment()
	{
		bind(AI.class);
		bind(World.class);
		bind(IWorldDescriptor.class).to(WorldDescriptor.class);
		bind(IWorldObjectDescriptor.class).to(EntityDescriptor.class);
		bind(IPosition.class).to(Position.class);

		try
		{
			bind(JAXBContext.class).toInstance(JAXBContext.newInstance(
				WorldDescriptor.class,
				EntityDescriptor.class,
				
				AiDescriptor.class,
				BehaviorDescriptor.class,
				
				SequenceSelector.class,
				PrioritySelector.class,
				Counter.class,
				Task.class,
				PositionTask.class,
				Goal.class,
				PositionGoal.class,
				NearPositionGoal.class,
				KeepInAreaGoal.class,
				Condition.class,
				Not.class,
				
				BehaviorComponent.class,
				ConsoleDebugComponent.class,
				ColorComponent.class,
				PositionComponent.class,
				DesiredPositionComponent.class,
				MovementDirectionComponent.class,
				MovementSpeedComponent.class,
				NearDistanceComponent.class,
				ShapeComponent.class,
				TasksComponent.class,
				
				Position.class,
				Polygon.class
			));
		}
		catch (JAXBException e)
		{
			e.printStackTrace();
		}
	}


	private void bindActions()
	{
		bind(MoveTo.class);
	}


	private void bindActors()
	{
		bind(NPC.class);
	}
}
