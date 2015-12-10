package br.cc.arswtechsolutions.benabank.ui.controllers;

import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.ResourceBundle;

import br.cc.arswtechsolutions.benabank.model.TableHistoricoData;
import br.cc.arswtechsolutions.benabank.screenframework.IControlledScreen;
import br.cc.arswtechsolutions.benabank.screenframework.ScreensController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

public class AdmManagerRelatorioController implements Initializable, IControlledScreen {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TableView<TableHistoricoData> tbViewHistorico;

	@FXML
	private TableColumn<TableHistoricoData, String> tbViewColArquivo;

	@FXML
	private TableColumn<TableHistoricoData, String> tbViewColData;

	private ScreensController myController;
	private ObservableList<TableHistoricoData> data = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		this.tbViewColData.setCellValueFactory(cellData -> cellData.getValue().DataProperty());
		this.tbViewColArquivo.setCellValueFactory(cellData -> cellData.getValue().FileNameProperty());
		File file = new File("./relatorios/");
		if (file.exists()){
		File[] m = file.listFiles();
		for (File f : m) {
			Path path = FileSystems.getDefault().getPath("./relatorios");
			BasicFileAttributes attributes;
			try {
				attributes = Files.readAttributes(path, BasicFileAttributes.class);
				FileTime creationTime = attributes.creationTime();
				data.add(new TableHistoricoData(creationTime.toString(), f.getPath()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			this.tbViewHistorico.getItems().addAll(data);
		}

		this.tbViewHistorico.setRowFactory(tv->{
			TableRow<TableHistoricoData> row = new TableRow<>();
			row.setOnMouseClicked(event ->{
				if (event.getClickCount() == 2 && (!row.isEmpty())){
					TableHistoricoData rowData = row.getItem();
					try {
						Thread t = new Thread(){
							public void run(){
								if (Desktop.isDesktopSupported())
									if (Desktop.getDesktop().isSupported(Action.OPEN)){
										Desktop op = Desktop.getDesktop();
										if (new File(rowData.getFileName()).exists()){
											try {
												op.open(new File(rowData.getFileName()));
											} catch (IOException e) {
												e.printStackTrace();
											}
										}
									}
							}
						};
						t.start();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			return row;
		});
	}

	@Override
	public void setScreenParent(ScreensController screenPage) {
		// TODO Auto-generated method stub
		this.myController = screenPage;
	}
}
