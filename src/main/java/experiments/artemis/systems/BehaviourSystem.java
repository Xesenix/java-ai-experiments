
package experiments.artemis.systems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

import experiments.artemis.ai.behaviours.ITask;
import experiments.artemis.components.tasks.TaskComponent;
import experiments.artemis.componentsbehaviours.BehaviorComponent;


public class BehaviourSystem extends EntityProcessingSystem
{
	private static final Logger log = LoggerFactory.getLogger(BehaviourSystem.class);


	@Mapper
	ComponentMapper<TaskComponent> tm;


	@Mapper
	ComponentMapper<BehaviorComponent> bm;


	public BehaviourSystem()
	{
		super(Aspect.getAspectForOne(BehaviorComponent.class).exclude(TaskComponent.class));
	}


	protected void process(Entity e)
	{
		log.debug("processing entity {}", e);

		BehaviorComponent behavior = bm.get(e); // get behavior for entity

		log.debug("entity behavior {}", behavior);

		ITask task = tm.get(e);

		log.debug("entity current task {}", task);

		// decide what to do

		task = decide(behavior, e);

		log.debug("entity new task {}", task);

		e.addComponent(new TaskComponent(task));
		e.changedInWorld();
	}


	private ITask decide(BehaviorComponent behavior, Entity e)
	{
		return behavior.chooseTask(world, e);
	}

}
