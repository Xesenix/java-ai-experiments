
package experiments.artemis;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import ai.AI;
import ai.world.IPosition;
import ai.world.IWorldDescriptor;
import ai.world.IWorldObjectDescriptor;

import com.artemis.World;
import com.google.inject.AbstractModule;

import experiments.artemis.ai.AiDescriptor;
import experiments.artemis.ai.BehaviorDescriptor;
import experiments.artemis.ai.behaviours.Inverter;
import experiments.artemis.ai.behaviours.Limiter;
import experiments.artemis.ai.behaviours.PrioritySelector;
import experiments.artemis.ai.behaviours.SequenceSelector;
import experiments.artemis.ai.behaviours.Succeeder;
import experiments.artemis.ai.conditions.Condition;
import experiments.artemis.ai.conditions.Not;
import experiments.artemis.ai.goals.Goal;
import experiments.artemis.ai.goals.KeepInAreaGoal;
import experiments.artemis.ai.goals.MessageGoal;
import experiments.artemis.ai.goals.NearPositionGoal;
import experiments.artemis.ai.goals.PositionGoal;
import experiments.artemis.ai.tasks.MessageTask;
import experiments.artemis.ai.tasks.NavigationTask;
import experiments.artemis.ai.tasks.Task;
import experiments.artemis.ai.world2d.Polygon;
import experiments.artemis.ai.world2d.Position;
import experiments.artemis.components.BehaviorComponent;
import experiments.artemis.components.ColorComponent;
import experiments.artemis.components.ConsoleDebugComponent;
import experiments.artemis.components.DesiredPositionComponent;
import experiments.artemis.components.MessageComponent;
import experiments.artemis.components.MovementDirectionComponent;
import experiments.artemis.components.MovementSpeedComponent;
import experiments.artemis.components.NameComponent;
import experiments.artemis.components.NearDistanceComponent;
import experiments.artemis.components.PositionComponent;
import experiments.artemis.components.ShapeComponent;
import experiments.artemis.components.TasksComponent;
import experiments.artemis.world.EntityChangeDescriptor;
import experiments.artemis.world.WorldChangeDescriptor;


public class ArtemisModule extends AbstractModule
{
	public void configure()
	{
		bindEnviroment();
	}


	private void bindEnviroment()
	{
		bind(AI.class);
		bind(World.class);
		bind(IWorldDescriptor.class).to(WorldChangeDescriptor.class);
		bind(IWorldObjectDescriptor.class).to(EntityChangeDescriptor.class);
		bind(IPosition.class).to(Position.class);

		try
		{
			bind(JAXBContext.class).toInstance(JAXBContext.newInstance(
				WorldChangeDescriptor.class,
				EntityChangeDescriptor.class,
				
				AiDescriptor.class,
				BehaviorDescriptor.class,

				Task.class,
				Not.class,
				Condition.class,
				Limiter.class,
				Succeeder.class,
				Inverter.class,
				MessageTask.class,
				NavigationTask.class,
				Goal.class,
				PositionGoal.class,
				MessageGoal.class,
				NearPositionGoal.class,
				KeepInAreaGoal.class,
				
				SequenceSelector.class,
				PrioritySelector.class,
				
				BehaviorComponent.class,
				ConsoleDebugComponent.class,
				ColorComponent.class,
				PositionComponent.class,
				DesiredPositionComponent.class,
				MovementDirectionComponent.class,
				MovementSpeedComponent.class,
				MessageComponent.class,
				NearDistanceComponent.class,
				NameComponent.class,
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
}
