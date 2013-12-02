
package experiments;

public interface IExperimentManager
{
	void initialize();


	void loadAiFromXmlString(String source);


	String getAiAsXmlString();


	void loadAiFromJsonString(String source);


	String getAiAsJsonString();


	void loadWorldFromJsonString(String text);


	String getWorldAsJsonString();


	void loadWorldFromXmlString(String text);


	String getWorldAsXmlString();


	void startAi();


	void doAiLogicStep(long stepMiliseconds);


	void stopAi();
}
