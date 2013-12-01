
package experiments.artemis.systems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

import experiments.artemis.components.BehaviourComponent;
import experiments.artemis.components.TaskComponent;


public class BehaviourSystem extends EntityProcessingSystem
{
	private static final Logger log = LoggerFactory.getLogger(BehaviourSystem.class);
	
	
	@Mapper
	ComponentMapper<TaskComponent> tm;


	@Mapper
	ComponentMapper<BehaviourComponent> bm;


	public BehaviourSystem()
	{
		super(Aspect.getAspectForOne(BehaviourComponent.class).exclude(TaskComponent.class));
	}


	protected void process(Entity e)
	{
		log.debug("processing entity {}", e);
		
		BehaviourComponent behaviour = bm.get(e); // get behavior for entity
		
		log.debug("entity behavior {}", behaviour);
		
		TaskComponent task = tm.get(e);
		
		log.debug("entity task {}", task);
		
		task = decide(behaviour, e); // decide what to do
		
		log.debug("entity task {}", task);

		e.addComponent(new TaskComponent(task));
		e.changedInWorld();
	}


	private TaskComponent decide(BehaviourComponent behaviour, Entity e)
	{
		return behaviour.chooseTask(world, e);
	}

}
