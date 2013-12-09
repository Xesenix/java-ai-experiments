
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


	protected void process(Entity e)
	{
		//boolean finished = true;

		log.setActive(cdm.get(e) != null && cdm.get(e).behavior);

		log.info("processing entity {}", e);
		log.info("retriving entity state..");

		BehaviorComponent behavior = bm.get(e); // get behavior for entity

		log.debug("entity behavior {}", behavior);

		// clean task list
		TasksComponent tasksComponent = tm.get(e);
		
		if (tasksComponent == null)
		{
			tasksComponent = new TasksComponent();
			e.addComponent(tasksComponent);
			e.changedInWorld();
		}
		
		// decide what to do
		behavior.reset(world, e);
		behavior.run(world, e);
		
		log.info("entity tasks {}", tasksComponent);
	}
}
