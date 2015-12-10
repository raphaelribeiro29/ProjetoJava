package br.cc.arswtechsolutions.benabank.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class DatabaseConfigLoader {


	private String hostAddr = null;
	private String userName = null;
	private String userPass = null;
	private String dataBaseName = null;

	public String getDatabaseName(){
		return uniqueInstance.dataBaseName;
	}

	private static DatabaseConfigLoader uniqueInstance;

	public String getHost(){
		return uniqueInstance.hostAddr;
	}

	public String getName(){
		return uniqueInstance.userName;
	}

	public String getPass(){
			return uniqueInstance.userPass;
	}

	private DatabaseConfigLoader(String fileName) throws FileNotFoundException{
		this.getDatabaseConfig(fileName);
	}

	public static synchronized DatabaseConfigLoader getInstance(String fileName) throws FileNotFoundException{
		if (uniqueInstance == null)
			uniqueInstance = new DatabaseConfigLoader(fileName);
		return uniqueInstance;
	}



	protected void getDatabaseConfig(String fileName) throws FileNotFoundException{
		if (fileName.trim().isEmpty() || (new File(fileName).exists() == false)){
			throw new FileNotFoundException();
		}

			Properties config = new Properties();
			FileInputStream inStream = new FileInputStream(fileName);
			try {
				config.load(inStream);
				dataBaseName = config.getProperty("db.conexao.db_name");
				hostAddr = String.format("%s:%s://%s:%s/%s",
						config.getProperty("db.conexao.conector"),
						config.getProperty("db.conexao.driver"),
						config.getProperty("db.conexao.host"),
						config.getProperty("db.conexao.port"),
						dataBaseName);
				userName = String.format("%s", config.getProperty("db.conexao.user"));
				userPass = String.format("%s", config.getProperty("db.conexao.pass"));

				inStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Erro interno. COD: 8890");
			}
	}
}
