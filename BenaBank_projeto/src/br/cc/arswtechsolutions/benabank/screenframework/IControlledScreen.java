package br.cc.arswtechsolutions.benabank.screenframework;

public interface IControlledScreen {
	//Iste método permite a injeção de telas no ScreenPane
	public void setScreenParent(ScreensController screenPage);
}
