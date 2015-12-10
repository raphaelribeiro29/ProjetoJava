package br.cc.arswtechsolutions.benabank.persistence;

import java.sql.ResultSet;

public interface IDBConnection {
	public boolean open();
	public void close();
	public ResultSet execute(String Query);
}
