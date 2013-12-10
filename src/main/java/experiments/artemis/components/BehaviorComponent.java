
package experiments.artemis.components;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.World;

import experiments.artemis.ai.behaviours.IBehavior;
import experiments.artemis.ai.behaviours.IContextAware;


public class BehaviorComponent extends Component
{
	private String name;


	public BehaviorComponent()
	{
	}


	public BehaviorComponent(String name)
	{
		setName(name);
	}


	public String getName()
	{
		return name;
	}


	public void setName(String name)
	{
		this.name = name;
	}


	public String toString()
	{
		return String.format("[%s@%x {behavior: %s}]", getClass().getSimpleName(), hashCode(), name);
	}
}
