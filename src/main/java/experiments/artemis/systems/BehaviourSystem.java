
package experiments.artemis.systems;

import org.slf4j.LoggerFactory;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.IntervalEntityProcessingSystem;

import experiments.artemis.ActiveLogger;
import experiments.artemis.ai.AI;
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


	private AI ai;


	public BehaviourSystem(AI ai, float interval)
	{
		super(Aspect.getAspectForOne(BehaviorComponent.class), interval);
		this.ai = ai;
	}


	protected void process(Entity actor)
	{
		//boolean finished = true;

		log.setActive(consoleDebugMapper.get(actor) != null && consoleDebugMapper.get(actor).behavior);

		log.info("processing entity {}", actor);
		log.info("retriving entity state..");

		BehaviorComponent behaviorComponent = behaviorMapper.get(actor); // get behavior for entity

		log.debug("entity behavior {}", behaviorComponent);

		// clean task list
		TasksComponent tasksComponent = tasksMapper.get(actor);
		
		if (tasksComponent == null)
		{
			tasksComponent = new TasksComponent();
			actor.addComponent(tasksComponent);
			actor.changedInWorld();
		}
		
		IBehavior behavior = ai.getBehaviors().get(behaviorComponent.getName());
		
		if (behavior != null)
		{
			// decide what to do
			behavior.setActor(actor);
			behavior.reset();
			behavior.run();
			
			log.info("entity tasks {}", tasksComponent);
		}
	}


	public void inserted(Entity entity)
	{
		ai.addActor(entity);
	}


	public void removed(Entity entity)
	{
		ai.removeActor(entity);
	}
	
}
