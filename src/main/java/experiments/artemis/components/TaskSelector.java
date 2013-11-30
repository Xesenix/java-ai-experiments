
package experiments.artemis.components;

import java.util.List;

import com.artemis.Entity;
import com.artemis.World;


public class TaskSelector extends BehaviourComponent
{
	private BehaviourComponent behaviours[];


	private TaskComponent running;


	public TaskComponent chooseTask(World world, Entity e)
	{
		TaskComponent task;
		
		for (int i = 0; i < behaviours.length; i++)
		{
			task = behaviours[i].chooseTask(world, e);

			if (task != null)
			{
				return task;
			}
		}

		return null;
	}


	public BehaviourComponent[] getBehaviours()
	{
		return behaviours;
	}


	public void setBehaviours(BehaviourComponent... behaviours)
	{
		this.behaviours = behaviours;
	}


	public void setBehaviours(List<BehaviourComponent> behavioursList)
	{
		this.behaviours = behavioursList.toArray(new BehaviourComponent[0]);
	}
}
