
package experiments.artemis.ai.goals;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import ai.world.IPosition;


public interface IPositionGoal extends IGoal
{
	IPosition getTarget();
}
