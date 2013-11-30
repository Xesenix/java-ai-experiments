
package experiments;

import java.util.Locale;
import java.util.ResourceBundle;

import com.google.inject.AbstractModule;


public class UIModule extends AbstractModule
{
	private Class<? extends IExperimentManager> type;


	public UIModule(Class<? extends IExperimentManager> type)
	{
		this.type = type;
	}


	public void configure()
	{
		bindResources();
		bindExperiment();
	}


	private void bindExperiment()
	{
		bind(IExperimentManager.class).to(type);
		bind(IExperimentView.class).to(UIController.class);
	}


	private void bindResources()
	{
		Locale locale = new Locale("pl", "PL");
		bind(ResourceBundle.class).toInstance(ResourceBundle.getBundle("bundles.app", locale));
	}
}
