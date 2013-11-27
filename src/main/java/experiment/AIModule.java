
package experiment;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import com.google.inject.AbstractModule;

import experiment.ai.AIEnviroment;
import experiment.ai.actions.Move;
import experiment.ai.actors.NPC;
import experiment.world.Position;


public class AIModule extends AbstractModule
{
	private Class<? extends IExperimentManager> type;


	public AIModule(Class<? extends IExperimentManager> type)
	{
		this.type = type;
	}


	public void configure()
	{
		bindResources();
		bindExperiment();
		bindActions();
		bindActors();
		bindXmlEnviroment();
	}


	private void bindXmlEnviroment()
	{
		bind(AIEnviroment.class);
		try
		{
			bind(JAXBContext.class).toInstance(JAXBContext.newInstance(Move.class, AIEnviroment.class, Position.class, NPC.class));
		}
		catch (JAXBException e)
		{
			e.printStackTrace();
		}
	}


	private void bindActions()
	{
		bind(Move.class);
	}


	private void bindActors()
	{
		bind(NPC.class);
	}


	private void bindExperiment()
	{
		bind(IExperimentManager.class).to(this.type);
		bind(IExperimentView.class).to(AppController.class);
	}


	private void bindResources()
	{
		Locale locale = new Locale("pl", "PL");
		bind(ResourceBundle.class).toInstance(ResourceBundle.getBundle("bundles.app", locale));
	}
}
