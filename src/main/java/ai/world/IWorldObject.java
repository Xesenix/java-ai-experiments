
package ai.world;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlJavaTypeAdapter(xml.AnyTypeAdapter.class)
public interface IWorldObject
{
	Position getPosition();


	void setPosition(Position position);
}
