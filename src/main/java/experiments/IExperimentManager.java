package experiments;

public interface IExperimentManager
{
	void initialize();

	void loadFromXmlString(String source);

	void start();

	String getAsXmlString();

}
