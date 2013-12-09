
package experiments.artemis.components;

import java.util.ArrayList;
import java.util.List;

import com.artemis.Component;

import experiments.artemis.ai.behaviours.ITask;


public class TasksComponent extends Component
{
	private List<ITask> tasks = new ArrayList<ITask>();


	public TasksComponent()
	{
	}


	public TasksComponent(ITask task)
	{
		addTask(task);
	}


	public List<ITask> getTasks()
	{
		return tasks;
	}


	public void setTasks(List<ITask> task)
	{
		this.tasks = task;
	}


	public void addTask(ITask task)
	{
		if (!tasks.contains(task))
		{
			this.tasks.add(task);
		}
	}


	public String toString()
	{
		return String.format("[%s@%x {tasks: (%d)%s}]", getClass().getSimpleName(), hashCode(), getTasks().size(), getTasks());
	}
}
