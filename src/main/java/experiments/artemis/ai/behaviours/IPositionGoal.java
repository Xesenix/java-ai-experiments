
package experiments.artemis.ai.behaviours;

import com.artemis.Entity;
import com.artemis.World;

import ai.world.IPosition;


public interface IPositionGoal extends IGoal
{
	IPosition getTarget(World world, Entity e);
}
