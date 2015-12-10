package br.cc.arswtechsolutions.benabank.ui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import br.cc.arswtechsolutions.benabank.model.LoginPanelAdm;
import br.cc.arswtechsolutions.benabank.model.exception.DefaultExceptionDialog;
import br.cc.arswtechsolutions.benabank.model.exception.LoginIncorretoException;
import br.cc.arswtechsolutions.benabank.model.utils.GlobalInfor;
import br.cc.arswtechsolutions.benabank.screenframework.IControlledScreen;
import br.cc.arswtechsolutions.benabank.screenframework.ScreensController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AdmLoginScreenController implements Initializable, IControlledScreen {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Label lbLogo;

	@FXML
	private TextField editLogin;

	@FXML
	private Button btnEntrar;

	@FXML
	private Button btnVoltar;

	@FXML
	private Group MainGroup;

	@FXML
	private AnchorPane mainPane;

	private ScreensController myController;

	@FXML
    void editLoginTextChanged(InputMethodEvent event) {

    }

	@FXML
	void btnVoltarOnAction(ActionEvent event) {
		this.editLogin.clear();
		((Stage) mainPane.getScene().getWindow()).setTitle("Bena Bank");
		this.myController.setScreen("user_login_screen");
	}

	@FXML
	void btnEntrarOnAction(ActionEvent event) throws LoginIncorretoException, IOException {
		if (this.editLogin.getText().trim().isEmpty()) {
			DefaultExceptionDialog de = new DefaultExceptionDialog("Campo de login não pode ficar em branco");
			de.showDialog();
		} else {
			GlobalInfor.admName = this.editLogin.getText();
			Stage stage = (Stage) this.btnEntrar.getScene().getWindow();
			stage.setAlwaysOnTop(false);
			stage.toFront();
			LoginPanelAdm login = new LoginPanelAdm();
			login.setScreenParent(myController);
			login.showPanel(event);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setScreenParent(ScreensController screenPage) {
		// TODO Auto-generated method stub
		this.myController = screenPage;
	}

}
