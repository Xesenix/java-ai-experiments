
package experiment;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import pl.xesenix.fxml.viewport.Viewport;

import com.google.inject.Inject;
import com.google.inject.Singleton;


@Singleton
public class AppController implements IExperimentView
{
	@Inject
	IExperimentManager experiment;


	@FXML
	private ResourceBundle resources;


	@FXML
	private URL location;


	@FXML
	private TextArea source;


	@FXML
	private AnchorPane view;


	@FXML
	private Viewport viewport;


	@FXML
	void clickLoadButton(ActionEvent event)
	{
		experiment.loadFromXmlString(source.getText());
	}


	@FXML
	void clickSaveButton(ActionEvent event)
	{
		source.setText(experiment.getAsXmlString());
	}


	@FXML
	void clickStartButton(ActionEvent event)
	{
		experiment.start();
	}


	@FXML
	void initialize()
	{
		assert source != null : "fx:id=\"source\" was not injected: check your FXML file 'app.fxml'.";
		assert view != null : "fx:id=\"view\" was not injected: check your FXML file 'app.fxml'.";
		assert viewport != null : "fx:id=\"viewport\" was not injected: check your FXML file 'app.fxml'.";
		
		experiment.initialize();
	}

}
