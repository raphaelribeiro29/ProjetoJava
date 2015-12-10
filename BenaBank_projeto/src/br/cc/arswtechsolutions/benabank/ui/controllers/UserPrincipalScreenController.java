package br.cc.arswtechsolutions.benabank.ui.controllers;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.ResourceBundle;

import br.cc.arswtechsolutions.benabank.model.exception.DefaultExceptionDialog;
import br.cc.arswtechsolutions.benabank.model.utils.GlobalInfor;
import br.cc.arswtechsolutions.benabank.model.utils.MessageDialog;
import br.cc.arswtechsolutions.benabank.persistence.ClienteDAO;
import br.cc.arswtechsolutions.benabank.screenframework.IControlledScreen;
import br.cc.arswtechsolutions.benabank.screenframework.ScreensController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;

public class UserPrincipalScreenController implements Initializable, Observer, IControlledScreen {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Label lbContaTipoNome;

	@FXML
	private Label lbClienteNome;

	@FXML
	private Label lbAgenciaNome;

	@FXML
	private Button btnSaldo;

	@FXML
	private Label lbSaldo;

	@FXML
	private Button btnSair;

	@FXML
	private Button btnDeposito;

	@FXML
	private Label lbContaNome;

	@FXML
	private Button btnSaque;

	@FXML
	private Button btnRelatorio;

	@FXML
	private Button btnTransferencia;

	private ScreensController myController;

	@FXML
	void btnOnAction(ActionEvent event) {
		Button btn = (Button) event.getSource();
		if (btn.getText().contains("Saq") == true) {
			TextInputDialog dialog = new TextInputDialog();
			dialog.setTitle("Saque");
			dialog.setContentText("Coloque o valor que deseja sacar: ");
			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()) {
				try {
					if (GlobalInfor.cliente_logged != null) {
						GlobalInfor.cliente_logged.debitar(Double.parseDouble(result.get()));
						//*this.lbSaldo.setText(String.format(" %.2f", GlobalInfor.cliente_logged.getSaldo()));
						ClienteDAO cd = new ClienteDAO();
						cd.atualizar(GlobalInfor.cliente_logged);
					}
				} catch (NumberFormatException e) {
					DefaultExceptionDialog ed = new DefaultExceptionDialog(
							"Verifique o valor digitado. Use . (ponto)\nem vez de , (vírgula) para separar a\nparte dos centavos.");
					ed.showDialog();
				} catch (IllegalArgumentException e) {
					DefaultExceptionDialog ed = new DefaultExceptionDialog(e.getMessage());
					ed.showDialog();
				}
			}
		} else if (btn.getText().contains("Relat") == true) {
			MessageDialog md = new MessageDialog();
			md.setBallonType(AlertType.INFORMATION);
			md.setDialogTitle("Bena Bank: Suporte");
			md.setDialogMessage("Função não implementada.");
			md.showDialog();
		} else if (btn.getText().contains("Trans") == true) {
			this.myController.unloadScreen("user_transf");
			this.myController.loadScreen("user_transf",
					"./br/cc/arswtechsolutions/benabank/ui/views/UserScreenTransferencia.fxml");
			this.myController.setScreen("user_transf");
		} else if (btn.getText().contains("Dep") == true) {
			TextInputDialog dialog = new TextInputDialog();
			dialog.setTitle("Deposito");
			dialog.setContentText("Coloque o valor a ser depositado: ");
			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()) {
				try {
					if (GlobalInfor.cliente_logged != null) {
						GlobalInfor.cliente_logged.creditar(Double.parseDouble(result.get()));
						//*this.lbSaldo.setText(String.format(" %.2f", GlobalInfor.cliente_logged.getSaldo()));
						ClienteDAO cd = new ClienteDAO();
						cd.atualizar(GlobalInfor.cliente_logged);
					}
				} catch (NumberFormatException e) {
					DefaultExceptionDialog ed = new DefaultExceptionDialog(
							"Verifique o valor digitado. Use . (ponto)\nem vez de , (vírgula) para separar a\nparte dos centavos.");
					ed.showDialog();
				} catch (IllegalArgumentException e) {
					DefaultExceptionDialog ed = new DefaultExceptionDialog(e.getMessage());
					ed.showDialog();
				}
			}
		} else
			if (btn.getText().toLowerCase().contains("sair")){
				//Evento do botão voltar
			GlobalInfor.cliente_logged = null;
			this.myController.unloadScreen("user_logged");
			this.myController.setScreen("user_login_screen");
			}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.resources = resources;
		this.location = location;
		if (GlobalInfor.cliente_logged != null) {
			this.lbSaldo.textProperty().bind(GlobalInfor.cliente_logged.saldoProperty().asString());
			this.lbAgenciaNome.textProperty().bind(GlobalInfor.cliente_logged.agenciaProperty().asString());
			this.lbClienteNome.textProperty().bind(GlobalInfor.cliente_logged.nomeProperty());
			this.lbContaNome.textProperty().bind(GlobalInfor.cliente_logged.contaProperty().asString());
			if (GlobalInfor.cliente_logged.isTipoConta())
				this.lbContaTipoNome.setText("Conta corrente");
			else this.lbContaNome.setText("Conta poupança");
		} else {
			DefaultExceptionDialog ed = new DefaultExceptionDialog(
					"Não foi possível carregar o recurso \"CLIENT LOGIN: MANAGER\"");
			ed.showDialog();
			this.myController.unloadScreen("user_logged");
			this.myController.setScreen("user_login_screen");
		}
	}

	@Override
	public void setScreenParent(ScreensController screenPage) {
		// TODO Auto-generated method stub
		this.myController = screenPage;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}
}
