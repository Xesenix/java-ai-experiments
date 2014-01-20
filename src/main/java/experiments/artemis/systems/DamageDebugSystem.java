
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
import experiments.ui.ActorDebugMediator;


public class DamageDebugSystem extends IntervalEntityProcessingSystem
{
	private static final ActiveLogger log = new ActiveLogger(LoggerFactory.getLogger(DamageDebugSystem.class));


	@Mapper
	ComponentMapper<HealthComponent> healthMapper;


	@Mapper
	ComponentMapper<ConsoleDebugComponent> consoleDebugMapper;


	public DamageDebugSystem(float interval)
	{
		super(Aspect.getAspectForAll(HealthComponent.class), interval);
	}


	protected void process(Entity entity)
	{
		log.setActive(consoleDebugMapper.get(entity) != null && consoleDebugMapper.get(entity).debug);

		DebugActorSystem debugActorSystem = world.getSystem(DebugActorSystem.class);

		HealthComponent health = healthMapper.get(entity);
		ActorDebugMediator mediator = debugActorSystem.getMediatorForEntity(entity);

		for (Double dmg : health.getDamageRequests())
		{
			mediator.showDamageTaken(dmg);
		}
		
		health.getDamageRequests().clear();
	}

}
