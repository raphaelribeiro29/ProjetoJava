package br.cc.arswtechsolutions.benabank.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TableHistoricoData {

	public StringProperty DataProperty() {
		return data;
	}
	public void setDataProperty(StringProperty data) {
		this.data = data;
	}
	public StringProperty FileNameProperty() {
		return fileName;
	}
	public void setFileNameProperty(StringProperty fileName) {
		this.fileName = fileName;
	}


	private StringProperty data;
	private StringProperty fileName;


	public void setData(String data){
		this.data.set(data);
	}

	public void setFileName(String fileName){
		this.fileName.set(fileName);
	}

	public String getData(){
		return this.data.get();
	}

	public String getFileName(){
		return this.fileName.get();
	}
	public TableHistoricoData(String data, String fileName) {
		super();
		this.data = new SimpleStringProperty(data);
		this.fileName = new SimpleStringProperty(fileName);
	}

	public TableHistoricoData(){
		this(null, null);
	}
}
