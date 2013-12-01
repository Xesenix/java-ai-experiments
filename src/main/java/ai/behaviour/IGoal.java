
package ai.behaviour;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


@XmlJavaTypeAdapter(xml.AnyTypeAdapter.class)
public interface IGoal
{
	boolean achived();
}
