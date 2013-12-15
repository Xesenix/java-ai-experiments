package experiments.artemis.ai;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

@Singleton
public class AiUnmarshaller
{
	@Inject
	private transient Injector injector;


	private AiManager ai;


	public void setAi(AiManager ai)
	{
		this.ai = ai;
	}
	
	
	public AiManager getAi()
	{
		if (ai == null)
		{
			ai = injector.getInstance(AiManager.class);
		}
		
		return ai;
	}


	/**
	 * Deserializes model to AI
	 * 
	 * @param descriptor
	 * @return
	 */
	public AiManager unmarshal(AiDescriptor descriptor)
	{
		AiManager ai = getAi();
		
		for (BehaviorDescriptor behavior : descriptor.behaviors)
		{
			ai.setBehavior(behavior.name, behavior.behavior);
		}
		
		return ai;
	}
}
