
package ai.domains.navigation;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import ai.actors.IActor;
import ai.world.IPosition;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DiscretPositionSet implements INavigationProblemsDomain
{
	@XmlElementWrapper(name = "aviablePositions")
	@XmlAnyElement(lax = true)
	private Set<IPosition> availablePositions = new HashSet<IPosition>();


	public Set<IPosition> getAvailablePositions()
	{
		return availablePositions;
	}


	public void setAvailablePositions(Set<IPosition> availablePositions)
	{
		if (!(availablePositions instanceof HashSet))
		{
			availablePositions = new HashSet<IPosition>(availablePositions);
		}

		this.availablePositions = availablePositions;
	}


	public boolean canMove(IActor actor)
	{
		return true;
	}


	public boolean canMoveTo(IActor actor, IPosition position)
	{
		return availablePositions.contains(position);
	}

}
