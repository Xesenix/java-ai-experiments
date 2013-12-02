package experiments.artemis.ai.behaviours;

import ai.world.IPosition;

public interface IPositionGoal extends IGoal
{
	IPosition getTarget();
}
