package codigo.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import codigo.Inicial;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import resources.css.CssMaster;

public class ControllerInicial implements Initializable {
	
	/*----------------PARTE DE CIMA DO FXML--------------*/
	    @FXML
	    VBox vb_topo;
	    
	    @FXML
		AnchorPane ap_barraPrimaria, ap_barraSecundaria;
	    
	  /*----------BARRA FIXA DO FXML-------------------*/
		@FXML
		JFXButton btn_home;
	    
		@FXML
		MenuBar m_bar;
		
	    @FXML
		JFXButton btn_minus, btn_max, btn_close;
	
	  /*-----------------------------------------------*/
		
	  /*----------SEGUNDA BARRA DO FXML----------------*/
		@FXML
		JFXButton btn_novo;
		
	  /*-----------------------------------------------*/
	/*---------------------------------------------------*/
	
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	
    	barra_controle();
    	menuBar();
    	barra_segundo();
    }
   
    //metodos de controle
    
    private void barra_controle() {
    	btn_home.setOnAction(e->{
    		modoInicial();
    	});
    	
    	btn_close.setOnAction(e->{ //fechar aplicacao
    		System.exit(0);
    	});
    	
    	btn_max.setOnAction(e -> { //maximizar aplicacao
    	  int i = Inicial.maximizar();
    	  
    	  if(i==1) { //maximized
    		  FontAwesomeIcon icon = new FontAwesomeIcon();
    		  icon.setFill(Paint.valueOf("#ccc4c4"));
    		  icon.setIcon(de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons.COMPRESS);
    		  btn_max.setGraphic(icon);
    	  }else { //!maximized
    		  FontAwesomeIcon icon = new FontAwesomeIcon();
    		  icon.setFill(Paint.valueOf("#ccc4c4"));
    		  icon.setIcon(de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons.SQUARE_ALT);
    		  btn_max.setGraphic(icon);
    	  }
    	});
    	
    	btn_minus.setOnAction(e -> { //minimizar aplicacao
    		Inicial.minimizar();
    	});
    	
    }

    private void menuBar() {
    	m_bar.getStylesheets().add(CssMaster.menuBar());
    }
    
    private void barra_segundo() {
    	btn_novo.setOnAction(e -> {
    		modoCodigo();
    	});
    }
    
    //metodos de mudanca de interface
    private void modoInicial() {
    	vb_topo.getChildren().clear();
    	vb_topo.getChildren().add(ap_barraPrimaria);
    	vb_topo.getChildren().add(ap_barraSecundaria);
    }
    
    private void modoCodigo() {
    	vb_topo.getChildren().clear();
    	vb_topo.getChildren().add(ap_barraPrimaria);
    }
    
}
