package experiments.artemis.ai;

import java.util.Map.Entry;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

import experiments.artemis.ai.behaviours.IBehavior;


@Singleton
public class BehaviorMarshaller
{
	@Inject
	private transient Injector injector;
	
	
	public BehaviorDescriptor marshall(Entry<String, IBehavior> entry)
	{
		BehaviorDescriptor descriptor = injector.getInstance(BehaviorDescriptor.class);
		
		descriptor.name = entry.getKey();
		descriptor.behavior = entry.getValue();
		
		return descriptor;
	}
}
