package br.cc.arswtechsolutions.benabank.ui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import br.cc.arswtechsolutions.benabank.screenframework.IControlledScreen;
import br.cc.arswtechsolutions.benabank.screenframework.ScreensController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;


public class AdmManagerScreenController implements Initializable, IControlledScreen {

	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MenuItem mSair;

    @FXML
    private MenuItem mCadastrarCliente;

    @FXML
    private MenuItem mSobre;

    @FXML
    private MenuItem mRelatorioVer;

    @FXML
    private Button btnCadastrarCliente;

    @FXML
    private MenuItem mProcurarCliente;

    @FXML
    private MenuItem mCadastrarAgencia;


    @FXML
    private Button btnProcurarCliente;

    @FXML
    private Button btnRelatorio;

    @FXML
    private Group frame;

	private ScreensController myController;

    @FXML
    void mCadastrarClienteOnAction(ActionEvent event) throws IOException {
    	this.btnCadastrarClienteOnAction(event);
    }



    @FXML
    void mProcurarClienteOnAction(ActionEvent event) throws IOException {
    	this.btnProcurarClienteOnAction(event);
    }


    @FXML
    void mSairOnAction(ActionEvent event) {
    	this.myController.setScreen("adm_login_screen");
    }


    @FXML
    void mRelatorioVerOnAction(ActionEvent event) throws IOException {
    	this.btnRelatorioOnAction(event);
    }

    @FXML
    void mSobreOnAction(ActionEvent event) {
    	this.myController.setScreen("about");
    }


    @FXML
    void btnRelatorioOnAction(ActionEvent event) throws IOException {
    	FXMLLoader fxmlloader = new FXMLLoader(getClass().getClassLoader()
				.getResource("./br/cc/arswtechsolutions/benabank/ui/views/AdmManagerRelatorioScreen.fxml"));
    	Parent parent = (Parent) fxmlloader.load();
    	this.frame.getChildren().clear();
    	this.frame.getChildren().addAll((Node) parent);
    }


    @FXML
    void btnCadastrarClienteOnAction(ActionEvent event) throws IOException{
    	FXMLLoader fxmlloader = new FXMLLoader(getClass().getClassLoader()
				.getResource("./br/cc/arswtechsolutions/benabank/ui/views/AdmManagerRegistrarCliente.fxml"));
    	Parent parent = (Parent) fxmlloader.load();
    	this.frame.getChildren().clear();
    	this.frame.getChildren().addAll((Node) parent);
    }


    @FXML
    void btnProcurarClienteOnAction(ActionEvent event) throws IOException {
    	FXMLLoader fxmlloader = new FXMLLoader(getClass().getClassLoader()
				.getResource("./br/cc/arswtechsolutions/benabank/ui/views/AdmManagerProcurarCliente.fxml"));
    	Parent parent = (Parent) fxmlloader.load();
    	//AdmManagerProcurarClienteController cmd = (AdmManagerProcurarClienteController) fxmlloader.getController();
    	this.frame.getChildren().clear();
    	this.frame.getChildren().addAll((Node) parent);
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@Override
	public void setScreenParent(ScreensController screenPage) {
		// TODO Auto-generated method stub
		this.myController = screenPage;
	}

}
