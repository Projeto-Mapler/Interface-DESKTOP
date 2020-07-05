package codigo.controllers;

import java.io.FileReader;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.StyleClassedTextArea;

import debug.Debugador;
import debug.GerenciadorEventos;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.paint.Paint;
import resources.bibliotecas.Arquivo;
import resources.bibliotecas.Console;
import resources.css.FXMaster;

public class ControllerCodigo implements Initializable {

    @FXML
    TabPane tb_console;

    @FXML
    SplitPane sp_areaGeral, sp_areaCodigo;

    @FXML
    StyleClassedTextArea area_portugol, area_codigo;

    @FXML
    Console area_console;

    private static StyleClassedTextArea traducao;
    private static Console console;
    
    // INTERPRETADOR
    private GerenciadorEventos ge = new GerenciadorEventos();
    private Debugador debugador = new Debugador(ge, false);

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
	// TODO Auto-generated method stub
    	if(Arquivo.abrir) {
    		Arquivo.abrir = false;
    		try {
				Scanner scanner = new Scanner(new FileReader(Arquivo.arquivo.getPath().toString()));
				scanner.useDelimiter("\n");
				String str = "";
    			while (scanner.hasNext())
    				str = str + scanner.next() + "\n";
				area_portugol.deleteText(0, area_portugol.getText().length());
				area_portugol.appendText(str);
			} catch (Exception es) {
				es.printStackTrace();
			}
    		
    	}
    		
    	tabPane();
    	splitPane();
    	areasStyle();
    	controlesCodigo();
    	traducao = area_codigo;
    	setTraducao(
		    "#include <stdio.h>\n\nint main(){\r\n" + "    printf(\"Ola Mundo!\");\r\n" + "    return 0;\r\n"
			    + "}",
		    "C");
    	console = area_console;
	
	
    	console.setPrincipal(ge, debugador);
    	//console.executar("C:\\Users\\Kerlyson\\Documents\\GitHub\\interpretadorPtEstruturadoJava\\exemplos\\io.txt");

    	//	imprimirConsole("Ola mundo!\n");
    }

    /*
     * Metodos padroes
     */

    public static void setTraducao(String str, String lgn) {
    	traducao.deleteText(0, traducao.getText().length());
    	traducao.appendText(str);
    	ControllerLinguagens.setLinguagem(lgn, traducao);
    }

    

    /*
     * Metodos de Eventos e Estilos
     */

    private void tabPane() {
	tb_console.getStylesheets().add(FXMaster.tabPane());
    }

    private void splitPane() {
	sp_areaCodigo.getStylesheets().add(FXMaster.splitPane());
	sp_areaGeral.getStylesheets().add(FXMaster.splitPane());
    }

    private void areasStyle() {
	area_codigo
		   .setStyle(
			     "-fx-font-size: 24; -fx-font-weight: bold;-fx-background-color: #1a1a1a;-fx-border-color: #1a1a1a");
	area_codigo.getStylesheets().add(FXMaster.codigo());
	area_codigo.setParagraphGraphicFactory(LineNumberFactory.get(area_codigo));
	area_codigo.setWrapText(true);
	area_codigo.setLineHighlighterOn(true);
	area_codigo.setLineHighlighterFill(Paint.valueOf("#353535"));
	area_codigo.setEditable(false);
	// area_codigo = ControllerLinguagens.setLinguagem("C", area_codigo);
	// go to line -> area.displaceCaret(numeroLinha.length);

	area_console
		    .setStyle(
			      "-fx-font-size: 20; -fx-font-weight: bold;-fx-background-color: #1a1a1a;-fx-border-color: #1a1a1a");
	area_console.getStylesheets().add(FXMaster.codigo());
	area_console.setWrapText(false);
	area_console.setLineHighlighterOn(false);

	area_portugol
		     .setStyle(
			       "-fx-font-size: 24; -fx-font-weight: bold;-fx-background-color: #1a1a1a;-fx-border-color: #1a1a1a");
	area_portugol.getStylesheets().add(FXMaster.codigo());
	area_portugol.setParagraphGraphicFactory(LineNumberFactory.get(area_portugol));
	area_portugol.setWrapText(true);
	area_portugol.setLineHighlighterOn(true);
	area_portugol.setLineHighlighterFill(Paint.valueOf("#353535"));

	ControllerPortugol.setCores(area_portugol);

    }

    private void controlesCodigo() {
	area_portugol.setOnKeyPressed(e -> {
	    area_portugol = ControllerPortugol.setCores(area_portugol);
	    // Propriedades.setPropriedade("autosave", codigo.getText()/*.replace("\n",
	    // "</line>").toString().replace(" ", "</space>")*/);
	});

	area_portugol.setOnKeyReleased(e -> {
	    area_portugol = ControllerPortugol.setCores(area_portugol);
	    // Propriedades.setPropriedade("autosave", codigo.getText()/*.replace("\n",
	    // "</line>").toString().replace(" ", "</space>")*/);
	});
    }

}
