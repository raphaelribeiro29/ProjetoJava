package br.cc.arswtechsolutions.benabank.model;

import br.cc.arswtechsolutions.benabank.model.utils.FormatMask;
import br.cc.arswtechsolutions.benabank.model.utils.Hash;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Cliente implements IContaActions {

	private final IntegerProperty Id;
	private final StringProperty cpf;
	private final StringProperty nome;
	private final LongProperty agencia;
	private final LongProperty conta;
	private final StringProperty senha;
	private final DoubleProperty saldo;
	private final BooleanProperty tipoConta; // true = conta corrente, false =
	// conta poupança

	public IntegerProperty idProperty() {
		return Id;
	}

	public StringProperty cpfProperty() {
		return cpf;
	}

	public StringProperty nomeProperty() {
		return nome;
	}

	public LongProperty agenciaProperty() {
		return agencia;
	}

	public LongProperty contaProperty() {
		return conta;
	}

	public StringProperty senhaProperty() {
		return senha;
	}

	public DoubleProperty saldoProperty() {
		return saldo;
	};

	public BooleanProperty tipoContaProperty() {
		return tipoConta;
	}

	public int getId() {
		return Id.get();
	}

	public void setId(int id) {
		if (id > 0)
			Id.set(id);
		else
			Id.set(0);
	}

	public String getCpf() {
		return cpf.get();
	}

	public void setCpf(String cpf) {
		if (cpf.trim().length() == 11) {
			this.cpf.set(cpf);
			return;
		}
		throw new IllegalArgumentException("CPF inválido");
	}

	public String getNome() {
		return nome.get();
	}

	public void setNome(String nome) {
		if (nome.trim().length() > 4) {
			this.nome.set(nome);
			return;
		}
		throw new IllegalArgumentException("Nome tem que ter mais de 4 caracteres");
	}

	public long getAgencia() {
		return agencia.get();
	}

	public void setAgencia(long agencia) {
		if (agencia > 0) {
			this.agencia.set(agencia);
			return;
		}
		throw new IllegalArgumentException("Número da Agência deve ser maior que zero");
	}

	public long getConta() {
		return conta.get();
	}

	public void setConta(long conta) {
		if (conta > 0) {
			this.conta.set(conta);
			return;
		}
		throw new IllegalArgumentException("Número da conta deve ser maior que zero");
	}

	public String getSenha() {
		return senha.get();
	}

	public void setSenha(String senha) {
		if ((senha.trim().length() >= 4) && (senha.trim().length() < 9) && senha.matches("\\d+")) {
			this.senha.set(senha);
			return;
		}
		throw new IllegalArgumentException("Senha deve conter apenas números no limite de 4 à 8 digitos.");
	}

	public double getSaldo() {
		return saldo.get();
	}

	public void setSaldo(double saldo) {
		if (saldo >= 0) {
			this.saldo.set(saldo);
		}
	}

	public boolean isTipoConta() {
		return tipoConta.get();
	}

	public void setTipoConta(boolean tipoConta) {
		this.tipoConta.set(tipoConta);
	}

	public Cliente() {
		this(0, null, null, 0, 0, null, 0.0, true);
	}

	public Cliente(int id, String cpf, String nome, long agencia, long conta, String senha, double saldo,
			boolean tipoConta) {
		super();
		this.Id = new SimpleIntegerProperty(id);
		this.cpf = new SimpleStringProperty(cpf);
		this.nome = new SimpleStringProperty(nome);
		this.agencia = new SimpleLongProperty(agencia);
		this.conta = new SimpleLongProperty(conta);
		this.senha = new SimpleStringProperty(senha);
		this.saldo = new SimpleDoubleProperty(saldo);
		this.tipoConta = new SimpleBooleanProperty(tipoConta);
	}

	@Override
	public void creditar(double valor) {
		// TODO Auto-generated method stub
		if (valor > 0) {
			this.setSaldo(this.getSaldo() + valor);
			return;
		}
		throw new IllegalArgumentException("Valor deve ser maior que zero.");
	}

	@Override
	public double debitar(double valor) {
		// TODO Auto-generated method stub
		if ((valor > 0) && (valor <= this.getSaldo())) {
			this.setSaldo(this.getSaldo() - valor);
			return valor;
		} else
			throw new IllegalArgumentException(
					"Parâmetro incorreto,\n" + "valor fora dos limites ou\n" + "você não tem este valor na sua conta.");
	}

	@Override
	public boolean transferir(Cliente c, double valor) {
		if (c == null && valor <= 0)
			return false;
		c.creditar(this.debitar(valor));
		return true;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String r = "ID: %d%n" + "NOME: %s%n" + "CPF: %s%n" + "AGÊNCIA: %d%n" + "CONTA: %d%n" + "SENHA: %s%n"
				+ "SALDO: %.2f%n" + "TIPO DA CONTA: %s%n%n";

		return (String.format(r, getId(), getNome(), FormatMask.format("###.###.###-##",getCpf()), getAgencia(), getConta(), Hash.encode(getSenha()),
				getSaldo(), isTipoConta() == true ? "Conta corrente" : "Conta poupança"));
	}
}
