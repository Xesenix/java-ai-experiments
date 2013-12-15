package experiments.artemis.ai;

import java.util.Map;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

import experiments.artemis.ai.behaviours.IBehavior;


@Singleton
public class AiMarshaller
{
	@Inject
	private transient Injector injector;
	
	
	public AiDescriptor marshall(AiManager ai)
	{
		AiDescriptor descriptor = injector.getInstance(AiDescriptor.class);
		BehaviorMarshaller behaviorMershaller = injector.getInstance(BehaviorMarshaller.class);
		
		for (Map.Entry<String, IBehavior> entry : ai.getBehaviors().entrySet())
		{
			descriptor.behaviors.add(behaviorMershaller.marshall(entry));
		}
		
		return descriptor;
	}
}
