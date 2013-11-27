package ai.actors;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import ai.world.Position;

@XmlJavaTypeAdapter(xml.AnyTypeAdapter.class)
public interface IActor
{
	void setPosition(Position position);
	
	
	Position getPosition();
}
