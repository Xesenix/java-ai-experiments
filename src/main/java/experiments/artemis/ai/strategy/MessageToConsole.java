package experiments.artemis.ai.strategy;

import com.artemis.Entity;
import com.artemis.World;

import experiments.artemis.ai.goals.IMessageGoal;
import experiments.artemis.ai.tasks.ITask;
import experiments.artemis.ai.tasks.MessageTask;
import experiments.artemis.ai.tasks.NavigationTask;
import experiments.artemis.systems.ConsoleMessageSystem;

public class MessageToConsole implements IStrategy
{
	public boolean canPerform(World world, Entity e, ITask task)
	{
		ConsoleMessageSystem system = world.getSystem(ConsoleMessageSystem.class);
		
		return system != null;
	}


	public boolean perform(World world, Entity e, ITask task)
	{
		ConsoleMessageSystem system = world.getSystem(ConsoleMessageSystem.class);
		
		if (task instanceof MessageTask)
		{
			IMessageGoal goal = (IMessageGoal) ((MessageTask) task).getGoals();
			
			system.messageToConsole(e, goal.getMessage());
			
			return true;
		}
		
		return false;
	}

}
