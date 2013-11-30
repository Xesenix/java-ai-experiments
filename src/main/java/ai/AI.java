
package ai;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.TreeSet;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import ai.actions.rules.IRule;
import ai.behaviour.IBehaviour;
import ai.behaviour.Scene;
import ai.domains.IProblemsDomain;

import com.google.inject.Inject;
import com.google.inject.Injector;

@XmlRootElement(name = "ai")
@XmlAccessorType(XmlAccessType.FIELD)
public class AI
{
	@Inject
	private transient Injector injector;


	@XmlElementWrapper(name = "behaviours")
	@XmlAnyElement(lax = true)
	private ArrayList<IBehaviour> behaviours = new ArrayList<IBehaviour>();
	
	
	@XmlElementWrapper(name = "domains")
	@XmlAnyElement(lax = true)
	private TreeSet<IProblemsDomain> domains = new TreeSet<IProblemsDomain>();
	
	
	@XmlElementWrapper(name = "rules")
	@XmlAnyElement(lax = true)
	private ArrayList<IRule> rules = new ArrayList<IRule>();
	
	
	public void addProblemsDomain(IProblemsDomain domain)
	{
		this.domains.add(domain);
	}
	
	
	public void addAllProblemsDomain(IProblemsDomain... domains)
	{
		for (IProblemsDomain domain : domains)
		{
			addProblemsDomain(domain);
		}
	}
	
	
	public void addAllProblemsDomain(Collection<IProblemsDomain> domains)
	{
		for (IProblemsDomain domain : domains)
		{
			addProblemsDomain(domain);
		}
	}


	public Scene createSceneBehaviour()
	{
		Scene scene = injector.getInstance(Scene.class);
		
		behaviours.add(scene);
		
		scene.setInstanceId(String.format("scene%02d", behaviours.indexOf(scene)));
		
		return scene;
	}


	public String toString()
	{
		return String.format("{behaviours: %s}", behaviours);
	}
}
