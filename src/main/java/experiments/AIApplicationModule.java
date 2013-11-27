
package experiments;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import ai.AIEnviromentFactory;
import ai.actions.Move;
import ai.actors.NPC;
import ai.world.Position;

import com.google.inject.AbstractModule;


public class AIApplicationModule extends AbstractModule
{
	private Class<? extends IExperimentManager> type;


	public AIApplicationModule(Class<? extends IExperimentManager> type)
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
		bind(IExperimentManager.class).to(this.type);
		bind(IExperimentView.class).to(AIApplicationController.class);
	}


	private void bindResources()
	{
		Locale locale = new Locale("pl", "PL");
		bind(ResourceBundle.class).toInstance(ResourceBundle.getBundle("bundles.app", locale));
	}
}
