package experiments.artemis.ai;

import java.util.HashMap;
import java.util.UUID;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.World;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

import experiments.artemis.ai.behaviours.IBehavior;
import experiments.artemis.world.EntityDescriptor;

@Singleton
public class AiUnmarshaller
{
	@Inject
	private transient Injector injector;


	private AI ai;


	public void setAi(AI ai)
	{
		this.ai = ai;
	}
	
	
	public AI getAi()
	{
		if (ai == null)
		{
			ai = injector.getInstance(AI.class);
		}
		
		return ai;
	}


	/**
	 * Deserializes model to AI
	 * 
	 * @param descriptor
	 * @return
	 */
	public AI unmarshal(AiDescriptor descriptor)
	{
		AI ai = getAi();
		
		for (BehaviorDescriptor behavior : descriptor.behaviors)
		{
			ai.setBehavior(behavior.name, behavior.behavior);
		}
		
		return ai;
	}
}
