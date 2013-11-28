
package ai.behaviour;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import ai.actors.IActor;


@XmlJavaTypeAdapter(xml.AnyTypeAdapter.class)
public interface IBehaviour
{

	void addActor(IActor actor);

}
