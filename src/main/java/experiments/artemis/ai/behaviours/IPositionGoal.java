
package experiments.artemis.ai.behaviours;

import ai.world.IPosition;

import com.artemis.Entity;
import com.artemis.World;


public interface IPositionGoal extends IGoal
{
	IPosition getTarget(World world, Entity e);
}
