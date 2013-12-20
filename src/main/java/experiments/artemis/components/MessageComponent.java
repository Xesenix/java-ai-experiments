
package experiments.artemis.components;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.artemis.Component;


@XmlRootElement
public class MessageComponent extends Component
{
	private Queue<String> messages = new LinkedList<String>();
	
	
	public MessageComponent()
	{
	}


	public MessageComponent(String... messages)
	{
		setMessages(messages);
	}


	@XmlElementWrapper(name = "messages")
	@XmlElement(name = "message")
	public Queue<String> getMessages()
	{
		return messages;
	}


	public void setMessages(Queue<String> messages)
	{
		this.messages = messages;
	}


	public void setMessages(String... messagesText)
	{
		Queue<String> messages = new LinkedList<String>(Arrays.asList(messagesText));
		this.messages = messages;
	}


	public String toString()
	{
		return String.format("[%s@%x {messages: %s}]", getClass().getSimpleName(), hashCode(), getMessages());
	}
}
