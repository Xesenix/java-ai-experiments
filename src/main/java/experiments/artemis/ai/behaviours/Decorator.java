
package experiments.artemis.ai.behaviours;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.artemis.Entity;
import com.artemis.World;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
abstract public class Decorator implements IBehavior
{
	@XmlAnyElement(lax = true)
	private IBehavior behavior;


	protected transient World world;


	protected transient Entity entity;
	
	
	public Decorator()
	{
	}


	public Decorator(IBehavior behavior)
	{
		this.behavior = behavior;
	}


	public void run()
	{
		behavior.run();
	}


	public void reset()
	{
		behavior.reset();
	}


	public boolean isReady()
	{
		return behavior.isReady();
	}


	public boolean isRunning()
	{
		return behavior.isRunning();
	}


	public boolean isSuccess()
	{
		return behavior.isSuccess();
	}


	public boolean isCompleted()
	{
		return behavior.isCompleted();
	}


	public void setContext(World world, Entity entity)
	{
		this.world = world;
		this.entity = entity;
		behavior.setContext(world, entity);
	}


	public IBehavior getBehavior()
	{
		return behavior;
	}


	public void setBehavior(IBehavior behavior)
	{
		this.behavior = behavior;
	}


	public String toString()
	{
		return String.format("[%s@%x, {behavior: %s}]", getClass().getSimpleName(), hashCode(), behavior);
	}
}
