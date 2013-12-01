
package ai.actors;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import ai.world.navigation.IPosition;


@XmlJavaTypeAdapter(xml.AnyTypeAdapter.class)
public interface IActor
{
	void setPosition(IPosition position);


	IPosition getPosition();
}
