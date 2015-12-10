package br.cc.arswtechsolutions.benabank.ui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import br.cc.arswtechsolutions.benabank.screenframework.IControlledScreen;
import br.cc.arswtechsolutions.benabank.screenframework.ScreensController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class AboutScreenController  implements Initializable, IControlledScreen {

	@FXML
	private Label lblText;

	@FXML
	private Label lblTitle;

	@FXML
	private Button btnOk;

	private ScreensController myController;

	@FXML
	void btnOkOnAction (ActionEvent event) {
		this.myController.setScreen("adm_main");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		this.lblText.setText(String.format("Versão: 1.0.0.0%n%n" +
		                     "Software desenvolvido para o projeto da disciplina de%n" +
				             "Metodologia e Linguagem de Programação III do%n" +
		                     "Centro Universitário de João Pessoa - UNIPÊ.%n%n" +
				             "Integrantes:%n" +
		                     "Anderson Eraldo%n" +
				             "Raphael Ribeiro%n" +
		                     "Swellington Santos%n" +
				             "Welton Matos%n%n"));
	}

	@Override
	public void setScreenParent(ScreensController screenPage) {
		// TODO Auto-generated method stub
		this.myController = screenPage;
	}

}
