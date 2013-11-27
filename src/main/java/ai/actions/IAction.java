
package ai.actions;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


@XmlJavaTypeAdapter(xml.AnyTypeAdapter.class)
public interface IAction
{
	void execute();
}
