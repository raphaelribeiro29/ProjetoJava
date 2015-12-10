package br.cc.arswtechsolutions.benabank.ui.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import br.cc.arswtechsolutions.benabank.model.exception.DefaultExceptionDialog;
import br.cc.arswtechsolutions.benabank.model.exception.LoginIncorretoException;
import br.cc.arswtechsolutions.benabank.model.utils.GlobalInfor;
import br.cc.arswtechsolutions.benabank.model.utils.MessageDialog;
import br.cc.arswtechsolutions.benabank.persistence.AdministradorLogin;
import br.cc.arswtechsolutions.benabank.screenframework.IControlledScreen;
import br.cc.arswtechsolutions.benabank.screenframework.ScreensController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AdmPopupPasswordPanelController implements Initializable, IControlledScreen {

	private List<String> adm_password = new ArrayList<String>();

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button btnN10;

	@FXML
	private Button btnOk;

	@FXML
	private Button btnN1;

	@FXML
	private Button btnApagar;

	@FXML
	private PasswordField edSenha;

	@FXML
	private Button btnN5;

	@FXML
	private Button btnN4;

	@FXML
	private Button btnN3;

	@FXML
	private Button btnN2;

	@FXML
	private Button btnN9;

	@FXML
	private Button btnN8;

	@FXML
	private Button btnN7;

	@FXML
	private Button btnN6;

	 @FXML
	 private BorderPane mainPane;

	private ScreensController myController;


	@FXML
	void OnTeste(ActionEvent event)
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		ini_controllers();
	}

	protected void ini_controllers() {
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < 10; i++)
			list.add(Integer.valueOf(i));
		Collections.shuffle(list);
		this.btnN1.setText(list.get(0).toString());
		this.btnN2.setText(list.get(1).toString());
		this.btnN3.setText(list.get(2).toString());
		this.btnN4.setText(list.get(3).toString());
		this.btnN5.setText(list.get(4).toString());
		this.btnN6.setText(list.get(5).toString());
		this.btnN7.setText(list.get(6).toString());
		this.btnN8.setText(list.get(7).toString());
		this.btnN9.setText(list.get(8).toString());
		this.btnN10.setText(list.get(9).toString());
	}


	@FXML
	private void btn_actionClick(ActionEvent event) throws RuntimeException, IOException {
		Button tmp = (Button) event.getSource();
		Stage stage = (Stage) tmp.getScene().getWindow();
		if (tmp.getText().equals("<")) {
			this.edSenha.clear();
		} else if (tmp.getText().equals("OK")) {
			// entra da rotina de logar no sistema
			try {
				if (this.edSenha.getText().length() > 0){
					AdministradorLogin al = new AdministradorLogin(GlobalInfor.admName, this.adm_password);
					if (al.login()){
						//some com o painel de controle
						this.myController.setScreen("adm_main");
						System.out.println("Logado");
						stage.close();
					}
				} else {
					DefaultExceptionDialog de = new DefaultExceptionDialog("Campo de senha não pode ficar em branco.");
					de.showDialog();
					this.edSenha.setFocusTraversable(true);
				}
			} catch (LoginIncorretoException e) {
				DefaultExceptionDialog de = new DefaultExceptionDialog(e.getMessage());
				de.showDialog();
			} catch (SQLException e){
				DefaultExceptionDialog de = new DefaultExceptionDialog("Erro no banco de dados");
				de.showDialog();
				stage.close();
			}finally {
				this.adm_password.clear();
				this.edSenha.clear();
			}
		} else {
			if (this.edSenha.getText().length() < 9) {
				this.edSenha.setText(this.edSenha.getText() + "1");
				this.adm_password.add(tmp.getText());
				this.ini_controllers();
			}
		}
		System.out.println("Debug: " + this.edSenha.getText());
	}

    @FXML
    void mainPaneOnKeyPressed(KeyEvent event) {

    }

	private void installEventHandler(final Node keyNode) {
	    // handler for enter key press / release events, other keys are
	    // handled by the parent (keyboard) node handler
	    final EventHandler<KeyEvent> keyEventHandler =
	        new EventHandler<KeyEvent>() {
	            public void handle(final KeyEvent keyEvent) {
	                if (keyEvent.getCode().isDigitKey()) {
	                	MessageDialog md = new MessageDialog();
	                	md.setBallonType(AlertType.INFORMATION);
	                	md.setDialogMessage("Use o teclado numerico");
	                	md.showDialog();
	                	//JOptionPane.showMessageDialog(null, "Use o teclado númerico");
	                    keyEvent.consume();
	                }
	            }
	        };

	    keyNode.setOnKeyPressed(keyEventHandler);
	    keyNode.setOnKeyReleased(keyEventHandler);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ini_controllers();
		//this.btnApagar.getParent().addEventHandler(eventType, eventHandler);
		this.installEventHandler((Node)this.mainPane);
	}

	@Override
	public void setScreenParent(ScreensController screenPage) {
		// TODO Auto-generated method stub
		this.myController = screenPage;
	}

}
