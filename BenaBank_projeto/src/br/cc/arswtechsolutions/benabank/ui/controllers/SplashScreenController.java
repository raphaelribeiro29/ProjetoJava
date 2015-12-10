package br.cc.arswtechsolutions.benabank.ui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class SplashScreenController implements Initializable {

	@FXML
	protected Label lbCarregando;
	@FXML
	protected ProgressBar pbCarregando;


	public Label getLbCarregando() {
		return lbCarregando;
	}

	public ProgressBar getPbCarregando() {
		return pbCarregando;
	}

	public void setPbCarregando(ProgressBar pbCarregando) {
		this.pbCarregando = pbCarregando;
	}

	public void setLbCarregando(Label lbCarregando) {
		this.lbCarregando = lbCarregando;
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
	
	
}