package br.cc.arswtechsolutions.benabank.ui.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import br.cc.arswtechsolutions.benabank.model.Cliente;
import br.cc.arswtechsolutions.benabank.model.utils.BuilderPassword;
import br.cc.arswtechsolutions.benabank.model.utils.GlobalInfor;
import br.cc.arswtechsolutions.benabank.model.utils.MessageDialog;
import br.cc.arswtechsolutions.benabank.persistence.ClienteDAO;
import br.cc.arswtechsolutions.benabank.screenframework.IControlledScreen;
import br.cc.arswtechsolutions.benabank.screenframework.ScreensController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.VBox;

public class UserSenhaScreenController implements Initializable, IControlledScreen {

    @FXML
    private ResourceBundle resources;

    private List<String> senha_user = new ArrayList<String>();

    @FXML
    private URL location;

    @FXML
    private Button btnConfirmar;

    @FXML
    private VBox vBoxMain;

    @FXML
    private Button btnCorrigir;

    @FXML
    private Button btn03;

    @FXML
    private Button btn02;

    @FXML
    private Button btn01;

    @FXML
    private Button btn05;

    @FXML
    private Button btn04;

    @FXML
    private Button btnMenu;

    @FXML
    private PasswordField editSenha;

	private ScreensController myController;

	private Cliente cliente;

	private double valorToTransfer;

	@FXML
    void OnBtnAction(ActionEvent event) {
		MessageDialog md = new MessageDialog();
    	Button evt = (Button) event.getSource();
    	if (evt.getText().toLowerCase().equals("confirmar")){
    		if (BuilderPassword.decryptPassword(senha_user, GlobalInfor.cliente_logged.getSenha())){
    			if (GlobalInfor.cliente_logged.transferir(cliente, valorToTransfer)){
    				md.setDialogMessage(String.format("O valor de R$ %.2f foi transferido com sucesso\n"
    							+ "para a conta do Sr(a) %s", this.valorToTransfer, this.cliente.getNome()));
    				md.showDialog();
    				ClienteDAO cd = new ClienteDAO();
    				cd.atualizar(cliente);
    				cd.atualizar(GlobalInfor.cliente_logged);
    				this.myController.loadScreen("user_logged", "./br/cc/arswtechsolutions/benabank/ui/views/UserPrincipalScreen.fxml");
					this.myController.setScreen("user_logged");
    			} else {
    				md.setDialogMessage("Não foi possível transferir, verifique as informações,"
    						+ "ou entre em contato com o administrador do sistema.");
    				md.showDialog();
    			}
    		} else {//Senhal Incorreta
    			md.setDialogMessage("Senha incorreta");
    			md.showDialog();
    			this.editSenha.clear();
    			this.senha_user.clear();
    		}
    	} else if (evt.getText().toLowerCase().equals("corrigir")){
    		this.editSenha.clear();
    		this.senha_user.clear();
    	} else if (evt.getText().toLowerCase().equals("menu")){
    		this.editSenha.clear();
    		this.senha_user.clear();
    		this.myController.setScreen("user_logged");
    		this.myController.unloadScreen("user_senha");
    	} else {
    		//Botões das senhas
    		this.editSenha.setText(this.editSenha.getText()+"1");
    		this.senha_user.add(evt.getText());
    	}
	}


    private void init() throws IllegalArgumentException, IllegalAccessException{
    	List<Integer> list_min = new ArrayList<Integer>();
    	for (int i = 0; i<10; ++i){
    		list_min.add(Integer.valueOf(i));
    	}
    	Collections.shuffle(list_min);
    	this.btn01.setText(String.format("%d ou %d" , list_min.get(0).intValue(), list_min.get((list_min.size()/2)).intValue()));
    	this.btn02.setText(String.format("%d ou %d", list_min.get(1).intValue(), list_min.get((list_min.size()/2) +1 ).intValue()));
    	this.btn03.setText(String.format("%d ou %d", list_min.get(2).intValue(), list_min.get((list_min.size()/2) +2 ).intValue()));
    	this.btn04.setText(String.format("%d ou %d", list_min.get(3).intValue(), list_min.get((list_min.size()/2) +3 ).intValue()));
    	this.btn05.setText(String.format("%d ou %d", list_min.get(4).intValue(), list_min.get((list_min.size()/2) +4 ).intValue()));
    }
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		try {
			this.init();
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void setScreenParent(ScreensController screenPage) {
		// TODO Auto-generated method stub
		this.myController = screenPage;
	}

	public void setCliente(Cliente cl) {
		this.cliente = cl;
	}

	public void setValueToTransfer(double valor) {
		// TODO Auto-generated method stub
		this.valorToTransfer = valor;
	}

}
