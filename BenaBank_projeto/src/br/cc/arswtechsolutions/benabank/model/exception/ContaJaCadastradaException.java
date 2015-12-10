package br.cc.arswtechsolutions.benabank.model.exception;

import java.io.IOException;

public class ContaJaCadastradaException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 6981751099275877256L;

	public ContaJaCadastradaException() throws IOException {
		super("Já existe uma conta cadastrada.");
		// TODO Auto-generated constructor stub

	}
}
