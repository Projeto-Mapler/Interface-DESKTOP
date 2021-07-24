package mapler.controller;

import java.awt.Desktop;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.StyleClassedTextArea;

import com.jfoenix.controls.JFXButton;import com.sun.prism.CompositeMode;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import mapler.model.ConsoleStyleClassedTextArea;
import mapler.model.Linguagem;
import mapler.model.MenuItemTraducao;
import mapler.model.resource.Estilos;
import mapler.model.resource.Templates;
import mapler.service.ArquivoService;
import mapler.service.BaseService;
import mapler.service.ConsoleTraducaoService;
//import mapler.service.ConsoleTraducaoService;
import mapler.service.EstiloLinguagensService;
import mapler.service.InicioService;
import mapler.service.TabService;
import mapler.util.CarregadorRecursos;

/**
 * Controller para tela_codigo.fxml
 *
 */
public class CodigoController implements Initializable {

	@FXML
	TabPane tab_areacod;

	@FXML
	Tab tab_cod, tab_terminal;

	@FXML
	SplitPane split_vertical, split_horizontal;

	@FXML
	StyleClassedTextArea area_cod, area_trad;

	@FXML
	ConsoleStyleClassedTextArea area_console;

	@FXML
	BorderPane bd_inicial;

	@FXML
	VBox vb_topo;

	@FXML
	AnchorPane ap_barraPrimaria, ap_barraSecundaria, ap_centerIncial, ap_cod, ap_trad, ap_console, ap_debug;

	@FXML
	MenuBar m_bar;

	@FXML
	Menu mn_exibir, mn_linguagem;

	@FXML // arquivo
	MenuItem mi_novo, mi_abrir, mi_salvar, mi_salvarc, mi_traducao, mi_console;

	@FXML
	JFXButton btn_left_inicio ,btn_left_tutoriais, btn_left_sobre, btn_left_news, btn_minus, btn_max, btn_close, btn_home;

	@FXML
	JFXButton btn_executar, btn_debug, btn_traduzir, btn_close_trad;

	@FXML
	FontAwesomeIcon icon_exec;

	/*
	 * private File arquivo; // referencia do arquivo que esta sendo manipulado
	 * private boolean arquivoComAlteracoesNaoSalvas = false;// TODO: implementar
	 * detecção de mudancas feitas e não salvas public CodigoController(File
	 * arquivo) { this.arquivo = arquivo; }
	 */

	private EstiloLinguagensService estiloLinguagensService;
	private InicioService inicialService;
	private BaseService baseService;
	private int idx = 2;
	private ConsoleTraducaoService consoleTraducaoService;
	private DebugController debugController;
	private ArquivoService arquivoService;

