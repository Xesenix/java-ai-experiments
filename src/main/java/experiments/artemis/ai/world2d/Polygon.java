
package experiments.artemis.ai.world2d;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import ai.world.IPosition;


@XmlRootElement(name ="polygon")
@XmlAccessorType(XmlAccessType.FIELD)
public class Polygon implements IPosition
{
	@XmlAttribute
	private double[] vertices;
	
	
	public Polygon()
	{
	}


	public Polygon(double... coordinates) throws Exception
	{
		setVertices(coordinates);
	}
	
	
	public double[] getVertices()
	{
		return vertices;
	}


	public void setVertices(double... coordinates) throws Exception
	{
		if (coordinates.length % 2 == 1)
		{
			throw new Exception("Invalid polygon coordinates");
		}

		this.vertices = coordinates;
	}


	public static boolean insidePolygon(double x, double y, double... v)
	{
		int wn = 0;
		int n = v.length / 2;

		for (int i = 0; i < n; i++)
		{
			if (v[2 * i + 1] <= y)
			{
				if (v[(2 * (i + 1) + 1) % v.length] > y)
				{
					if (isLeft(v[2 * i], v[2 * i + 1], v[(2 * (i + 1))  % v.length], v[(2 * (i + 1) + 1) % v.length], x, y) > 0)
					{
						++wn;
					}
				}
			}
			else
			{
				if (v[(2 * (i + 1) + 1) % v.length] <= y)
				{
					if (isLeft(v[2 * i], v[2 * i + 1], v[(2 * (i + 1))  % v.length], v[(2 * (i + 1) + 1) % v.length], x, y) < 0)
					{
						--wn;
					}
				}
			}
		}

		return wn % 2 == 1;
	}


	public static double isLeft(double x0, double y0, double x1, double y1, double x2, double y2)
	{
		return ((x1 - x0) * (y2 - y0) - (x2 - x0) * (y1 - y0));
	}
}
