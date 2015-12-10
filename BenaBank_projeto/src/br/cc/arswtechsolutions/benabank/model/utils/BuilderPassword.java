package br.cc.arswtechsolutions.benabank.model.utils;

import java.util.ArrayList;
import java.util.List;

public  class BuilderPassword {

	public static boolean decryptPassword(List<String> senha, String db_pass){

		List<String> dbPassList = new ArrayList<String>();
		if (senha.size() != db_pass.length())
			return false;
		//adiciona cada caractere da db_pass para a lista
		for (int i = 0; i<db_pass.length(); ++i){
			dbPassList.add(String.format("%c",db_pass.charAt(i)));
		}
		//compara as senhas
		for (int i = 0; i<dbPassList.size(); ++i){
			if (senha.get(i).contains(dbPassList.get(i)) == false)
				return false;
		}
		return true;
	}
}
