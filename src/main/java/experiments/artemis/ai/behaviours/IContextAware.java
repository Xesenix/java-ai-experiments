package experiments.artemis.ai.behaviours;

import com.artemis.Entity;
import com.artemis.EntityObserver;
import com.artemis.World;

public interface IContextAware
{
	void setContext(World world, Entity entity);
}
