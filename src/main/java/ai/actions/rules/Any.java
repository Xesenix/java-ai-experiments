
package ai.actions.rules;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Any implements IRule
{
	@XmlElementWrapper(name = "rules")
	@XmlAnyElement(lax = true)
	private List<IRule> rules;


	public boolean check()
	{
		return false;
	}


	public List<IRule> getRules()
	{
		if (rules == null)
		{
			rules = new ArrayList<IRule>();
		}

		return rules;
	}


	public void setRules(List<IRule> rules)
	{
		this.rules = rules;
	}
}
