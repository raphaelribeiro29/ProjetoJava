package br.cc.arswtechsolutions.benabank.persistence;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.cc.arswtechsolutions.benabank.model.Cliente;
import br.cc.arswtechsolutions.benabank.model.exception.ContaJaCadastradaException;
import br.cc.arswtechsolutions.benabank.model.exception.ContaNaoEncontradaException;
import br.cc.arswtechsolutions.benabank.model.exception.LoginIncorretoException;
import br.cc.arswtechsolutions.benabank.model.utils.Hash;

public class ClienteDAO {

	private IDBConnection DbConnector = DBConnectionManager.getInstance();
	private static final String TB_CLIENTE = "`tb_cliente`";

	public Cliente findByID(long id) throws ContaNaoEncontradaException, IOException {
		String sql = "SELECT * FROM " + TB_CLIENTE + " WHERE `ID` = %d";
		String Query = String.format(sql, id);

		Cliente cliente = null;
		if (DbConnector.open()) {
			try {
				if (DbConnector.open()) {
					ResultSet rs = DbConnector.execute(Query);
					if (rs.next()) {
						cliente = new Cliente(rs.getInt("ID"), rs.getString("CPF"), rs.getString("NOME"),
								rs.getLong("AGENCIA"), rs.getLong("CONTA"), Hash.decode(rs.getString("SENHA")),
								rs.getDouble("SALDO"), rs.getBoolean("TIPO_CONTA"));
					} else {
						throw new ContaNaoEncontradaException();
					}
					rs.close();
				}
			} catch (SQLException e) {
				System.out.println(
						"public cliente findByID@Exception: " + e.getMessage() + "com código: " + e.getErrorCode());
			} finally {
				DbConnector.close();
			}
		}
		return cliente;
	}

	public Cliente findByCPF(String cpf) throws ContaNaoEncontradaException, IOException {
		String sql = "SELECT * FROM " + TB_CLIENTE + " WHERE `CPF` = \"%s\"";
		String Query = String.format(sql, cpf);

		Cliente cliente = null;
		if (DbConnector.open()) {
			try {
				if (DbConnector.open()) {
					ResultSet rs = DbConnector.execute(Query);
					if (rs.next()) {
						cliente = new Cliente(rs.getInt("ID"), rs.getString("CPF"), rs.getString("NOME"),
								rs.getLong("AGENCIA"), rs.getLong("CONTA"), Hash.decode(rs.getString("SENHA")),
								rs.getDouble("SALDO"), rs.getBoolean("TIPO_CONTA"));
					} else {
						throw new ContaNaoEncontradaException();
					}
					rs.close();
				}
			} catch (SQLException e) {
				System.out.println(
						"public cliente findByCPF@Exception: " + e.getMessage() + "com código: " + e.getErrorCode());
			} finally {
				DbConnector.close();
			}
		}
		return cliente;

	}

	public Cliente findByConta(long conta) throws ContaNaoEncontradaException, IOException {
		String sql = "SELECT * FROM " + TB_CLIENTE + " WHERE `CONTA` = %d";
		String Query = String.format(sql, conta);

		Cliente cliente = null;
		if (DbConnector.open()) {
			try {
				if (DbConnector.open()) {
					ResultSet rs = DbConnector.execute(Query);
					if (rs.next()) {
						cliente = new Cliente(rs.getInt("ID"), rs.getString("CPF"), rs.getString("NOME"),
								rs.getLong("AGENCIA"), rs.getLong("CONTA"), Hash.decode(rs.getString("SENHA")),
								rs.getDouble("SALDO"), rs.getBoolean("TIPO_CONTA"));
					} else {
						throw new ContaNaoEncontradaException();
					}
					rs.close();
				}
			} catch (SQLException e) {
				System.out.println(
						"public cliente findByConta@Exception: " + e.getMessage() + "com código: " + e.getErrorCode());
			} finally {
				DbConnector.close();
			}
		}
		return cliente;

	}

	public Cliente findByName(String nome) throws ContaNaoEncontradaException, IOException {
		String sql = "SELECT * FROM " + TB_CLIENTE + " WHERE `NOME` = \"%s\"";
		String Query = String.format(sql, nome);

		Cliente cliente = null;
		if (DbConnector.open()) {
			try {
				if (DbConnector.open()) {
					ResultSet rs = DbConnector.execute(Query);
					if (rs.next()) {
						cliente = new Cliente(rs.getInt("ID"), rs.getString("CPF"), rs.getString("NOME"),
								rs.getLong("AGENCIA"), rs.getLong("CONTA"), Hash.decode(rs.getString("SENHA")),
								rs.getDouble("SALDO"), rs.getBoolean("TIPO_CONTA"));
					} else {
						throw new ContaNaoEncontradaException();
					}
					rs.close();
				}
			} catch (SQLException e) {
				System.out.println(
						"public cliente findByName@Exception: " + e.getMessage() + "com código: " + e.getErrorCode());
			} finally {
				DbConnector.close();
			}
		}
		return cliente;

	}

