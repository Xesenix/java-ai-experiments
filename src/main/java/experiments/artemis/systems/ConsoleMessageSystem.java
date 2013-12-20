
package experiments.artemis.systems;

import java.util.Queue;

import org.slf4j.LoggerFactory;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

import experiments.IExperimentViewMediator;
import experiments.artemis.ActiveLogger;
import experiments.artemis.components.ConsoleDebugComponent;
import experiments.artemis.components.MessageComponent;


public class ConsoleMessageSystem extends EntityProcessingSystem
{
	private static final ActiveLogger log = new ActiveLogger(LoggerFactory.getLogger(ConsoleMessageSystem.class));


	private IExperimentViewMediator mediator;


	@Mapper
	ComponentMapper<MessageComponent> messageMapper;


	@Mapper
	ComponentMapper<ConsoleDebugComponent> consoleDebugMapper;


	public ConsoleMessageSystem(IExperimentViewMediator mediator)
	{
		super(Aspect.getAspectForAll(MessageComponent.class));
		this.mediator = mediator;
	}


	protected void process(Entity actor)
	{
		log.setActive(consoleDebugMapper.get(actor) != null && consoleDebugMapper.get(actor).behavior);
		
		log.info("processing entity {}", actor);
		log.info("retriving entity state..");
		
		MessageComponent messagesComponent = messageMapper.get(actor);
		
		log.debug("entity messages {}", messagesComponent);
		
		Queue<String> messages = messagesComponent.getMessages();
		
		while (!messages.isEmpty())
		{
			messageToConsole(actor, messages.remove());
		}
	}
	

	public void messageToConsole(Entity actor, String message)
	{
		String name = actor.toString();
		
		mediator.createMessage(String.format("%s:\n%s", name, message));
	}
}
