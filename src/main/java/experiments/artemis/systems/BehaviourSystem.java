
package experiments.artemis.systems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.vividsolutions.jts.geomgraph.Position;

import experiments.artemis.ai.behaviours.ITask;
import experiments.artemis.components.PositionComponent;
import experiments.artemis.components.behaviours.BehaviorComponent;
import experiments.artemis.components.tasks.TaskComponent;


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
		log.debug("processing entity {} {}", e, e.getComponent(PositionComponent.class));

		BehaviorComponent behavior = bm.get(e); // get behavior for entity

		log.debug("entity behavior {}", behavior);

		ITask task = tm.get(e);

		log.debug("entity current task {}", task);

		// decide what to do

		task = decide(e, behavior);

		log.debug("entity new task {}", task);

		e.addComponent(new TaskComponent(task));
		e.changedInWorld();
	}


	private ITask decide(Entity e, BehaviorComponent behavior)
	{
		return behavior.chooseTask(world, e);
	}

}
