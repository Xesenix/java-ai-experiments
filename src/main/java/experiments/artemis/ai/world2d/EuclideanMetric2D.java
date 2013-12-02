package experiments.artemis.ai.world2d;

import ai.world.IMetric;
import ai.world.IPosition;

public class EuclideanMetric2D implements IMetric {
	public double distance(IPosition source, IPosition target)
	{
		double dx = ((Position) target).getX() - ((Position) source).getX();
		double dy = ((Position) target).getY() - ((Position) source).getY();
		
		return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
	}
}