	public CodigoController() throws Exception {
		this.debugController = new DebugController();
		this.estiloLinguagensService = EstiloLinguagensService.getInstancia();
		this.inicialService = InicioService.getInstancia();
		this.baseService = BaseService.getInstancia();
		this.arquivoService = ArquivoService.getInstance();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		/*
		 * if (arquivo != null) { // abri um arquivo try { Scanner scanner = new
		 * Scanner(new FileReader(arquivo)); scanner.useDelimiter("\n"); String str =
		 * ""; while (scanner.hasNext()) str = str + scanner.next() + "\n";
		 * area_cod.deleteText(0, area_cod.getText().length());
		 * area_cod.appendText(str); } catch (Exception es) { es.printStackTrace(); }
		 * 
		 * }
		 */
		setStyle();
		setTraducaoVisible(false);
		setConsoleVisible(false);
		setDebugVisible(false);
		try {
			FXMLLoader loader = new FXMLLoader(CarregadorRecursos.getResource(Templates.DEBUG.getUrl()));
			loader.setController(debugController);
			ap_debug.getChildren().add(loader.load());
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.consoleTraducaoService = new ConsoleTraducaoService(debugController, area_console, area_trad); 
		String conteudo = this.arquivoService.getConteudo();
		if(conteudo != null) area_cod.appendText(conteudo);
		area_cod.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				
				if(!arquivoService.isArquivoAlterado()) arquivoService.setArquivoAlterado();
				
			}
		});
	}
	
	public static boolean openWebpage(URI uri) {
		Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
		if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
			try {
				desktop.browse(uri);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	private TabService createTab(String titulo, String texto) {
		TabService nova = new TabService(titulo, texto);
		nova.setText(titulo);
		return nova;
	}

	private boolean salvar() {
		return true;
	}

	private void setStyle() {

		mn_linguagem.getItems().forEach(c -> {
			c.setOnAction(e -> {
				setTraducaoVisible(true);
				MenuItemTraducao itemTraducao = (MenuItemTraducao) c;
				this.consoleTraducaoService.setTraducaoTexto(area_cod.getText(), itemTraducao.getConversorStrategy());
				this.estiloLinguagensService.setHighlighterLinguagem(area_trad, itemTraducao.getLinguagem());
			});
		});

		mi_traducao.setOnAction(e -> {
			setTraducaoVisible(true);
		});

		btn_traduzir.setOnAction(e -> {
			if (btn_traduzir.getText().equals("Esconder")) {
				setTraducaoVisible(false);
			} else {
				setTraducaoVisible(true);
			}

		});
		
		btn_debug.setOnAction(e -> {
			boolean visivel = split_horizontal.getItems().contains(ap_debug);
			if(visivel) {
				consoleTraducaoService.pararDebug();
			} else {
				consoleTraducaoService.executarTexto(this.area_cod.getText().trim(), true);				
			}
			setDebugVisible(!visivel);
		});

		mi_novo.setOnAction(e -> {
			if(arquivoService.checkAlteracoesNaoSalvas()) {
				arquivoService.fechar();
				area_cod.clear();
			}
		});
		
		mi_abrir.setOnAction(e -> {
			boolean abriu = this.arquivoService.abrir();
			if(abriu) {
				String conteudo = this.arquivoService.getConteudo();
				area_cod.clear();
				area_cod.appendText(conteudo);
			}
		});

		mi_salvar.setOnAction(e -> {
			this.arquivoService.salvar(area_cod.getText());
		});

		mi_salvarc.setOnAction(e -> {
			this.arquivoService.salvarComo(area_cod.getText());
		});

		btn_executar.setOnAction(e -> {
			if (btn_executar.getText().equals("Parar")) {
				setConsoleVisible(false);
				btn_executar.setText("Executar");
				icon_exec.setGlyphName("PLAY");
			} else {
				setConsoleVisible(true);
				icon_exec.setGlyphName("STOP");
			}
			consoleTraducaoService.executarTexto(this.area_cod.getText().trim(), false);

		});
		
		btn_close_trad.setOnAction(e -> {
			this.setTraducaoVisible(false);
		});

		btn_home.setOnAction(e -> {
			try {
				this.baseService.carregaTela(Templates.INICIO.getUrl());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		btn_home.getStylesheets().add(CarregadorRecursos.getResourceExternalForm(Estilos.BOTOES.getUrl()));

		btn_close.setOnMouseEntered(e -> {
			btn_close.setStyle("-fx-background-color: #1b1b1b;");
		});

		btn_close.setOnMouseExited(e -> {
			btn_close.setStyle("");
		});

		btn_close.setOnAction(e -> { // fechar aplicacao
			/*
			 * if(Arquivo.salvar) { int alerta =
			 * Alertas.showConfirm("Deseja salvar o projeto?"); if(alerta == 0) {
			 * System.exit(0); }else if(alerta == 1) { if(Arquivo.arquivo == null) { boolean
			 * salvar = Arquivo.SalvarComo(Arquivo.arquivo, ControllerCodigo.getPortugol());
			 * if(salvar) System.exit(0); }else { if(Arquivo.salvarArquivo(Arquivo.arquivo,
			 * true, ControllerCodigo.getPortugol())) System.exit(0); } }
			 * 
			 * }else { System.exit(0); }
			 */
			System.exit(0);
		});

		btn_max.setOnAction(e -> { // maximizar aplicacao
			int i = this.inicialService.maximizar();

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

		btn_max.setOnMouseEntered(e -> {
			btn_max.setStyle("-fx-background-color: #1b1b1b;");
		});

		btn_max.setOnMouseExited(e -> {
			btn_max.setStyle("");
		});

		btn_minus.setOnAction(e -> { // minimizar aplicacao
			this.inicialService.minimizar();
		});

		btn_minus.setOnMouseEntered(e -> {
			btn_minus.setStyle("-fx-background-color: #1b1b1b;");
		});

		btn_minus.setOnMouseExited(e -> {
			btn_minus.setStyle("");
		});

		btn_executar.setOnMouseEntered(e -> {
			btn_executar.setStyle("-fx-background-color: white;");
			btn_executar.setTextFill(Paint.valueOf("#272727"));
		});

		btn_executar.setOnMouseExited(e -> {
			btn_executar.setStyle("");
			btn_executar.setTextFill(Paint.valueOf("white"));
		});

		btn_debug.setOnMouseEntered(e -> {
			btn_debug.setStyle("-fx-background-color: white;");
			btn_debug.setTextFill(Paint.valueOf("#272727"));
		});

		btn_debug.setOnMouseExited(e -> {
			btn_debug.setStyle("");
			btn_debug.setTextFill(Paint.valueOf("white"));
		});

		btn_traduzir.setOnMouseEntered(e -> {
			btn_traduzir.setStyle("-fx-background-color: white;");
			btn_traduzir.setTextFill(Paint.valueOf("#272727"));
		});

		btn_traduzir.setOnMouseExited(e -> {
			btn_traduzir.setStyle("");
			btn_traduzir.setTextFill(Paint.valueOf("white"));
		});
		
//		btn_left_inicio.setOnAction(e -> {
//			try {
//				this.baseService.carregaTela(Templates.INICIO.getUrl());
//			} catch (Exception e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//		});
		btn_left_tutoriais.setOnAction(e -> {
			try {
				URL url = new URL("https://portugol.sourceforge.io/");
				boolean boo = openWebpage(url.toURI());
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		btn_left_tutoriais.setOnMouseEntered(e -> {
			btn_left_tutoriais.setStyle("-fx-background-color: white;");
			btn_left_tutoriais.setTextFill(Paint.valueOf("#272727"));
		});

		btn_left_tutoriais.setOnMouseExited(e -> {
			btn_left_tutoriais.setStyle("");
			btn_left_tutoriais.setTextFill(Paint.valueOf("white"));
		});
		
		btn_left_sobre.setOnAction(e -> {
			try {
				URL url = new URL("https://portugol.sourceforge.io/sobre.html");
				boolean boo = openWebpage(url.toURI());
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		btn_left_sobre.setOnMouseEntered(e -> {
			btn_left_sobre.setStyle("-fx-background-color: white;");
			btn_left_sobre.setTextFill(Paint.valueOf("#272727"));
		});

		btn_left_sobre.setOnMouseExited(e -> {
			btn_left_sobre.setStyle("");
			btn_left_sobre.setTextFill(Paint.valueOf("white"));
		});
		
		btn_left_news.setOnAction(e -> {
			try {
				URL url = new URL("https://portugol.sourceforge.io/");
				boolean boo = openWebpage(url.toURI());
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		btn_left_news.setOnMouseEntered(e -> {
			btn_left_news.setStyle("-fx-background-color: white;");
			btn_left_news.setTextFill(Paint.valueOf("#272727"));
		});

		btn_left_news.setOnMouseExited(e -> {
			btn_left_news.setStyle("");
			btn_left_news.setTextFill(Paint.valueOf("white"));
		});

		// css
		split_vertical.getStylesheets().add(CarregadorRecursos.getResourceExternalForm(Estilos.SPLITPANE.getUrl()));
		split_horizontal.getStylesheets().add(CarregadorRecursos.getResourceExternalForm(Estilos.SPLITPANE.getUrl()));
		m_bar.getStylesheets().add(CarregadorRecursos.getResourceExternalForm(Estilos.MENUBAR.getUrl()));
		tab_areacod.getStylesheets().add(CarregadorRecursos.getResourceExternalForm(Estilos.TABAREACOD.getUrl()));

		area_trad.getStylesheets().add(CarregadorRecursos.getResourceExternalForm("css/syntax-highlighter.css"));
		area_trad.setParagraphGraphicFactory(LineNumberFactory.get(area_trad));
		area_trad.setWrapText(true);
		area_trad.setLineHighlighterOn(false);
		area_trad.setEditable(false);

		area_console.getStylesheets().add(CarregadorRecursos.getResourceExternalForm(Estilos.TEXTO.getUrl()));
		area_console.setWrapText(false);
		area_console.setLineHighlighterOn(false);
		// area_console.appendText("texte");

		area_cod.getStylesheets().add(CarregadorRecursos.getResourceExternalForm("css/syntax-highlighter.css"));
		area_cod.setParagraphGraphicFactory(LineNumberFactory.get(area_cod));
		area_cod.setWrapText(true);
		area_cod.setLineHighlighterOn(true);
		this.estiloLinguagensService.setHighlighterLinguagem(area_cod, Linguagem.PORTUGOL);

	}

	private void setTraducaoVisible(boolean a) {
		split_horizontal.getItems().remove(ap_trad);
		btn_traduzir.setText("Traduzir");
		if (a) {
			split_horizontal.getItems().add(ap_trad);
			btn_traduzir.setText("Esconder");
		}
	}

	private void setConsoleVisible(boolean a) {
		split_vertical.getItems().remove(ap_console);
		if (a) {
			split_vertical.getItems().add(ap_console);
			btn_executar.setText("Parar");
		}
	}

	private void setDebugVisible(boolean a) {
		btn_executar.setDisable(a);

		split_horizontal.getItems().remove(ap_debug);
		if (a) {
			this.setConsoleVisible(true);
			split_horizontal.getItems().add(ap_debug);
		}

	}

	/*
	 * 
	 * public boolean salvarArquivo(boolean isSalvarComo) { String txt =
	 * this.getTextoPortugol(); if (txt.isEmpty()) { txt = new String(""); } if
	 * (isSalvarComo) { return GerenciadorArquivo.salvarArquivoComo(arquivo, txt); }
	 * else { return GerenciadorArquivo.salvarArquivo(arquivo, true, txt); } }
	 * 
	 * private String getCaminhoArquivo() { if (arquivo == null) {
	 * this.salvarArquivo(false); } return arquivo.getAbsolutePath(); }
	 * 
	 * public boolean isArquivoComAlteracoesNaoSalvas() { return
	 * arquivoComAlteracoesNaoSalvas; }
	 * 
	 * public void setArquivoComAlteracoesNaoSalvas(boolean
	 * arquivoComAlteracoesNaoSalvas) { this.arquivoComAlteracoesNaoSalvas =
	 * arquivoComAlteracoesNaoSalvas; }
	 */

}
