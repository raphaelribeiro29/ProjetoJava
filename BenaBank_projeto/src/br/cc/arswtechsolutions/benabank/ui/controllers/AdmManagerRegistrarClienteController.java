package br.cc.arswtechsolutions.benabank.ui.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import br.cc.arswtechsolutions.benabank.model.Cliente;
import br.cc.arswtechsolutions.benabank.model.exception.ContaJaCadastradaException;
import br.cc.arswtechsolutions.benabank.model.exception.DefaultExceptionDialog;
import br.cc.arswtechsolutions.benabank.persistence.ClienteDAO;
import br.cc.arswtechsolutions.benabank.screenframework.IControlledScreen;
import br.cc.arswtechsolutions.benabank.screenframework.ScreensController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

public class AdmManagerRegistrarClienteController implements Initializable, IControlledScreen{

	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnSalvar;

    @FXML
    private TextField editSaldoInicial;

    @FXML
    private TextField editCPF;

    @FXML
    private Button btnCancelar;

    @FXML
    private TextField editNome;

    @FXML
    private ToggleGroup contaEscola;

    @FXML
    private RadioButton rbContaPoupanca;

    @FXML
    private PasswordField editSenha;

    @FXML
    private TextField editAgencia;

    @FXML
    private RadioButton rbContaCorrente;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private TextField editConta;

    private ScreensController myController;

    @FXML
    void btnCancelarOnAction(ActionEvent event) {
    	//this.btnCancelar.getParent().setVisible(false);
    	this.ClearComponents();
    	this.mainPane.setVisible(false);
    }

    @FXML
    void btnSalvarOnAction(ActionEvent event) throws ContaJaCadastradaException, SQLException, IOException {
    	DefaultExceptionDialog message = new DefaultExceptionDialog("");

    	Cliente cliente = null;
    	try {
    		cliente = new Cliente();
    		cliente.setAgencia(Long.parseLong(this.editAgencia.getText()));
    		cliente.setConta(Long.parseLong(this.editConta.getText()));
    		cliente.setCpf(this.editCPF.getText());
    		cliente.setNome(this.editNome.getText());
    		cliente.setSaldo(Double.parseDouble(this.editSaldoInicial.getText()));
    		cliente.setSenha(this.editSenha.getText());
    		cliente.setTipoConta(this.rbContaCorrente.isSelected());
    		ClienteDAO clienteDAO = new ClienteDAO();
    		if (clienteDAO.adicionar(cliente) == true){
    			message.setBallonType(AlertType.INFORMATION);
    			message.setDialogTitle("Cadastrado: Cliente");
    			message.setDialogMessage("Cliente cadastrado com sucesso");
    			message.showDialog();
    			this.ClearComponents();
    		} else {
    			message.setBallonType(AlertType.ERROR);
    			message.setDialogMessage("Houve algum erro no cadastro do cliente, verifique os parâmetros.");
    			message.showDialog();
    		}
    	} catch (ContaJaCadastradaException e){
    		message.setDialogMessage(e.getMessage());
    		message.showDialog();
    		System.out.println("Debug: BtnSalvarOnAction@AdmManagerRegistrarCliente: "+e.getMessage());
    	} catch (IOException e){
    		message.setDialogMessage(e.getMessage());
    		message.showDialog();
    		System.out.println("Debug: BtnSalvarOnAction@AdmManagerRegistrarCliente: "+e.getMessage());
    	} catch (IllegalArgumentException e){
    		message.setDialogMessage(e.getMessage());
    		message.showDialog();
    		System.out.println("Debug: BtnSalvarOnAction@AdmManagerRegistrarCliente: "+e.getMessage());
    	} catch (Exception e){
    		message.setDialogMessage("Por algum motivo não foi possível adicionar o cliente no banco de dados.");
    		message.showDialog();
    		System.out.println("Debug: BtnSalvarOnAction@AdmManagerRegistrarCliente: "+e.getMessage());
    	} finally {
    		cliente = null;
    	}

    }


	private void ClearComponents() {
		this.editAgencia.clear();
		this.editConta.clear();
		this.editCPF.clear();
		this.editNome.clear();
		this.editSaldoInicial.clear();
		this.editSenha.clear();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.resources = resources;
		this.location = location;
	}

	@Override
	public void setScreenParent(ScreensController screenPage) {
		// TODO Auto-generated method stub
		//this.setScreenParent(screenPage);
		this.myController = screenPage;
	}

}
