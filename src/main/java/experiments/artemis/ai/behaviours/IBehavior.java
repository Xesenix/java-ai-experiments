
package experiments.artemis.ai.behaviours;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;



@XmlJavaTypeAdapter(xml.AnyTypeAdapter.class)
public interface IBehavior extends IActorAware
{
	/**
	 * 
	 * @param world
	 * @param e
	 * @return
	 */
	void run();


	/**
	 * Reset state of behavior.
	 * 
	 * @param world
	 * @param e
	 */
	void reset();


	/**
	 * True if not yet started.
	 * 
	 * @param world
	 * @param e
	 * @return
	 */
	boolean isReady();


	/**
	 * True if behavior still running.
	 * 
	 * @param world
	 * @param e
	 * @return
	 */
	boolean isRunning();


	/**
	 * True if goals achieved.
	 * 
	 * @param world
	 * @param e
	 * @return true if achieved all goals
	 */
	boolean isSuccess();


	/**
	 * Strategy finished execution either success or failure.
	 * 
	 * @param world
	 * @param e
	 * @return
	 */
	boolean isCompleted();
}
