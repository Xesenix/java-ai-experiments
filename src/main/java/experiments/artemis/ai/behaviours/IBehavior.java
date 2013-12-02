
package experiments.artemis.ai.behaviours;

import com.artemis.Entity;
import com.artemis.World;


public interface IBehavior
{
	ITask chooseTask(World world, Entity e);

	void setCompleted(World world, Entity e, boolean completed);

	boolean isRunning();
}
