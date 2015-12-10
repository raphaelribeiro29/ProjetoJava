package br.cc.arswtechsolutions.benabank.ui.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.cc.arswtechsolutions.benabank.model.Cliente;
import br.cc.arswtechsolutions.benabank.model.exception.DefaultExceptionDialog;
import br.cc.arswtechsolutions.benabank.model.utils.FormatMask;
import br.cc.arswtechsolutions.benabank.model.utils.MessageDialog;
import br.cc.arswtechsolutions.benabank.model.utils.Relatorio;
import br.cc.arswtechsolutions.benabank.persistence.ClienteDAO;
import br.cc.arswtechsolutions.benabank.screenframework.IControlledScreen;
import br.cc.arswtechsolutions.benabank.screenframework.ScreensController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AdmManagerProcurarClienteController implements Initializable, IControlledScreen {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private RadioButton rbtnBuscaPorCPF;

	@FXML
	private TableView<Cliente> tbViewClientes;

	@FXML
	private TableColumn<Cliente, Integer> tbColId;

	@FXML
	private TableColumn<Cliente, String> tbColCPF;

	@FXML
	private TableColumn<Cliente, String> tbColNome;

	@FXML
	private TableColumn<Cliente, Long> tbColAgencia;

	@FXML
	private TableColumn<Cliente, Long> tbColConta;

	@FXML
	private TableColumn<Cliente, Boolean> tbColTipoConta;

	@FXML
	private MenuItem cmHistorico;

	@FXML
	private MenuItem mcEditar;

	@FXML
	private RadioButton rbtnBuscaPorConta;

	@FXML
	private RadioButton rbtnBuscaPorID;

	@FXML
	private RadioButton rbtnBuscaPorAgencia;

	@FXML
	private ToggleGroup opBusca;

	@FXML
	private TextField editBuscar;

	@FXML
	private RadioButton rbtnBuscaPorNome;

	@FXML
	private MenuItem mcRemover;

	@FXML
	private Button btnLimpar;

	@FXML
	private MenuItem cmRelatorio;

	@FXML
	private Label lbQuantidadeItens;

	@FXML
	private Button btnTeste;


	private ObservableList<Cliente> data = FXCollections.observableArrayList();
	private List<Cliente> listClients;
	FilteredList<Cliente> filteredData = new FilteredList<>(data, p -> true);
	private ScreensController myController;

	@FXML
	void btnEditarTeste(ActionEvent evt) {
		if (this.tbViewClientes.getSelectionModel().getSelectedItem() != null) {
			Cliente selectedCliente = this.tbViewClientes.getSelectionModel().getSelectedItem();
			if (selectedCliente != null) {
				boolean okClicked = this.ShowClienteDialog(selectedCliente);
				if (okClicked) {
					this.updateCliente(selectedCliente);
				}
			}
		}
	}


	@FXML
	void btnOnAction(ActionEvent evt) {
		Button btn = (Button) evt.getSource();
		if (btn.getText().toLowerCase().equals("limpar")) {
			this.editBuscar.clear();
			data.clear();
			this.listClients.clear();
			ClienteDAO cd = new ClienteDAO();
			this.listClients = cd.listClients();
			data.addAll(this.listClients);
		}
	}

	@FXML
	void ContexMenuOnAction(ActionEvent event) throws FileNotFoundException {
		MenuItem menu = (MenuItem) event.getSource();
		if (menu.getText().toLowerCase().contains("relatório")) {
			if (this.tbViewClientes.getSelectionModel().getSelectedItems().size() > 0) {
				Relatorio h = new Relatorio();
				for (Cliente cliente : this.tbViewClientes.getSelectionModel().getSelectedItems()) {
					h.addClients(cliente);
				}
				h.salvarRelatorio();
				File f = new File(h.getFilePath());
				if (f.exists()) {
					MessageDialog md = new MessageDialog();
					md.setDialogMessage("Relatório gravado com sucess!" + "\nVerique a pasta onde está o aplicativo.");
					md.showDialog();
				} else {
					MessageDialog md = new MessageDialog();
					md.setBallonType(AlertType.ERROR);
					md.setDialogMessage("Por algum motivo não foi possível gerar o relatório.\n"
							+ "Por favor entre em contato com um administrador.");
					md.showDialog();
				}
			}

		} else if (menu.getText().toLowerCase().equals("remover")) {
			if (tbViewClientes.getSelectionModel().getSelectedItems().size() > 0) {
				ClienteDAO cd = new ClienteDAO();
				for (Cliente cliente : this.tbViewClientes.getSelectionModel().getSelectedItems()) {
					cd.remover(cliente);
					data.remove(cliente);
				}
				MessageDialog message = new MessageDialog("BenaBank", null, "Usuário(s) removido(s) com sucesso.",
						AlertType.INFORMATION);
				message.showDialog();
			} else {
				MessageDialog message = new MessageDialog("BenaBank", null, "É preciso selecionar pelo menos um item.",
						AlertType.INFORMATION);
				message.showDialog();
			}
		} else if (menu.getText().toLowerCase().equals("editar")) {
			this.btnEditarTeste(null);
		}
	}

	public void updateCliente(Cliente cliente) {
		if (cliente != null) {
			ClienteDAO clienteDAO = new ClienteDAO();
			if (clienteDAO.atualizar(cliente) == true) {
				this.data.clear();
				this.listClients.clear();
				this.listClients = clienteDAO.listClients();
				this.data.addAll(this.listClients);
				MessageDialog md = new MessageDialog();
				md.setBallonType(AlertType.INFORMATION);
				md.setDialogMessage("Cadastro do Sr(a) "+ cliente.getNome()+ "\natualizado com sucesso.");
				md.showDialog();
			} else {
				DefaultExceptionDialog ed = new DefaultExceptionDialog("Por algum motivo não foi possível atualizar os dados.");
				ed.showDialog();
			}
		}
	}

	public boolean ShowClienteDialog(Cliente cliente) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader()
					.getResource("./br/cc/arswtechsolutions/benabank/ui/views/AdmUserInforEditScreen.fxml"));
			Parent page = (Parent) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Editar cadastro");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(this.btnLimpar.getScene().getWindow());
			dialogStage.setResizable(false);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			AdmUserInforScreenController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setCliente(cliente);
			dialogStage.showAndWait();
			return controller.isOkClicked();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.tbColId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
		this.tbColCPF.setCellValueFactory(cellData -> cellData.getValue().cpfProperty());
		this.tbColNome.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
		this.tbColAgencia.setCellValueFactory(cellData -> cellData.getValue().agenciaProperty().asObject());
		this.tbColConta.setCellValueFactory(cellData -> cellData.getValue().contaProperty().asObject());
		this.tbColTipoConta.setCellValueFactory(cellData -> cellData.getValue().tipoContaProperty().asObject());
		ClienteDAO cd = new ClienteDAO();
		this.listClients = cd.listClients();
		data.addAll(this.listClients);
		this.tbViewClientes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		this.tbViewClientes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		this.editBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(cliente -> {
				if (newValue == null || newValue.isEmpty())
					return true;

				String filter = newValue.toLowerCase();

				if (this.rbtnBuscaPorID.isSelected()) {
					if (Integer.toString(cliente.getId()).contains(filter))
						return true;
				}

				if (this.rbtnBuscaPorNome.isSelected()) {
					if (cliente.getNome().toLowerCase().contains(filter))
						return true;

				}
				if (this.rbtnBuscaPorCPF.isSelected()) {
					if (FormatMask.format("###.###.###-##", cliente.getCpf()).toLowerCase().contains(filter))
						return true;

				}

				if (this.rbtnBuscaPorAgencia.isSelected()) {
					if (Long.toString(cliente.getAgencia()).toLowerCase().contains(filter))
						return true;

				} else if (this.rbtnBuscaPorConta.isSelected()) {
					if (Long.toString(cliente.getConta()).toLowerCase().contains(filter))
						return true;
				}
				return false;
			});
		});

		SortedList<Cliente> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(this.tbViewClientes.comparatorProperty());

		this.tbViewClientes.setItems(sortedData);

		this.tbViewClientes.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Cliente>() {
			@Override
			public void changed(ObservableValue<? extends Cliente> observable, Cliente oldValue, Cliente newValue) {
				// TODO Auto-generated method stub
				if (tbViewClientes.getSelectionModel().getSelectedItems() != null) {
					lbQuantidadeItens.setText("Itens: "
							+ Integer.valueOf(tbViewClientes.getSelectionModel().getSelectedItems().size()).toString());
				}
			}
		});

		this.tbColCPF.setCellFactory(column -> {
			return new TableCell<Cliente, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					if (item == null || empty) {
						setText(null);
					} else {
						setText(FormatMask.format("###.###.###-##", item));
					}
				}
			};
		});

		this.tbColTipoConta.setCellFactory(column -> {
			return new TableCell<Cliente, Boolean>() {
				@Override
				protected void updateItem(Boolean item, boolean empty) {
					super.updateItem(item, empty);
					if (!empty) {
						setText(item == Boolean.TRUE ? "Conta corrente" : "Conta Poupança");
					}
				}
			};
		});
	}

	@Override
	public void setScreenParent(ScreensController screenPage) {
		// TODO Auto-generated method stub
		this.myController = screenPage;
	}

}
