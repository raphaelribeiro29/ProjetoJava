package br.cc.arswtechsolutions.benabank.ui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import br.cc.arswtechsolutions.benabank.model.Cliente;
import br.cc.arswtechsolutions.benabank.model.exception.DefaultExceptionDialog;
import br.cc.arswtechsolutions.benabank.model.exception.LoginIncorretoException;
import br.cc.arswtechsolutions.benabank.model.utils.GlobalInfor;
import br.cc.arswtechsolutions.benabank.persistence.ClienteDAO;
import br.cc.arswtechsolutions.benabank.screenframework.IControlledScreen;
import br.cc.arswtechsolutions.benabank.screenframework.ScreensController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class UserScreenTransferenciaController implements Initializable, IControlledScreen {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button btnConfirmar;

	@FXML
	private Label lbContaTipoNome;

	@FXML
	private TextField editValor;

	@FXML
	private Button btnVoltar;

	@FXML
	private Label lbClienteNome;

	@FXML
	private Label lbAgenciaNome;

	@FXML
	private Label lbSaldo;

	@FXML
	private TextField editAgencia;

	@FXML
	private Label lbContaNome;

	@FXML
	private TextField editConta;

	private ScreensController myController;

	@FXML
	void btnVoltarOnAction(ActionEvent event) {
		this.ClearComponents();
		this.myController.unloadScreen("user_logged");
		this.myController.loadScreen("user_logged", "./br/cc/arswtechsolutions/benabank/ui/views/UserPrincipalScreen.fxml");
		this.myController.setScreen("user_logged");
	}

	@FXML
	void btnConfirmarOnAction(ActionEvent event) throws RuntimeException, IOException {
		DefaultExceptionDialog message = new DefaultExceptionDialog("");
		message.setDialogTitle("Transferência");
		ClienteDAO cd = new ClienteDAO();
		try {
			Cliente cl = cd.login(Long.valueOf(this.editConta.getText()), Long.valueOf(this.editAgencia.getText()));
			if (cl.getId() != GlobalInfor.cliente_logged.getId()) {
				//cria a janela de senha
				chamarTelaSenha(cl, Double.valueOf(this.editValor.getText()));
			} else {
				message.setDialogMessage("Você não pode transferir para sua própria conta");
				message.showDialog();
			}
		} catch (LoginIncorretoException e) {
			message.setDialogMessage("Usuário não encontrado");
			message.showDialog();
		} catch (NumberFormatException e){
			message.setDialogMessage("Verifique as entradas.");
			message.showDialog();
		}

	}

	private void chamarTelaSenha(Cliente cl, double valor) {
		//this.myController.loadScreen("user_senha", "./br/cc/arswtechsolutions/benabank/ui/views/UserSenhaScreen.fxml");
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("./br/cc/arswtechsolutions/benabank/ui/views/UserSenhaScreen.fxml"));
			Parent root = (Parent) loader.load();
			UserSenhaScreenController cmd = (UserSenhaScreenController) loader.getController();
			System.out.println(loader.getController().toString());
			cmd.setCliente(cl);
			cmd.setValueToTransfer(valor);
			this.myController.addScreen("user_senha", root);
			this.myController.setScreen("user_senha");
			cmd.setScreenParent(myController);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.resources = resources;
		this.location = location;

		if (GlobalInfor.cliente_logged != null) {
			this.lbAgenciaNome.setText(Long.toString(GlobalInfor.cliente_logged.getAgencia()));
			this.lbContaNome.setText(Long.toString(GlobalInfor.cliente_logged.getConta()));
			this.lbSaldo.setText(String.format("%.2f", GlobalInfor.cliente_logged.getSaldo()));
			this.lbClienteNome.setText(GlobalInfor.cliente_logged.getNome());
			this.lbContaTipoNome
					.setText(GlobalInfor.cliente_logged.isTipoConta() == true ? "Conta corrente" : "Conta poupanÃ§a");
		} else {
			DefaultExceptionDialog ed = new DefaultExceptionDialog("Não foi possível carregar o recurso!");
			ed.showDialog();
			this.myController.unloadScreen("user_logged");
			this.myController.setScreen("user_login_screen");
		}
	}

	@Override
	public void setScreenParent(ScreensController screenPage) {
		this.myController = screenPage;
	}

	private void ClearComponents() {
		this.editAgencia.clear();
		this.editConta.clear();
		this.editValor.clear();
	}

}
