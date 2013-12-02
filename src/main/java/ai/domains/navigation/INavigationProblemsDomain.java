
package ai.domains.navigation;

import ai.actors.IActor;
import ai.domains.IProblemsDomain;
import ai.world.IPosition;


public interface INavigationProblemsDomain extends IProblemsDomain
{
	boolean canMove(IActor actor);


	boolean canMoveTo(IActor actor, IPosition position);
}
