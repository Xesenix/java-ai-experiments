package experiments.artemis.ai;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "ai")
@XmlAccessorType(XmlAccessType.FIELD)
public class AiDescriptor
{
	@XmlElementWrapper(name = "behaviors")
	@XmlElement(name = "behavior")
	public ArrayList<BehaviorDescriptor> behaviors = new ArrayList<BehaviorDescriptor>();
}
