package experiments.artemis.systems;

import org.slf4j.LoggerFactory;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

import experiments.artemis.ActiveLogger;
import experiments.artemis.ai.world2d.Position;
import experiments.artemis.components.ConsoleDebugComponent;
import experiments.artemis.components.MovementDirectionComponent;
import experiments.artemis.components.MovementSpeedComponent;
import experiments.artemis.components.PositionComponent;

public class MovementSystem extends EntityProcessingSystem
{
	private static final ActiveLogger log = new ActiveLogger(LoggerFactory.getLogger(MovementSystem.class));


	@Mapper
	ComponentMapper<PositionComponent> positionMapper;


	@Mapper
	ComponentMapper<MovementDirectionComponent> movementDirectionMapper;


	@Mapper
	ComponentMapper<MovementSpeedComponent> movementSpeedMapper;


	@Mapper
	ComponentMapper<ConsoleDebugComponent> consoleDebugMapper;
	

	public MovementSystem()
	{
		super(Aspect.getAspectForAll(PositionComponent.class, MovementDirectionComponent.class, MovementSpeedComponent.class));
	}

	
	protected void process(Entity e)
	{
		log.setActive(consoleDebugMapper.get(e) != null && consoleDebugMapper.get(e).movement);

		log.info("process entity {}", e);
		log.info("retriving entity state..");
		
		PositionComponent worldPosition = positionMapper.get(e);
		
		log.info("position: {}", worldPosition);
		
		if (worldPosition.getPosition() instanceof Position)
		{
			Position position = (Position) worldPosition.getPosition();
			
			MovementSpeedComponent speed = movementSpeedMapper.get(e);
			MovementDirectionComponent direction = movementDirectionMapper.get(e);

			log.info("speed: {}", speed);
			log.info("direction: {}", direction);
			
			double dx = Math.cos(direction.getDirection()) * speed.getSpeed() * world.getDelta();
			double dy = Math.sin(direction.getDirection()) * speed.getSpeed() * world.getDelta();
			
			position.set(position.getX() + dx, position.getY() + dy);
		}
	}

}
