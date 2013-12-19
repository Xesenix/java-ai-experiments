
package experiments.artemis.ai.behaviours;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import experiments.artemis.ai.tasks.BehaviorState;


@XmlJavaTypeAdapter(xml.AnyTypeAdapter.class)
public interface IBehavior extends IActorAware
{
	BehaviorState getState();


	void setState(BehaviorState state);
	
	
	/**
	 * 
	 */
	void run();


	/**
	 * Reset state of behavior.
	 * 
	 */
	void reset();


	/**
	 * True if not yet started.
	 * 
	 * @return
	 */
	boolean isReady();


	/**
	 * True if behavior still running.
	 * 
	 * @return
	 */
	boolean isRunning();


	/**
	 * True if goals achieved.
	 * 
	 * @return true if achieved all goals
	 */
	boolean isSuccess();


	/**
	 * Strategy finished execution either success or failure.
	 * 
	 * @return
	 */
	boolean isCompleted();
}
