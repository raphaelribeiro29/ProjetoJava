package br.cc.arswtechsolutions.benabank.test;

import java.util.ArrayList;
import java.util.Collection;

import br.cc.arswtechsolutions.benabank.model.Cliente;
import junit.framework.TestCase;

public class ClienteTest extends TestCase{

	private Collection<Object> colec;
	private Cliente client;

	@Override
	protected void setUp() {
		colec = new ArrayList<>();
		client = new Cliente();
	}

	@Override
	protected void tearDown() {
		colec.clear();
	}

	public void testCreditar(){
		client.creditar(1000.0);
		assertEquals(client.getSaldo(), 1000.00);
	}

	public void testDebitar(){
		client.creditar(10000);
		assertTrue(client.debitar(1000)>0);
	}

	public void testTransferir(){
		Cliente c = new Cliente();
		c.creditar(1000);
		client.creditar(100);
		assertTrue(client.transferir(c, 10));

	}
}