	public Cliente login(long conta, long agencia) throws LoginIncorretoException, RuntimeException, IOException {
		String sql = "SELECT * FROM " + TB_CLIENTE + " WHERE `AGENCIA` = %d AND `CONTA` = %d";
		String Query = String.format(sql, agencia, conta);
		Cliente cliente = null;
		if (DbConnector.open()) {
			try {
				ResultSet rs = DbConnector.execute(Query);
				if (rs.next()) {
					cliente = new Cliente(rs.getInt("ID"), rs.getString("CPF"), rs.getString("NOME"),
							rs.getLong("AGENCIA"), rs.getLong("CONTA"), Hash.decode(rs.getString("SENHA")),
							rs.getDouble("SALDO"), rs.getBoolean("TIPO_CONTA"));
				} else {
					throw new LoginIncorretoException();
				}
				rs.close();
			} catch (SQLException e) {
				System.out.println(
						"public Cliente login@Exception: " + e.getMessage() + "com código: " + e.getErrorCode());
			} finally {
				DbConnector.close();
			}
		}
		return cliente;
	}

	public boolean adicionar(Cliente cliente) throws ContaJaCadastradaException, IOException {
		boolean result = false;
		final String sql = "INSERT INTO " + TB_CLIENTE
				+ "(`CPF`, `NOME`, `AGENCIA`, `CONTA`, `SENHA`, `SALDO`, `TIPO_CONTA`)"
				+ "VALUES (\"%s\", \"%s\", %d, %d, \"%s\", %f, %d)";
		String Query = String.format(sql, cliente.getCpf(), cliente.getNome(), cliente.getAgencia(), cliente.getConta(),
				Hash.encode(cliente.getSenha()), cliente.getSaldo(), cliente.isTipoConta() == true ? 1 : 0);
		if (DbConnector.open()) {
			try {
				ResultSet rs = DbConnector.execute(Query);
				result = rs != null;
				rs.close();
				return true;
			} catch (SQLException e) {
				if (e.getErrorCode() == 1062)
					throw new ContaJaCadastradaException();
				System.out.println(
						"public boolean adicionar@Exception: " + e.getMessage() + "com código: " + e.getErrorCode());
				result = false;
			} finally {
				DbConnector.close();
			}
		}
		return result;
	}

	public boolean atualizar(Cliente cliente) {
		boolean result = false;
		final String sql = "UPDATE " + TB_CLIENTE + " SET " + "`CPF` = \"%s\", " + "`NOME` = \"%s\", "
				+ "`AGENCIA` = %d, " + "`CONTA` = %d, " + "`SENHA` = \"%s\", " + "`SALDO` = %f, "
				+ "`TIPO_CONTA` = %d WHERE " + TB_CLIENTE + ".`ID` = %d AND " + TB_CLIENTE + ".`CPF` = \"%s\"";
		String Query = String.format(sql, cliente.getCpf(), cliente.getNome(), cliente.getAgencia(), cliente.getConta(),
				Hash.encode(cliente.getSenha()), cliente.getSaldo(), cliente.isTipoConta() == true ? 1 : 0,
				cliente.getId(), cliente.getCpf());
		if (DbConnector.open()) {
			try {
				ResultSet rs = DbConnector.execute(Query);
				result = true;
				rs.close();
			} catch (SQLException e) {
				System.out.println(
						"public boolean atualizar@Exception: " + e.getMessage() + "com código: " + e.getErrorCode());
				result = false;
			} finally {
				DbConnector.close();
			}
		}
		return result;
	}

	public boolean remover(Cliente cliente) {
		boolean result = false;
		final String sql = "DELETE FROM " + TB_CLIENTE + " WHERE " + "`ID` = %d AND" + "`CPF` = \"%s\" AND"
				+ "`CONTA` = %d AND" + "`AGENCIA` = %d";

		String Query = String.format(sql, cliente.getId(), cliente.getCpf(), cliente.getConta(), cliente.getAgencia());
		if (DbConnector.open()) {
			try {
				ResultSet rs = DbConnector.execute(Query);
				result = true;
				rs.close();
			} catch (SQLException e) {
				System.out.println("public boolean remover@Exception: " + e.getMessage() + "com código: " + e.getErrorCode());
				result = false;
			} finally {
				DbConnector.close();
			}
		}
		return result;
	}

	public List<Cliente> listClients() {
		final String sql = "SELECT * FROM " + TB_CLIENTE;
		List<Cliente> list = new ArrayList<Cliente>();
		if (DbConnector.open()) {
			try {
				ResultSet rs = DbConnector.execute(sql);
				while (rs.next()) {
					list.add(new Cliente(rs.getInt("ID"), rs.getString("CPF"), rs.getString("NOME"),
							rs.getLong("AGENCIA"), rs.getLong("CONTA"), Hash.decode(rs.getString("SENHA")),
							rs.getDouble("SALDO"), rs.getBoolean("TIPO_CONTA")));
				}
				rs.close();
			} catch (SQLException e) {
				System.out.println(
						"public List<Cliente> listClients@Exception: " + e.getMessage() + "com código: " + e.getErrorCode());
			} finally {
				DbConnector.close();
			}
		}
		return list;
	}
}
