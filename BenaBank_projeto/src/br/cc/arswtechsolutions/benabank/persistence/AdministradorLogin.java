package br.cc.arswtechsolutions.benabank.persistence;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import br.cc.arswtechsolutions.benabank.model.Administrador;
import br.cc.arswtechsolutions.benabank.model.exception.LoginIncorretoException;
import br.cc.arswtechsolutions.benabank.model.utils.BuilderPassword;
import br.cc.arswtechsolutions.benabank.model.utils.Hash;

public class AdministradorLogin {

	private String userName;
	private List<String> senha;
	private Administrador adm;

	public boolean login() throws RuntimeException, IOException, LoginIncorretoException, SQLException{
		AdministradorDAO admDao = new AdministradorDAO();
		this.adm = admDao.login(this.userName);
		if (this.adm == null || BuilderPassword.decryptPassword(senha, Hash.decode(adm.getPassword()))==false){
			throw new LoginIncorretoException();
		}
		return true;
	}


	public AdministradorLogin(String login, List<String> senha) {
		this.userName = login;
		this.senha = senha;
	}




}
