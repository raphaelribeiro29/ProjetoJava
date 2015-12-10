package br.cc.arswtechsolutions.benabank.model.exception;

import br.cc.arswtechsolutions.benabank.model.utils.MessageDialog;
import javafx.scene.control.Alert;

public class DefaultExceptionDialog extends MessageDialog {

	public DefaultExceptionDialog(String dialogMessage) {
		super("Bena Bank", null, dialogMessage, Alert.AlertType.ERROR);
		// TODO Auto-generated constructor stub
	}


}
