
package ai.world;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


@XmlJavaTypeAdapter(xml.AnyTypeAdapter.class)
public interface IWorldObject
{
	IPosition getPosition();


	void setPosition(IPosition position);
}
