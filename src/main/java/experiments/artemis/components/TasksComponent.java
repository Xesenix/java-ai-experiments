
package experiments.artemis.components;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.artemis.Component;

import experiments.artemis.ai.tasks.ITask;


@XmlRootElement(name ="tasks")
@XmlAccessorType(XmlAccessType.FIELD)
public class TasksComponent extends Component
{
	private transient List<ITask> tasks = new ArrayList<ITask>();


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
