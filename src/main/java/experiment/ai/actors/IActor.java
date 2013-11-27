package experiment.ai.actors;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import experiment.world.Position;

@XmlJavaTypeAdapter(experiment.xml.AnyTypeAdapter.class)
public interface IActor
{
	void setPosition(Position position);
	
	
	Position getPosition();
}
