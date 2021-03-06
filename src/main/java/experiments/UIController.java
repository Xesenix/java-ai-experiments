
package experiments;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import pl.xesenix.fxml.viewport.Viewport;
import pl.xesenix.javafx.animation.FixedStepAnimationTimer;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

import experiments.ui.ActorDebugMediator;
import experiments.ui.BehaviorTreeDebugMediator;
import experiments.ui.IActorDebugSprite;
import experiments.ui.IBehaviorTreeDebugSprite;


@Singleton
public class UIController implements IExperimentViewMediator
{
	@Inject
	Injector inject;


	@Inject
	IExperimentManager experiment;


	@FXML
	private ResourceBundle resources;


	@FXML
	private URL location;


	@FXML
	private TextArea aiConsole;


	@FXML
	private TextArea aiJsonSource;


	@FXML
	private ToggleButton aiRunningButton;


	@FXML
	private TextArea aiXmlSource;


	@FXML
	private AnchorPane view;


	@FXML
	private Viewport viewport;


	@FXML
	private TextArea worldJsonSource;


	@FXML
	private TextArea worldXmlSource;


	@FXML
	private Group debugPointLayer;


	@FXML
	private FlowPane btDebugList;


	@FXML
	void clickAiLoadJsonButton(ActionEvent event)
	{
		experiment.loadAiFromJsonString(aiJsonSource.getText());
	}


	@FXML
	void clickAiSaveJsonButton(ActionEvent event)
	{
		aiJsonSource.setText(experiment.getAiAsJsonString());
	}


	@FXML
	void clickAiXmlLoadButton(ActionEvent event)
	{
		experiment.loadAiFromXmlString(aiXmlSource.getText());
	}


	@FXML
	void clickAiXmlSaveButton(ActionEvent event)
	{
		aiXmlSource.setText(experiment.getAiAsXmlString());
	}


	@FXML
	void clickWorldLoadJsonButton(ActionEvent event)
	{
		experiment.loadWorldFromJsonString(worldJsonSource.getText());
	}


	@FXML
	void clickWorldSaveJsonButton(ActionEvent event)
	{
		worldJsonSource.setText(experiment.getWorldAsJsonString());
	}


	@FXML
	void clickWorldXmlLoadButton(ActionEvent event)
	{
		experiment.loadWorldFromXmlString(worldXmlSource.getText());
	}


	@FXML
	void clickWorldXmlSaveButton(ActionEvent event)
	{
		worldXmlSource.setText(experiment.getWorldAsXmlString());
	}


	@FXML
	void toggleAiRunning(ActionEvent event)
	{
		if (aiRunningButton.isSelected())
		{
			experiment.startAi();
		}
		else
		{
			experiment.stopAi();
		}
	}


	@FXML
	void initialize()
	{
		assert aiConsole != null : "fx:id=\"aiConsole\" was not injected: check your FXML file 'app.fxml'.";
		assert aiJsonSource != null : "fx:id=\"aiJsonSource\" was not injected: check your FXML file 'app.fxml'.";
		assert aiRunningButton != null : "fx:id=\"aiRunningButton\" was not injected: check your FXML file 'app.fxml'.";
		assert aiXmlSource != null : "fx:id=\"aiXmlSource\" was not injected: check your FXML file 'app.fxml'.";
		assert view != null : "fx:id=\"view\" was not injected: check your FXML file 'app.fxml'.";
		assert viewport != null : "fx:id=\"viewport\" was not injected: check your FXML file 'app.fxml'.";
		assert worldJsonSource != null : "fx:id=\"worldJsonSource\" was not injected: check your FXML file 'app.fxml'.";
		assert worldXmlSource != null : "fx:id=\"worldXmlSource\" was not injected: check your FXML file 'app.fxml'.";
		assert btDebugList != null : "fx:id=\"btDebugList\" was not injected: check your FXML file 'app.fxml'.";

		debugPointLayer = new Group();

		viewport.addNodeToCanvas(debugPointLayer);

		experiment.initialize();

		AnimationTimer animator = new FixedStepAnimationTimer(10) {

			public void render(long stepMiliseconds)
			{

			}


			public void dologic(long stepMiliseconds)
			{
				experiment.doAiLogicStep(stepMiliseconds);
			}
		};

		animator.start();
	}


	public ActorDebugMediator createPositionDebugSprite()
	{
		IActorDebugSprite sprite = inject.getInstance(IActorDebugSprite.class);

		debugPointLayer.getChildren().add((Node) sprite);

		ActorDebugMediator mediator = inject.getInstance(ActorDebugMediator.class);
		mediator.setView(sprite);

		return mediator;
	}


	public void removePositionDebugSprite(ActorDebugMediator mediator)
	{
		debugPointLayer.getChildren().remove(mediator.getSprite());
	}


	public BehaviorTreeDebugMediator createBehaviorSprite()
	{
		IBehaviorTreeDebugSprite sprite = inject.getInstance(IBehaviorTreeDebugSprite.class);

		btDebugList.getChildren().add((Node) sprite);

		BehaviorTreeDebugMediator mediator = inject.getInstance(BehaviorTreeDebugMediator.class);
		mediator.setView(sprite);

		return mediator;
	}


	public void createMessage(String message)
	{
		aiConsole.appendText(message + "\n");
	}
}
