
package ai.world;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import ai.world.navigation.IPosition;

@XmlJavaTypeAdapter(xml.AnyTypeAdapter.class)
public interface IWorldObject
{
	IPosition getPosition();


	void setPosition(IPosition position);
}
