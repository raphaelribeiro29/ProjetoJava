package br.cc.arswtechsolutions.benabank.persistence;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.cc.arswtechsolutions.benabank.model.Administrador;
import br.cc.arswtechsolutions.benabank.model.exception.LoginIncorretoException;

public class AdministradorDAO {

	private IDBConnection DbConnector = DBConnectionManager.getInstance();
	private final String TB_ADMINISTRADOR = "`tb_administrador`";

	public Administrador login(String admUserName)
			throws LoginIncorretoException, RuntimeException, IOException {
		final String sql = "SELECT * FROM " + TB_ADMINISTRADOR + " WHERE `LOGIN` = \"%s\"";
		String Query = String.format(sql, admUserName);
		Administrador adm = null;
		if (DbConnector.open()) {
			try {
				ResultSet rs = DbConnector.execute(Query);
				if (rs.next()) {
					adm = new Administrador(rs.getLong("ID"), rs.getString("LOGIN"),
							rs.getString("SENHA"));
				}
				rs.close();
			} catch (SQLException e) {
				System.out.println(
						"public Administrador login@Exception: " + e.getMessage() + "com código: " + e.getErrorCode());
			} finally {
				DbConnector.close();
			}
		}
		return adm;
	}
}
