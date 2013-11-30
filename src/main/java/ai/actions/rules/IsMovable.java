package ai.actions.rules;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.inject.Inject;

import ai.domains.navigation.INavigationProblemsDomain;

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
