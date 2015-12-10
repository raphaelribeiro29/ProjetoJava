package br.cc.arswtechsolutions.benabank.model.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MessageDialog {

	private String dialogTitle;
	private String dialogHeader;
	private String dialogMessage;
	private AlertType ballon;

	public void setBallonType(AlertType ballon){
		this.ballon = ballon;
	}

	public String getDialogTitle() {
		return dialogTitle;
	}

	public void setDialogTitle(String dialogTitle) {
		if (dialogTitle.trim() == null)
			this.dialogTitle = "Bena Bank: Erro";
		else
			this.dialogTitle = dialogTitle;
	}

	public void setDigloaHeader(String dialogHeader) {
		this.dialogHeader = dialogHeader;

	}

	public void setDialogMessage(String dialogMessage) {
		if (dialogMessage.trim() == null)
			this.dialogMessage = "Erro interno.";
		else
			this.dialogMessage = dialogMessage;

	}

	public MessageDialog(String dialogTitle, String dialogHeader, String dialogMessage, AlertType ballon) {
		this.dialogTitle = dialogTitle;
		this.dialogHeader = dialogHeader;
		this.dialogMessage = dialogMessage;
		this.ballon = ballon;
	}

	public MessageDialog() {
		this(null, null, null, AlertType.INFORMATION);
	}

	public void showDialog() {
		try {
			Alert alert = new Alert(ballon);
			alert.setTitle(this.dialogTitle);
			alert.setHeaderText(this.dialogHeader);
			alert.setContentText(dialogMessage);
			alert.showAndWait();
		} catch (Exception e) {
			System.out.println("Houve um erro desconhecido");
		}
	}
}
