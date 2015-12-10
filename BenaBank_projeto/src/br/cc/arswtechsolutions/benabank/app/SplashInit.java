package br.cc.arswtechsolutions.benabank.app;

import java.io.IOException;

import br.cc.arswtechsolutions.benabank.ui.controllers.SplashScreenController;
import javafx.application.Preloader;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class SplashInit extends Preloader {
	Stage MainStage;
	SplashScreenController controller;

	private Scene createPreloaderScene() throws IOException {
		FXMLLoader fxmlloader = new FXMLLoader(
				getClass().getClassLoader().getResource("./br/cc/arswtechsolutions/benabank/ui/views/SplashScreen.fxml"));
		Parent parent = fxmlloader.load();
		Scene splashScene = new Scene(parent, 522, 320);
		controller = fxmlloader.<SplashScreenController> getController();
		return splashScene;
	}

	public void start(final Stage stage) throws Exception {
		this.MainStage = stage;
		this.MainStage.setScene(createPreloaderScene());
		this.MainStage.initStyle(StageStyle.UNDECORATED);
		this.MainStage.setTitle("Loading...");
		this.MainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				// TODO Auto-generated method stub
				if (event.getEventType() == WindowEvent.WINDOW_CLOSE_REQUEST || event.getEventType() == WindowEvent.WINDOW_HIDDEN) {
					event.consume();
				}
			}
		});
		this.MainStage.show();
	}

	public Label getProgressLabel() {
		return this.controller.getLbCarregando();
	}

	@Override
	public void handleProgressNotification(ProgressNotification pn) {
		this.controller.getPbCarregando().setProgress(pn.getProgress());

	}

	@Override
	public void handleStateChangeNotification(StateChangeNotification evt) {
		if (evt.getType().ordinal() == StateChangeNotification.Type.BEFORE_START.ordinal()) {
			this.MainStage.close();
			Stage s = (Stage) controller.getLbCarregando().getScene().getWindow();
			s.hide();
		}
	}
}
