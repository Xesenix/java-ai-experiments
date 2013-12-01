
package ai.actions.rules;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


@XmlJavaTypeAdapter(xml.AnyTypeAdapter.class)
public interface IRule
{
	boolean check();
}
