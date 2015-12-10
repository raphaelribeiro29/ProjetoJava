package br.cc.arswtechsolutions.benabank.ui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import br.cc.arswtechsolutions.benabank.model.Cliente;
import br.cc.arswtechsolutions.benabank.model.utils.FormatMask;
import br.cc.arswtechsolutions.benabank.model.utils.MessageDialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class AdmUserInforScreenController implements Initializable {

	private Cliente cliente;
	private Stage dialogStage;
	private boolean okClicked = false;
	private final ObservableList<String> tipoConta = FXCollections.observableArrayList("1. Conta corrente",
			"2. Conta poupança");

	public void setDialogStage(Stage ds) {
		this.dialogStage = ds;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
		this.edClienteAgencia.setText(Long.toString(cliente.getAgencia()));
		this.edClienteConta.setText(Long.toString(cliente.getConta()));
		this.edClienteCPF.setText(FormatMask.format("###.###.###-##", cliente.getCpf()));
		this.edClienteId.setText(Long.toString(cliente.getId()));
		this.edClienteNome.setText(cliente.getNome());
		this.edClienteSaldo.setText(Double.toString(cliente.getSaldo()));
		this.edClienteSenha.setText(cliente.getSenha());
		if (cliente.isTipoConta())
			this.cbClienteTipoConta.getSelectionModel().select(0);
		else this.cbClienteTipoConta.getSelectionModel().select(1);
	}

	public boolean isOkClicked() {
		return okClicked;
	}

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private ComboBox<String> cbClienteTipoConta;

	@FXML
	private Button btnSalvar;

	@FXML
	private Button btnCancelar;

	@FXML
	private TextField edClienteCPF;

	@FXML
	private TextField edClienteId;

	@FXML
	private TextField edClienteSaldo;

	@FXML
	private TextField edClienteNome;

	@FXML
	private PasswordField edClienteSenha;

	@FXML
	private TextField edClienteConta;

	@FXML
	private TextField edClienteAgencia;

	@FXML
	void BtnSaveOnAction(ActionEvent event) {
		if (isInputValid()) {
			this.cliente.setSenha(this.edClienteSenha.getText());
			this.cliente.setTipoConta(this.cbClienteTipoConta.getSelectionModel().getSelectedIndex()==0?true:false);
			this.cliente.setNome(this.edClienteNome.getText());
			this.cliente.setAgencia(Long.valueOf(this.edClienteAgencia.getText()));
			this.dialogStage.close();
			this.okClicked = true;
		}
	}

	@FXML
	void btnCancelOnAction(ActionEvent event) {
		this.dialogStage.close();
	}

	private boolean isInputValid() {
		// TODO Auto-generated method stub
		String errorMessage = "";
		if (this.edClienteSenha.getText() == null || (this.edClienteSenha.getText().length() < 0 && this.edClienteSenha.getText().length() > 8) ||
				(this.edClienteSenha.getText().matches("\\d+") == false)){
			errorMessage += "Senha não pode ficar em branco e deve ser no máximo 8 digitos";
		} else
		if (this.edClienteAgencia.getText() == null || this.edClienteAgencia.getText().length() == 0 || !this.edClienteAgencia.getText().matches("\\d+"))
			errorMessage += "Campo de Agência inválido";
		else if (this.edClienteNome.getText() == null || (this.edClienteNome.getText().length() == 0)){
			errorMessage += "Nome inválido";
		}

		if (errorMessage.length() == 0){
			return true;
		} else {
			MessageDialog md = new MessageDialog();
			md.setBallonType(AlertType.ERROR);
			md.setDigloaHeader("Por favorm corriga os campos inválidos");
			md.setDialogMessage(errorMessage);
			md.showDialog();
			return false;
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		this.cbClienteTipoConta.setItems(tipoConta);
		this.cbClienteTipoConta.getSelectionModel().select(0);
	}
}
