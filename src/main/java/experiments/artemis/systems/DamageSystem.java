
package experiments.artemis.systems;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.IntervalEntityProcessingSystem;
import com.artemis.utils.Bag;

import experiments.artemis.ActiveLogger;
import experiments.artemis.components.ConsoleDebugComponent;
import experiments.artemis.components.HealthComponent;
import experiments.artemis.damage.Damage;
import experiments.ui.ActorDebugMediator;


public class DamageSystem extends IntervalEntityProcessingSystem
{
	private static final ActiveLogger log = new ActiveLogger(LoggerFactory.getLogger(DamageSystem.class));


	@Mapper
	ComponentMapper<HealthComponent> healthMapper;


	@Mapper
	ComponentMapper<ConsoleDebugComponent> consoleDebugMapper;


	private Bag<List<Damage>> damageRaportByEntity = new Bag<List<Damage>>();


	public DamageSystem(float interval)
	{
		super(Aspect.getAspectForAll(HealthComponent.class), interval);
	}


	protected void process(Entity entity)
	{
		log.setActive(consoleDebugMapper.get(entity) != null && consoleDebugMapper.get(entity).debug);

		HealthComponent health = healthMapper.get(entity);

		DebugActorSystem debugActorSystem = world.getSystem(DebugActorSystem.class);
		ActorDebugMediator mediator = debugActorSystem.getMediatorForEntity(entity);
		ConsoleMessageSystem consoleSystem = world.getSystem(ConsoleMessageSystem.class);

		for (Damage dmg : health.getDamageRequests())
		{
			health.setCurrent(health.getCurrent() - dmg.getPower());
			damageRaportByEntity.get(entity.getId()).add(dmg);
		}
		
		health.getDamageRequests().clear();
	}

	
	protected void inserted(Entity entity)
	{
		damageRaportByEntity.set(entity.getId(), new ArrayList<Damage>(20));
	}
	
	
	public List<Damage> getEntityDamageRaport(Entity entity)
	{
		return damageRaportByEntity.get(entity.getId());
	}
}
