package br.cc.arswtechsolutions.benabank.model;


public interface IContaActions {
	public void creditar (double valor);
	public double debitar (double valor);
	public boolean transferir (Cliente c, double valor);
}
