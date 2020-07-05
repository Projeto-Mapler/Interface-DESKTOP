package codigo.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import codigo.Inicial;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import resources.bibliotecas.Arquivo;
import resources.css.FXMaster;

public class ControllerInicial implements Initializable {

    @FXML
    BorderPane bd_inicial;

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
    MenuItem mi_novo, mi_abrir, mi_salvar, mi_salvarc;

    @FXML
    JFXButton btn_minus, btn_max, btn_close;

    /*-----------------------------------------------*/

    /*----------SEGUNDA BARRA DO FXML----------------*/
    @FXML
    JFXButton btn_novo;

    /*-----------------------------------------------*/

    /*---------------------------------------------------*/

    /*----------------Center----------------------------*/
    @FXML
    AnchorPane ap_centerIncial;
    /*--------------------------------------------------*/

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	modoInicial();// iniciar na tela principal

    	barra_controle();
    	menuBar();
    	botoesMenu();
    	barra_segundo();
    	
    }

    // metodos de controle
    private void botoesMenu() {
    	mi_abrir.setOnAction(e->{
    		abrirArquivo();
    	});
    	
    	mi_novo.setOnAction(e->{
    		modoInicial();
    		modoCodigo();
    	});
    	
    	mi_salvar.setOnAction(e->{
    		
    	});
    	
    	mi_salvarc.setOnAction(e->{
    		
    	});
    }
    
    private void barra_controle() {
    	btn_home.setOnAction(e -> {
    		modoInicial();
    	});

    	btn_close.setOnAction(e -> { // fechar aplicacao
    		System.exit(0);
    	});

    	btn_max.setOnAction(e -> { // maximizar aplicacao
    		int i = Inicial.maximizar();

    		if (i == 1) { // maximized
    			FontAwesomeIcon icon = new FontAwesomeIcon();
    			icon.setFill(Paint.valueOf("#ccc4c4"));
    			icon.setIcon(de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons.COMPRESS);
    			btn_max.setGraphic(icon);
    		} else { // !maximized
    			FontAwesomeIcon icon = new FontAwesomeIcon();
    			icon.setFill(Paint.valueOf("#ccc4c4"));
    			icon.setIcon(de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons.SQUARE_ALT);
    			btn_max.setGraphic(icon);
    		}
    	});

	btn_minus.setOnAction(e -> { // minimizar aplicacao
	    Inicial.minimizar();
	});

    }

    private void menuBar() {
	m_bar.getStylesheets().add(FXMaster.menuBar());
    }

    private void barra_segundo() {
	btn_novo.setOnAction(e -> {
	    modoCodigo();
	});
    }
    
    //metodos de arquivos
    private void abrirArquivo() {
    	File f = Arquivo.openJanelaArquivo();
    	if(f != null) {
    		modoInicial();
    		modoCodigo();
    		Arquivo.abrir = true;
    	}
    }
    
    // metodos de mudanca de interface
    private void modoInicial() {
	vb_topo.getChildren().clear();
	vb_topo.getChildren().add(ap_barraPrimaria);
	vb_topo.getChildren().add(ap_barraSecundaria);

	try {
	    AnchorPane ap_codigo = FXMLLoader.load(getClass().getResource("/resources/view/tela_principal.fxml"));
	    setCenter(ap_codigo);

	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

    private void modoCodigo() {
	vb_topo.getChildren().clear();
	vb_topo.getChildren().add(ap_barraPrimaria);

	try {
	    AnchorPane ap_codigo = FXMLLoader.load(getClass().getResource("/resources/view/tela_codigo.fxml"));
	    setCenter(ap_codigo);

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    private void setCenter(AnchorPane ap) {
	ap_centerIncial.getChildren().clear();
	ap_centerIncial.getChildren().add(ap);
	ap_centerIncial.setBottomAnchor(ap, 0.0);
	ap_centerIncial.setLeftAnchor(ap, 0.0);
	ap_centerIncial.setTopAnchor(ap, 0.0);
	ap_centerIncial.setRightAnchor(ap, 0.0);
    }

}
