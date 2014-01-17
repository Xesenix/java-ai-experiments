package experiments.artemis.systems;

import org.slf4j.LoggerFactory;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.IntervalEntityProcessingSystem;

import experiments.artemis.ActiveLogger;
import experiments.artemis.components.ConsoleDebugComponent;
import experiments.artemis.components.HealthComponent;

public class DeathSystem extends IntervalEntityProcessingSystem
{
	private static final ActiveLogger log = new ActiveLogger(LoggerFactory.getLogger(DeathSystem.class));


	@Mapper
	ComponentMapper<HealthComponent> healthMapper;
	

	@Mapper
	ComponentMapper<ConsoleDebugComponent> consoleDebugMapper;
	

	public DeathSystem(float interval)
	{
		super(Aspect.getAspectForAll(HealthComponent.class), interval);
	}


	protected void process(Entity actor)
	{
		log.setActive(consoleDebugMapper.get(actor) != null && consoleDebugMapper.get(actor).behavior);
		
		HealthComponent healthComponent = healthMapper.get(actor);
		
		if (healthComponent.getCurrent() <= 0)
		{
			world.deleteEntity(actor);
		}
	}

}
