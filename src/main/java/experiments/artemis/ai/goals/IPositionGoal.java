
package experiments.artemis.ai.goals;

import ai.world.IPosition;

import com.artemis.Entity;
import com.artemis.World;


public interface IPositionGoal extends IGoal
{
	IPosition getTarget();
}
