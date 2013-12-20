package experiments.artemis.ai.goals;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MessageGoal extends Goal implements IMessageGoal
{
	private String message;


	public MessageGoal()
	{
		
	}
	
	
	public MessageGoal(String message)
	{
		this.message = message;
	}
	
	
	public String getMessage()
	{
		return message;
	}
	
	
	public void setMessage(String message)
	{
		this.message = message;
	}
	
	
	public boolean achived()
	{
		return true;
	}


	public String toString()
	{
		return String.format("[%s@%x {message: %s}]", getClass().getSimpleName(), hashCode(), getMessage());
	}
}
