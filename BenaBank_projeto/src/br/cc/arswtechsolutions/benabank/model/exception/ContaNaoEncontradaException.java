package br.cc.arswtechsolutions.benabank.model.exception;

import java.io.IOException;

public class ContaNaoEncontradaException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 303964344011775471L;

	public ContaNaoEncontradaException() throws IOException {
		super("Não foi encontrada nenhuma informações com as entradas colocadas.");
		// TODO Auto-generated constructor stub
	}

}
