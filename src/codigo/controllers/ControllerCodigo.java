package codigo.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import resources.css.CssMaster;

public class ControllerCodigo implements Initializable {
	
	@FXML
	TabPane tb_console;
	
	@FXML
	SplitPane sp_areaGeral, sp_areaCodigo;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		tabPane();
		splitPane();
	}
	
	private void tabPane() {
    	tb_console.getStylesheets().add(CssMaster.tabPane());
    }
	
	private void splitPane() {
		sp_areaCodigo.getStylesheets().add(CssMaster.splitPane());
		sp_areaGeral.getStylesheets().add(CssMaster.splitPane());
	}

}
