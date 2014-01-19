
package experiments.artemis.components;

import javax.xml.bind.annotation.XmlRootElement;

import com.artemis.Component;


@XmlRootElement(name = "action")
public class ActionComponent extends Component
{
	private String label;
	
	
	private double progress;


	public ActionComponent()
	{
	}


	public String getLabel()
	{
		return label;
	}


	public void setLabel(String name)
	{
		this.label = label;
	}


	public double getProgress()
	{
		return progress;
	}


	public void setProgress(double progress)
	{
		this.progress = progress;
	}


	public String toString()
	{
		return String.format("[%s@%x {action: %s; progress: %.2f}]", getClass().getSimpleName(), hashCode(), label);
	}

}
