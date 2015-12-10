package br.cc.arswtechsolutions.benabank.model;

public class Administrador {

	private long id;
	private String userName;
	private String password;


	public long getId() {
		return id;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public void setId(long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Administrador(long id, String userName, String password) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
	}

	public Administrador(){
		this(0, null, null);
	}

}
