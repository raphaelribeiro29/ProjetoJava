package br.cc.arswtechsolutions.benabank.model.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import br.cc.arswtechsolutions.benabank.model.Cliente;

public class Relatorio {



	final String header =
	"BENA BANK 1.0 - AESW TECHNOLOGY SOLUTUIONS. Inc\n"+
	"Versão: 1.0 alpha" +
	"\nAno: "+Calendar.getInstance().get(Calendar.YEAR)
	+ "\nRelatório: versão 1.0" +
	"\nData: " + Calendar.getInstance().getTime().toString() +
	"\n\n"
	+ "============================================================";
	final String footer = "suporte@benabank.com\n" + "\nAnderson Eraldo\n" + "Rafael Ribeiro\n" + "Swellington Soares\n"
			+ "Welton Matos\n\n"
			+ "============================================================";

	Set<Cliente> hashSet = new HashSet<Cliente>();
	private String filepath;

	public String getFilePath(){
		return this.filepath;
	}

	public void salvarRelatorio() {
		if (hashSet.size() > 0) {
			File testDir = new File("./relatorios");
			if (testDir.exists() == false)
				testDir.mkdir();
			File file = new File("./relatorios/relatorio" + Calendar.getInstance().getTime().toString().replaceAll(" ", "_").replaceAll(":","_") + ".txt");
			try {
				this.filepath = file.getAbsolutePath();
				PrintWriter pw = new PrintWriter(file);
				pw.println(header);
				for (Cliente cliente : hashSet) {
					pw.println(cliente.toString());
				}
				pw.println(footer);
				pw.close();
			} catch ( FileNotFoundException e){
				System.out.println(e.getMessage());
			}

		}

	}

	public void addClients(Cliente cl) {
		hashSet.add(cl);
	}

	public void cleanList() {
		this.hashSet.clear();
	}

}
