
package experiments.artemis.systems;

import java.util.HashMap;

import org.slf4j.LoggerFactory;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.IntervalEntityProcessingSystem;

import experiments.artemis.ActiveLogger;
import experiments.artemis.ai.behaviours.IBehavior;
import experiments.artemis.components.BehaviorComponent;
import experiments.artemis.components.ConsoleDebugComponent;
import experiments.artemis.components.TasksComponent;


public class BehaviourSystem extends IntervalEntityProcessingSystem
{
	private static final ActiveLogger log = new ActiveLogger(LoggerFactory.getLogger(BehaviourSystem.class));


	@Mapper
	ComponentMapper<BehaviorComponent> behaviorMapper;


	@Mapper
	ComponentMapper<TasksComponent> tasksMapper;
	

	@Mapper
	ComponentMapper<ConsoleDebugComponent> consoleDebugMapper;
	
	
	HashMap<String, IBehavior> behaviorMap = new HashMap<String, IBehavior>();


	public BehaviourSystem(float interval)
	{
		super(Aspect.getAspectForOne(BehaviorComponent.class), interval);
	}


	protected void process(Entity entity)
	{
		//boolean finished = true;

		log.setActive(consoleDebugMapper.get(entity) != null && consoleDebugMapper.get(entity).behavior);

		log.info("processing entity {}", entity);
		log.info("retriving entity state..");

		BehaviorComponent behaviorComponent = behaviorMapper.get(entity); // get behavior for entity

		log.debug("entity behavior {}", behaviorComponent);

		// clean task list
		TasksComponent tasksComponent = tasksMapper.get(entity);
		
		if (tasksComponent == null)
		{
			tasksComponent = new TasksComponent();
			entity.addComponent(tasksComponent);
			entity.changedInWorld();
		}
		
		IBehavior behavior = behaviorMap.get(behaviorComponent.getName());
		
		if (behavior != null)
		{
			// decide what to do
			behavior.setContext(world, entity);
			behavior.reset();
			behavior.run();
			
			log.info("entity tasks {}", tasksComponent);
		}
		
	}


	public void addBehavior(String key, IBehavior behavior)
	{
		behaviorMap.put(key, behavior);
	}
}
