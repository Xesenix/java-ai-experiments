package experiments.artemis.systems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.world.IPosition;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.Bag;

import experiments.IExperimentView;
import experiments.artemis.ai.world2d.Position;
import experiments.artemis.components.PositionComponent;
import experiments.ui.PositionDebugSprite;

public class DebugEntityPositionSystem extends EntityProcessingSystem
{
	private static final Logger log = LoggerFactory.getLogger(DebugEntityPositionSystem.class);


	@Mapper
	ComponentMapper<PositionComponent> pm;
	
	
	private IExperimentView view;
	
	
	private Bag<PositionDebugSprite> spritesByEntity = new Bag<PositionDebugSprite>();

	
	public DebugEntityPositionSystem(IExperimentView view)
	{
		super(Aspect.getAspectForAll(PositionComponent.class));
		
		this.view = view;
	}

	protected void process(Entity e)
	{
		// log.debug("debugging: {}", e);
		
		PositionComponent position = pm.get(e);
		
		if (position != null)
		{
			IPosition coordinates = position.getPosition();
			
			// log.debug("coordinates: {}", coordinates);
			
			if (coordinates instanceof Position)
			{
				PositionDebugSprite sprite = spritesByEntity.get(e.getId());
				
				if (sprite == null)
				{
					sprite = view.createPositionDebugSprite();
					spritesByEntity.set(e.getId(), sprite);
				}
				
				sprite.setTranslateX(((Position) coordinates).getX());
				sprite.setTranslateY(((Position) coordinates).getY());
			}
		}
	}

}
