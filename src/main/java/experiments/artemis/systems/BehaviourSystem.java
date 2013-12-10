
package experiments.artemis.systems;

import org.slf4j.LoggerFactory;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.IntervalEntityProcessingSystem;

import experiments.artemis.ActiveLogger;
import experiments.artemis.components.BehaviorComponent;
import experiments.artemis.components.ConsoleDebugComponent;
import experiments.artemis.components.TasksComponent;


public class BehaviourSystem extends IntervalEntityProcessingSystem
{
	private static final ActiveLogger log = new ActiveLogger(LoggerFactory.getLogger(BehaviourSystem.class));


	@Mapper
	ComponentMapper<BehaviorComponent> bm;


	@Mapper
	ComponentMapper<TasksComponent> tm;
	

	@Mapper
	ComponentMapper<ConsoleDebugComponent> cdm;


	public BehaviourSystem(float interval)
	{
		super(Aspect.getAspectForOne(BehaviorComponent.class), interval);
	}


	protected void process(Entity entity)
	{
		//boolean finished = true;

		log.setActive(cdm.get(entity) != null && cdm.get(entity).behavior);

		log.info("processing entity {}", entity);
		log.info("retriving entity state..");

		BehaviorComponent behavior = bm.get(entity); // get behavior for entity

		log.debug("entity behavior {}", behavior);

		// clean task list
		TasksComponent tasksComponent = tm.get(entity);
		
		if (tasksComponent == null)
		{
			tasksComponent = new TasksComponent();
			entity.addComponent(tasksComponent);
			entity.changedInWorld();
		}
		
		// decide what to do
		behavior.setContext(world, entity);
		behavior.reset();
		behavior.run();
		
		log.info("entity tasks {}", tasksComponent);
	}
}
