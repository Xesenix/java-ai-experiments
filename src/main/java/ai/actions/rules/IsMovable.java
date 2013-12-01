
package ai.actions.rules;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import ai.domains.navigation.INavigationProblemsDomain;

import com.google.inject.Inject;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class IsMovable implements IRule
{
	@Inject
	private INavigationProblemsDomain navigationDomain;


	public boolean check()
	{
		return navigationDomain.canMove(null);
	}

}
