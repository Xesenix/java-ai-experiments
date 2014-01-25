
package experiments.artemis.systems;

import java.util.List;

import org.slf4j.LoggerFactory;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.IntervalEntityProcessingSystem;

import experiments.artemis.ActiveLogger;
import experiments.artemis.components.ConsoleDebugComponent;
import experiments.artemis.components.HealthComponent;
import experiments.artemis.damage.Damage;
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

		DamageSystem damageSystem = world.getSystem(DamageSystem.class);

		List<Damage> damageReports = damageSystem.getEntityDamageRaport(entity);
		
		if (damageReports != null)
		{
			DebugActorSystem debugActorSystem = world.getSystem(DebugActorSystem.class);
			ActorDebugMediator mediator = debugActorSystem.getMediatorForEntity(entity);
			ConsoleMessageSystem consoleSystem = world.getSystem(ConsoleMessageSystem.class);
			
			for (Damage dmg : damageSystem.getEntityDamageRaport(entity))
			{
				mediator.showDamageTaken(dmg.getPower());
				consoleSystem.messageToConsole(entity, String.format("%s damage recived: %.2f", dmg.getElementType(), dmg.getPower()));
			}
		}
	}

}
