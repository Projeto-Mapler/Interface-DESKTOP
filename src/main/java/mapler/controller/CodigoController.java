package mapler.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.StyleClassedTextArea;

import com.jfoenix.controls.JFXButton;

import conversores.ConversorStrategy;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
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
import mapler.model.MenuItemTraducao;
import mapler.model.resource.Estilos;
import mapler.model.resource.Templates;
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
	SplitPane split_vertical,split_horizontal;

	@FXML
	StyleClassedTextArea area_cod, area_trad;

	@FXML
	ConsoleStyleClassedTextArea area_console;

	@FXML
	BorderPane bd_inicial;

	@FXML
	VBox vb_topo;

	@FXML
	AnchorPane ap_barraPrimaria, ap_barraSecundaria, ap_centerIncial, ap_cod, ap_trad, ap_console;

	@FXML
	MenuBar m_bar;

	@FXML
	Menu mn_exibir, mn_linguagem;

	@FXML // arquivo
	MenuItem mi_novo, mi_abrir, mi_salvar, mi_salvarc, mi_traducao, mi_console;

	@FXML
	JFXButton btn_left_tutoriais, btn_left_sobre, btn_left_news, btn_minus, btn_max,
			btn_close, btn_home;
	
	@FXML 
	JFXButton btn_executar, btn_debug, btn_traduzir;

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

	public CodigoController() throws Exception {

		this.estiloLinguagensService = EstiloLinguagensService.getInstancia();
		this.inicialService = InicioService.getInstancia();
		this.baseService = BaseService.getInstancia();
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
		this.consoleTraducaoService = new ConsoleTraducaoService(area_console, area_trad); // uma instancia por 'aba'
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
			c.setOnAction(e ->{
				setTraducaoVisible(true);
				MenuItemTraducao itemTraducao = (MenuItemTraducao) c;
				this.consoleTraducaoService.setTraducaoTexto(area_cod.getText(), itemTraducao.getConversorStrategy());
				this.estiloLinguagensService.setEstiloTraducao(itemTraducao.getLinguagem(), area_trad);
			});
		});
		
		mi_traducao.setOnAction(e -> {
			setTraducaoVisible(true);
		});

		btn_traduzir.setOnAction(e -> {
			if(btn_traduzir.getText().equals("Esconder")) {
				setTraducaoVisible(false);
				btn_traduzir.setText("Traduzir");
			}else {
				setTraducaoVisible(true);
			}
			
			
		});


		mi_novo.setOnAction(e -> {
			
		});

		mi_salvar.setOnAction(e -> {
			// Arquivo.salvarArquivo(Arquivo.arquivo, true, ControllerCodigo.getPortugol());
		});

		mi_salvarc.setOnAction(e -> {
			// Arquivo.SalvarComo(Arquivo.arquivo, ControllerCodigo.getPortugol());
		});

		btn_executar.setOnAction(e -> {
			if(btn_executar.getText().equals("Parar")) {
				setConsoleVisible(false);
				btn_executar.setText("Executar");
				icon_exec.setGlyphName("PLAY");
			}else {
				setConsoleVisible(true);
				icon_exec.setGlyphName("STOP");
			}
			consoleTraducaoService.executarTexto(this.area_cod.getText().trim());
			
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

		btn_left_tutoriais.setOnMouseEntered(e -> {
			btn_left_tutoriais.setStyle("-fx-background-color: white;");
			btn_left_tutoriais.setTextFill(Paint.valueOf("#272727"));
		});

		btn_left_tutoriais.setOnMouseExited(e -> {
			btn_left_tutoriais.setStyle("");
			btn_left_tutoriais.setTextFill(Paint.valueOf("white"));
		});

		btn_left_sobre.setOnMouseEntered(e -> {
			btn_left_sobre.setStyle("-fx-background-color: white;");
			btn_left_sobre.setTextFill(Paint.valueOf("#272727"));
		});

		btn_left_sobre.setOnMouseExited(e -> {
			btn_left_sobre.setStyle("");
			btn_left_sobre.setTextFill(Paint.valueOf("white"));
		});

		btn_left_news.setOnMouseEntered(e -> {
			btn_left_news.setStyle("-fx-background-color: white;");
			btn_left_news.setTextFill(Paint.valueOf("#272727"));
		});

		btn_left_news.setOnMouseExited(e -> {
			btn_left_news.setStyle("");
			btn_left_news.setTextFill(Paint.valueOf("white"));
		});
		
		//css
		split_vertical.getStylesheets().add(CarregadorRecursos.getResourceExternalForm(Estilos.SPLITPANE.getUrl()));
		split_horizontal.getStylesheets().add(CarregadorRecursos.getResourceExternalForm(Estilos.SPLITPANE.getUrl()));
		m_bar.getStylesheets().add(CarregadorRecursos.getResourceExternalForm(Estilos.MENUBAR.getUrl()));
		tab_areacod.getStylesheets().add(CarregadorRecursos.getResourceExternalForm(Estilos.TABAREACOD.getUrl()));

		area_trad.getStylesheets().add(CarregadorRecursos.getResourceExternalForm(Estilos.TEXTO.getUrl()));
		area_trad.setParagraphGraphicFactory(LineNumberFactory.get(area_trad));
		area_trad.setWrapText(true);
		area_trad.setLineHighlighterOn(false);
		area_trad.setEditable(false);

		area_console.getStylesheets().add(CarregadorRecursos.getResourceExternalForm(Estilos.TEXTO.getUrl()));
		area_console.setWrapText(false);
		area_console.setLineHighlighterOn(false);
		//area_console.appendText("texte");

		area_cod.getStylesheets().add(CarregadorRecursos.getResourceExternalForm(Estilos.TEXTO.getUrl()));
		area_cod.setParagraphGraphicFactory(LineNumberFactory.get(area_cod));
		area_cod.setWrapText(true);
		area_cod.setLineHighlighterOn(true);
		area_cod.setStyleClass(0, 0, "variaveis");

		area_cod.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {

				if (ke.getText().equals(";") || ke.getText().equals(".") || ke.getText().equals("'")
						|| ke.getCode().equals(KeyCode.ENTER) || ke.getCode().equals(KeyCode.BACK_SPACE)
						|| ke.getCode().equals(KeyCode.DELETE) || ke.getCode().equals(KeyCode.SPACE)
						|| ke.getCode().equals(KeyCode.TAB)) {

					int comecoDaLinha = area_cod.getText().lastIndexOf("\n", area_cod.getCaretPosition() - 1);
					int finalDaLinha = area_cod.getText().indexOf("\n", area_cod.getCaretPosition()); // se == -1 � a
																										// ultima
					int comecoDaLinhaAnt = 0;
					int fimTexto = area_cod.getText().length();

					if (finalDaLinha == -1 && comecoDaLinha != -1)
						estiloLinguagensService.setEstiloPortugol(area_cod, comecoDaLinha + 1, fimTexto);
					else if (finalDaLinha == -1 && comecoDaLinha == -1)
						estiloLinguagensService.setEstiloPortugol(area_cod, 0, fimTexto);
					else if (finalDaLinha != -1 && comecoDaLinha == -1)
						estiloLinguagensService.setEstiloPortugol(area_cod, 0, finalDaLinha);
					else
						estiloLinguagensService.setEstiloPortugol(area_cod, comecoDaLinha, finalDaLinha);

					if (comecoDaLinha > 1) {
						comecoDaLinhaAnt = area_cod.getText().lastIndexOf("\n", comecoDaLinha - 1);
						if (comecoDaLinhaAnt == -1)
							estiloLinguagensService.setEstiloPortugol(area_cod, 0, comecoDaLinha);
						else
							estiloLinguagensService.setEstiloPortugol(area_cod, comecoDaLinhaAnt, comecoDaLinha);
					}
				}
			}
		});
	}

	private void setTraducaoVisible(boolean a) {
		split_horizontal.getItems().remove(ap_trad);
		if (a) {
			split_horizontal.getItems().add(ap_trad);
			btn_traduzir.setText("Esconder");
		}
	}
	
	private void setConsoleVisible(boolean a) {
		split_vertical.getItems().remove(ap_console);
		if(a) {
			split_vertical.getItems().add(ap_console);
			btn_executar.setText("Parar");
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
