
package experiment.ai.actions;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


@XmlJavaTypeAdapter(experiment.xml.AnyTypeAdapter.class)
public interface IAction
{
	void execute();
}
