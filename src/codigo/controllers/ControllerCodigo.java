package codigo.controllers;

import java.io.FileReader;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.StyleClassedTextArea;

import com.jfoenix.controls.JFXButton;

import conversores.ConversorStrategy;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import debug.Debugador;
import debug.GerenciadorEventos;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import resources.bibliotecas.Arquivo;
import resources.bibliotecas.Console;
import resources.css.FXMaster;

public class ControllerCodigo implements Initializable {

    @FXML
    TabPane tb_console;

    @FXML
    AnchorPane ap_console;

    @FXML
    SplitPane sp_areaGeral, sp_areaCodigo;

    @FXML
    StyleClassedTextArea area_portugol, area_codigo;

    @FXML
    Console area_console;

    @FXML
    JFXButton btn_close_console, btn_play, btn_save, btn_debug;

    @FXML
    JFXButton btn_bars, btn_c, btn_java, btn_python, btn_pascal;

    private static StyleClassedTextArea traducao, portugol;
    private static Console console;
    private static SplitPane horizontal, vertical;
    private static AnchorPane pane_console;
    private static JFXButton fechar_console;

    // INTERPRETADOR
    private GerenciadorEventos ge = new GerenciadorEventos();
    private Debugador debugador = new Debugador(ge, false);

    public static int inter = 0;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
	// TODO Auto-generated method stub
	if (Arquivo.abrir) {
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

	// de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons.k

	tabPane();
	splitPane();
	areasStyle();
	controlesCodigo();
	controlesInterface();
	controlesLinguagens();

	traducao = area_codigo;
	setTraducao(
		    "#include <stdio.h>\n\nint main(){\r\n" + "    printf(\"Ola Mundo!\");\r\n" + "    return 0;\r\n"
			    + "}",
		    "C");
	console = area_console;
	portugol = area_portugol;

	console.setPrincipal(ge, debugador);
	// console.executar("C:\\Users\\Kerlyson\\Documents\\GitHub\\interpretadorPtEstruturadoJava\\exemplos\\io.txt");

	// imprimirConsole("Ola mundo!\n");

	horizontal = sp_areaCodigo;
	vertical = sp_areaGeral;
	pane_console = ap_console;
	fechar_console = btn_close_console;
	Interface(inter);
    }

    /*
     * Metodos padroes
     */

    public static void setTraducao(String str, String lgn) {
	traducao.deleteText(0, traducao.getText().length());
	traducao.appendText(str);
	ControllerLinguagens.setLinguagem(lgn, traducao);
    }

    public static String getPortugol() {
	return portugol.getText();
    }

    /*
     * Metodos de Eventos e Estilos
     */
    public static void Interface(int modo) {
	ControllerCodigo.inter = modo;
	if (modo == 0) {// apenas portugol
	    vertical.getItems().clear();
	    vertical.getItems().add(portugol);
	} else if (modo == 1) {// portugol + traducao
	    vertical.getItems().clear();
	    vertical.getItems().add(horizontal);
	    horizontal.getItems().clear();
	    horizontal.getItems().add(portugol);
	    horizontal.getItems().add(traducao);
	} else if (modo == 2) {// portugol + console
	    vertical.getItems().clear();
	    vertical.getItems().add(portugol);
	    vertical.getItems().add(pane_console);
	} else if (modo == 3) {// portugol + traducao + console
	    vertical.getItems().clear();
	    vertical.getItems().add(horizontal);
	    horizontal.getItems().clear();
	    horizontal.getItems().add(portugol);
	    horizontal.getItems().add(traducao);
	    vertical.getItems().add(pane_console);
	}

    }

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

    private void controlesInterface() {
	btn_close_console.setOnAction(e -> {
	    if (ControllerCodigo.inter == 2) {// portugol + console
		Interface(0);// apenas portugol
	    } else if (ControllerCodigo.inter == 3) {// portugol + traducao + console
		Interface(1);// portugol + traducao
	    }
	    ControllerInicial.mni_console.setText("Console");
	});
    }

    private void controlesCodigo() {
	area_portugol.setOnKeyPressed(e -> {
	    area_portugol = ControllerPortugol.setCores(area_portugol);
	    Arquivo.salvar = true;
	    // Propriedades.setPropriedade("autosave", codigo.getText()/*.replace("\n",
	    // "</line>").toString().replace(" ", "</space>")*/);
	});

	area_portugol.setOnKeyReleased(e -> {
	    area_portugol = ControllerPortugol.setCores(area_portugol);
	    Arquivo.salvar = true;
	    // Propriedades.setPropriedade("autosave", codigo.getText()/*.replace("\n",
	    // "</line>").toString().replace(" ", "</space>")*/);
	});

	btn_debug.setOnAction(e -> {

	});

	btn_play.setOnAction(e -> {
	    Interface(2);
	    String caminho = this.getCaminhoArquivo();
	    console.executar(caminho);
	});

	btn_save.setOnAction(e -> {
	    Arquivo.salvarArquivo(Arquivo.arquivo, true, ControllerCodigo.getPortugol());
	});

    }

    private void controlesLinguagens() {
	setIcon(btn_c, "icon_C.png");
	btn_c.setOnAction(e -> {
	    exibirTraducao(ConversorStrategy.C);
	});

	setIcon(btn_java, "icon_JAVA.png");
	btn_java.setOnAction(e -> {
	    exibirTraducao(ConversorStrategy.JAVA);
	});

	setIcon(btn_python, "icon_PYTHON.png");
	btn_python.setOnAction(e -> {
	    exibirTraducao(ConversorStrategy.PYTHON);
	});

	setIcon(btn_pascal, "icon_SWIFT.png");
	btn_pascal.setOnAction(e -> {
	    exibirTraducao(ConversorStrategy.PASCAL);
	});
    }

    private String getCaminhoArquivo() {
	if (Arquivo.arquivo == null) {
	    Arquivo.salvarArquivo(Arquivo.arquivo, true, ControllerCodigo.getPortugol());
	}
	return Arquivo.arquivo.getAbsolutePath();
    }

    private void exibirTraducao(ConversorStrategy tipoConversao) {
	Interface(3);
	String caminho = this.getCaminhoArquivo();
	setTraducao(console.getTraducao(caminho, tipoConversao), tipoConversao.name());
    }

    private void setIcon(JFXButton btn, String img) {
	Image imagem = new Image(
				 ControllerCodigo.class
						       .getClassLoader()
						       .getResource("resources/images/" + img)
						       .toExternalForm());
	ImageView iv = new ImageView(imagem);
	iv.setFitHeight(32);
	iv.setFitWidth(32);
	btn.setGraphic(iv);
    }

}
