
package experiments.artemis.ai.goals;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import experiments.artemis.ai.behaviours.IContextAware;


public interface IGoal extends IContextAware
{
	boolean achived();
}
