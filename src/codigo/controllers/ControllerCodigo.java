package codigo.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.StyleClassedTextArea;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.paint.Paint;
import resources.css.FXMaster;

public class ControllerCodigo implements Initializable {
	
	@FXML
	TabPane tb_console;
	
	@FXML
	SplitPane sp_areaGeral, sp_areaCodigo;
	
	@FXML
	StyleClassedTextArea area_portugol, area_codigo, area_console;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		tabPane();
		splitPane();
		areasStyle();
		
		area_portugol.setOnKeyPressed(e -> {
			area_portugol = ControllerPortugol.setCores(area_portugol);
	    	//Propriedades.setPropriedade("autosave", codigo.getText()/*.replace("\n", "</line>").toString().replace(" ", "</space>")*/);
		});
    
		area_portugol.setOnKeyReleased(e -> {
			area_portugol = ControllerPortugol.setCores(area_portugol);
	    	//Propriedades.setPropriedade("autosave", codigo.getText()/*.replace("\n", "</line>").toString().replace(" ", "</space>")*/);
	    });
	}
	
	private void tabPane() {
    	tb_console.getStylesheets().add(FXMaster.tabPane());
    }
	
	private void splitPane() {
		sp_areaCodigo.getStylesheets().add(FXMaster.splitPane());
		sp_areaGeral.getStylesheets().add(FXMaster.splitPane());
	}
	
	private void areasStyle() {
		area_portugol.setStyle("-fx-font-size: 24; -fx-font-weight: bold;-fx-background-color: #1a1a1a;-fx-border-color: #1a1a1a");
		area_portugol.getStylesheets().add(FXMaster.portugol());
		area_portugol.setParagraphGraphicFactory(LineNumberFactory.get(area_portugol));
		area_portugol.setWrapText(true);
		area_portugol.setLineHighlighterOn(true);
		area_portugol.setLineHighlighterFill(Paint.valueOf("#353535"));
		ControllerPortugol.setCores(area_portugol);
		//go to line -> area.displaceCaret(numeroLinha.length);
	}

}