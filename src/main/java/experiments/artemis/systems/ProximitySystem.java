
package experiments.artemis.systems;

import org.slf4j.LoggerFactory;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

import experiments.artemis.ActiveLogger;
import experiments.artemis.components.ConsoleDebugComponent;
import experiments.artemis.components.PositionComponent;


/**
 * Should be use to measure distances and trigger actions.
 * 
 * @author Xesenix
 *
 */
public class ProximitySystem extends EntityProcessingSystem
{
	private static final ActiveLogger log = new ActiveLogger(LoggerFactory.getLogger(ProximitySystem.class));


	@Mapper
	ComponentMapper<PositionComponent> positionMapper;


	@Mapper
	ComponentMapper<ConsoleDebugComponent> consoleDebugMapper;


	public ProximitySystem()
	{
		super(Aspect.getAspectForAll(PositionComponent.class));
	}


	@Override
	protected void process(Entity e)
	{
		log.setActive(consoleDebugMapper.get(e) != null && consoleDebugMapper.get(e).movement);
	}

}
