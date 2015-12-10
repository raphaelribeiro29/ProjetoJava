package br.cc.arswtechsolutions.benabank.app;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import br.cc.arswtechsolutions.benabank.screenframework.ScreensController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.application.Preloader.ProgressNotification;
import javafx.application.Preloader.StateChangeNotification;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class Main extends Application {

	private BooleanProperty ready = new SimpleBooleanProperty(false);
	private SplashInit splashInit;
	private HashMap<String, String> hashMap = new HashMap<String, String>();
	private  ScreensController mainContainer;

	void addAllScreen(){
		final String PATH = "./br/cc/arswtechsolutions/benabank/ui/views/";
		hashMap.put("user_login_screen", PATH+"UserLoginScreen.fxml");
		hashMap.put("user_senha", PATH+"UserSenhaScreen.fxml");
		//*hashMap.put("user_transf", PATH+"UserScreenTransferencia.fxml");
		//*hashMap.put("user_saque", PATH+"UserSaqueScreen.fxml");
		//*hashMap.put("user_logged", PATH+"UserPrincipalScreen.fxml");
		hashMap.put("adm_main", PATH+"AdmManagerScreen.fxml");
		hashMap.put("adm_relatorio", PATH+"AdmManagerRelatorioScreen.fxml");
		hashMap.put("adm_client_reg", PATH+"AdmManagerRegistrarCliente.fxml");
		hashMap.put("adm_client_find", PATH+"AdmManagerProcurarCliente.fxml");
		hashMap.put("adm_login_screen", PATH+"AdmLoginScreen.fxml");
		hashMap.put("adm_popup_panel", PATH+"AdmPopupPasswordPanel.fxml");
		hashMap.put("about", PATH+"AboutScreen.fxml");
	}

	private void longStart() {
		mainContainer = new ScreensController();
			this.addAllScreen();
	        Task<Void> task = new Task<Void>() {
	            @Override
	            protected Void call() throws Exception {
	            	int max = hashMap.size();
	            	int i = 0;
	            	for (Map.Entry<String, String> entry: hashMap.entrySet()){
	            	   mainContainer.loadScreen(entry.getKey(), entry.getValue());
	            	   updateMessage("Carregando Módulos ["+entry.getKey()+"]...");
	            	   splashInit.handleProgressNotification(new ProgressNotification(((double) i)/max));
	            	   i++;
	            	   /*Thread.Sleep(x) pode ser removido sem problemas,
	            	    *isto serve somente para que o carregamento seja um pouco lendo,
	            	    *para que assim possa ser possível visualizar a tela de splash,
	            	    *porque os arquivos carregados são muito pequenos
	            	    */
	            	   Thread.sleep(100);
	               }

	                ready.setValue(Boolean.TRUE);
	                splashInit.handleStateChangeNotification(new StateChangeNotification(
	                    StateChangeNotification.Type.BEFORE_START));
	                return null;
	            }
	        };
	        splashInit.getProgressLabel().textProperty().bind(task.messageProperty());
	        new Thread(task).start();
	    }


	@Override
	public void start(Stage primaryStage) throws Exception {
		Platform.setImplicitExit(false);
		splashInit = new SplashInit();
		splashInit.start(primaryStage);
		this.longStart();
		ready.addListener(new ChangeListener<Boolean>(){
            public void changed(
                ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                    if (Boolean.TRUE.equals(t1)) {
                        Platform.runLater(new Runnable() {
                            public void run() {
                            	Group root = new Group();
                            	root.getChildren().addAll(mainContainer);
                            	Scene scene = new Scene(root, 600,500);
                            	Stage stage = new Stage(StageStyle.DECORATED);
								stage.setScene(scene);
								stage.setResizable(false);
								primaryStage.hide();
								mainContainer.setScreen("user_login_screen");
								//mainContainer.setScreen("adm_client_find");
								stage.setTitle("Bena Bank v1.0");
								stage.setOnCloseRequest(new EventHandler<WindowEvent>(){
									@Override
									public void handle(WindowEvent event) {
										Platform.exit();
									}
								});
								stage.show();
                            }
                        });
                    }
                }
        });
    }


	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Application.launch(args);
	}

}