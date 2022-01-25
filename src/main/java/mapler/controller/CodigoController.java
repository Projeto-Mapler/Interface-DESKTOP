package mapler.controller;

import java.awt.Desktop;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.spi.FileSystemProvider;
import java.util.Collections;
import java.util.ResourceBundle;

import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.StyleClassedTextArea;
import org.fxmisc.wellbehaved.event.InputMap;
import org.fxmisc.wellbehaved.event.Nodes;
import static org.fxmisc.wellbehaved.event.EventPattern.keyPressed;
import static org.fxmisc.wellbehaved.event.InputMap.consume;
import static org.fxmisc.wellbehaved.event.InputMap.sequence;
import static javafx.scene.input.KeyCode.*;


import com.jfoenix.controls.JFXButton;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Spinner;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.scene.control.Slider;
import mapler.model.ConsoleStyleClassedTextArea;
import mapler.model.Linguagem;
import mapler.model.MenuItemTraducao;
import mapler.model.ResizeListener;
import mapler.model.Terminavel;
import mapler.model.highlight.SyntaxHighlighter;
import mapler.model.resource.Estilos;
import mapler.model.resource.Tema;
import mapler.model.resource.Templates;
import mapler.service.ArquivoService;
import mapler.service.BaseService;
import mapler.service.ConfigService;
import mapler.service.ConsoleTraducaoService;
// import mapler.service.ConsoleTraducaoService;
import mapler.service.EstiloLinguagensService;
import mapler.service.InicioService;
import mapler.service.TabService;
import mapler.util.CarregadorRecursos;

/**
 * Controller para tela_codigo.fxml
 *
 */
public class CodigoController implements Initializable, Terminavel {


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
  AnchorPane ap_barraPrimaria, ap_barraSecundaria, ap_centerIncial, ap_cod, ap_trad, ap_console,
      ap_debug, area_lateral;

  @FXML
  MenuBar m_bar;

  @FXML
  Menu mn_linguagem;

  @FXML // arquivo
  MenuItem mi_novo, mi_abrir, mi_salvar, mi_salvarc, mi_console, mi_site, mi_software;

  @FXML
  MenuItem mi_ex_estrutura, mi_ex_io, mi_ex_mdl, mi_ex_tp_v, mi_ex_tp_l, mi_ex_tp_b, mi_ex_tp_n,
      mi_ex_tp_g, mi_ex_op_g, mi_ex_op_a, mi_ex_op_s, mi_ex_op_m, mi_ex_op_d, mi_ex_op_p,
      mi_ex_co_se, mi_ex_co_sn, mi_ex_lc_r, mi_ex_lc_p, mi_ex_lc_e, mi_ex_lc_g, mi_cf_dark, mi_cf_light, mi_cf_pb;
  
  @FXML
  Spinner<Integer> mn_spinner;

  @FXML
  JFXButton btn_left_inicio, btn_left_tutoriais, btn_left_sobre, btn_left_news, btn_minus, btn_max,
      btn_close, btn_home;

  @FXML
  JFXButton btn_executar, btn_debug, btn_traduzir, btn_close_trad;

  @FXML
  FontAwesomeIcon icon_exec, icon_debug, icon_code;

  /*
   * private File arquivo; // referencia do arquivo que esta sendo manipulado private boolean
   * arquivoComAlteracoesNaoSalvas = false;// TODO: implementar detecção de mudancas feitas e não
   * salvas public CodigoController(File arquivo) { this.arquivo = arquivo; }
   */

  private EstiloLinguagensService estiloLinguagensService;
  private InicioService inicialService;
  private BaseService baseService;
  private int idx = 2;
  private ConsoleTraducaoService consoleTraducaoService;
  private DebugController debugController;
  private ArquivoService arquivoService;
  private SyntaxHighlighter codAreaHighlighter, tradAreaHighlighter;
  private ResizeListener resize;

  public CodigoController() throws Exception {
    this.debugController = new DebugController();
    this.estiloLinguagensService = EstiloLinguagensService.getInstancia();
    this.inicialService = InicioService.getInstancia();
    this.baseService = BaseService.getInstancia();
    this.arquivoService = ArquivoService.getInstance();
    this.resize = ResizeListener.getInstancia();
  }

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    /*
     * if (arquivo != null) { // abri um arquivo try { Scanner scanner = new Scanner(new
     * FileReader(arquivo)); scanner.useDelimiter("\n"); String str = ""; while (scanner.hasNext())
     * str = str + scanner.next() + "\n"; area_cod.deleteText(0, area_cod.getText().length());
     * area_cod.appendText(str); } catch (Exception es) { es.printStackTrace(); }
     * 
     * }
     */
    setStyle();
    setEventos();
    setTamanhoFonte(Integer.parseInt(ConfigService.get().getTamanhoFonte()));
    setTraducaoVisible(false);
    setConsoleVisible(false);
    setDebugVisible(false);
    try {
      FXMLLoader loader =
          new FXMLLoader(CarregadorRecursos.get().getResource(Templates.DEBUG.getUrl()));
      loader.setController(debugController);
      ap_debug.getChildren().add(loader.load());
    } catch (IOException e) {
      e.printStackTrace();
    }
    this.debugController.setArea_cod(area_cod);
    this.consoleTraducaoService =
        new ConsoleTraducaoService(debugController, area_console, area_trad);
    String conteudo = this.arquivoService.getConteudo();
    if (conteudo != null) {
      area_cod.clear();
      area_cod.appendText(conteudo);
    }

    area_cod.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue,
          String newValue) {

        if (!arquivoService.isArquivoAlterado())
          arquivoService.setArquivoAlterado();

      }
    });


  }
  
  private void setTamanhoFonte(int tamanho) {
	  if(tamanho <= 2) {
		  tamanho = 2;
	  }
	  area_cod.setStyle("-fx-font-size: "+tamanho+";");
	  area_trad.setStyle("-fx-font-size: "+tamanho+";");
	  area_console.setStyle("-fx-font-size: "+tamanho+";");
	  ConfigService.get().setTamanhoFonte(tamanho+"");
	  return;
	  
  }
  
  private void setEventos() {
	  
	 InputMap<Event> salvar = InputMap.consume(
              keyPressed(S, KeyCodeCombination.CONTROL_DOWN, KeyCodeCombination.SHORTCUT_ANY), e -> {
            	  this.arquivoService.salvar(area_cod.getText());
              }
              
      );
	 
	 InputMap<Event> salvarComo = InputMap.consume(
             keyPressed(S, KeyCodeCombination.CONTROL_DOWN, KeyCodeCombination.SHIFT_DOWN, KeyCodeCombination.SHORTCUT_ANY), e -> {
            	 this.arquivoService.salvarComo(area_cod.getText());
             }
             
     );
	 
	 InputMap<Event> aumentarFonte = InputMap.consume(
             keyPressed(KeyCode.EQUALS, KeyCodeCombination.CONTROL_DOWN, KeyCodeCombination.SHIFT_DOWN, KeyCodeCombination.SHORTCUT_ANY), e -> {
            	 String tamanho = ConfigService.get().getTamanhoFonte();
            	 setTamanhoFonte(Integer.parseInt(tamanho)+1);
             }
             
     );
	 InputMap<Event> diminuirFonte = InputMap.consume(
             keyPressed(KeyCode.MINUS, KeyCodeCombination.CONTROL_DOWN, KeyCodeCombination.SHORTCUT_ANY), e -> {
            	 String tamanho = ConfigService.get().getTamanhoFonte();
            	 setTamanhoFonte(Integer.parseInt(tamanho)-1);
             }
             
     );
	 
	 Nodes.addInputMap(area_cod, salvar);
	 Nodes.addInputMap(area_cod, salvarComo);
	 Nodes.addInputMap(area_cod, aumentarFonte);
	 Nodes.addInputMap(area_cod, diminuirFonte);
	  
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

  private void CarregaExemplo(URL url) {
		String content = null;
	    try {
	    	URI uri = url.toURI();

	    	if("jar".equals(uri.getScheme())){
	    	    for (FileSystemProvider provider: FileSystemProvider.installedProviders()) {
	    	        if (provider.getScheme().equalsIgnoreCase("jar")) {
	    	            try {
	    	                provider.getFileSystem(uri);
	    	            } catch (FileSystemNotFoundException e) {
	    	                // in this case we need to initialize it first:
	    	                provider.newFileSystem(uri, Collections.emptyMap());
	    	            }
	    	        }
	    	    }
	    	}
	    	Path source = Paths.get(uri);
	      content = Files.readString(source);
	      area_trad.clear();
	      
	      if (tradAreaHighlighter != null)
	          tradAreaHighlighter.stop();

	        this.tradAreaHighlighter =
	            estiloLinguagensService.setHighlighterLinguagem(area_trad, Linguagem.PORTUGOL);
	      
		  area_trad.appendText(content);
		  split_horizontal.getItems().remove(ap_trad);
		  split_horizontal.getItems().add(ap_trad);
	    } catch (IOException exc) {
	      // TODO Auto-generated catch block
	      exc.printStackTrace();
	    } catch (URISyntaxException exc) {
	      // TODO Auto-generated catch block
	      exc.printStackTrace();
	    }
		
	}

  private boolean salvar() {
    return true;
  }
  

  private void setStyle() {

    mn_linguagem.getItems().forEach(c -> {
      c.setOnAction(e -> {
        setTraducaoVisible(true);
        MenuItemTraducao itemTraducao = (MenuItemTraducao) c;

        if (tradAreaHighlighter != null)
          tradAreaHighlighter.stop();

        this.tradAreaHighlighter =
            estiloLinguagensService.setHighlighterLinguagem(area_trad, itemTraducao.getLinguagem());
        this.consoleTraducaoService.setTraducaoTexto(area_cod.getText(),
            itemTraducao.getConversorStrategy());
      });
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
      if (visivel) {
        consoleTraducaoService.pararDebug();
      } else {
        consoleTraducaoService.executarTexto(this.area_cod.getText().trim(), true);
      }
      setDebugVisible(!visivel);
    });

    mi_novo.setOnAction(e -> {
      if (arquivoService.checkAlteracoesNaoSalvas()) {
        arquivoService.fechar();
        area_cod.clear();
        area_cod.appendText("variaveis\r\n" + "  //declare aqui suas variaveis\r\n" + "inicio\r\n"
            + "  //auto gerado pelo MAPLER\r\n" + "  escrever \"Ola mundo!\";\r\n" + "fim");
      }
    });

    mi_abrir.setOnAction(e -> {
      boolean abriu = this.arquivoService.abrir();
      if (abriu) {
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

    mi_site.setOnAction(e -> {
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

    mi_software.setOnAction(e -> {

      try {
        Stage stage = new Stage();
        Parent root;
        root = FXMLLoader.load(CarregadorRecursos.get().getResource(Templates.SOBRE.getUrl()));
        Scene scene = new Scene(root, 320, 400); // resolucao inicial
        stage.setScene(scene);
        stage.setTitle("MAPLER STUDIO - Sobre");
        stage.setResizable(false);
        stage.show();

      } catch (IOException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }

    });

    mi_ex_estrutura.setOnAction(e -> {
    	URL url = CarregadorRecursos.get().getResource("/exemplos/estrutura.txt");
		CarregaExemplo(url);
    });

    mi_ex_io.setOnAction(e -> {
    	URL url = CarregadorRecursos.get().getResource("/exemplos/io.txt");
		CarregaExemplo(url);
    });

    mi_ex_mdl.setOnAction(e -> {
    	URL url = CarregadorRecursos.get().getResource("/exemplos/modulos.txt");
		CarregaExemplo(url);
    });

    mi_ex_tp_v.setOnAction(e -> {
    	URL url = CarregadorRecursos.get().getResource("/exemplos/variaveis_vetores.txt");
		CarregaExemplo(url);
    });

    mi_ex_tp_l.setOnAction(e -> {
    	URL url = CarregadorRecursos.get().getResource("/exemplos/variaveis_literais.txt");
		CarregaExemplo(url);
    });

    mi_ex_tp_b.setOnAction(e -> {
    	URL url = CarregadorRecursos.get().getResource("/exemplos/variaveis_booleano.txt");
		CarregaExemplo(url);
    });

    mi_ex_tp_n.setOnAction(e -> {
    	URL url = CarregadorRecursos.get().getResource("/exemplos/variaveis_numericos.txt");
		CarregaExemplo(url);
    });

    mi_ex_tp_g.setOnAction(e -> {
    	URL url = CarregadorRecursos.get().getResource("/exemplos/variaveis.txt");
		CarregaExemplo(url);
    });

    mi_ex_op_g.setOnAction(e -> {
    	URL url = CarregadorRecursos.get().getResource("/exemplos/operacoes.txt");
		CarregaExemplo(url);
    });

    mi_ex_op_a.setOnAction(e -> {
    	URL url = CarregadorRecursos.get().getResource("/exemplos/operacao_add.txt");
		CarregaExemplo(url);
    });

    mi_ex_op_s.setOnAction(e -> {
    	URL url = CarregadorRecursos.get().getResource("/exemplos/operacao_sub.txt");
		CarregaExemplo(url);
    });

    mi_ex_op_m.setOnAction(e -> {
    	URL url = CarregadorRecursos.get().getResource("/exemplos/operacao_mult.txt");
		CarregaExemplo(url);
    });

    mi_ex_op_d.setOnAction(e -> {
    	URL url = CarregadorRecursos.get().getResource("/exemplos/operacao_div.txt");
		CarregaExemplo(url);
    });

    mi_ex_op_p.setOnAction(e -> {
    	URL url = CarregadorRecursos.get().getResource("/exemplos/operacao_precedencia.txt");
		CarregaExemplo(url);
    });

    mi_ex_co_se.setOnAction(e -> {
    	URL url = CarregadorRecursos.get().getResource("/exemplos/condicionais_se.txt");
		CarregaExemplo(url);
    });

    mi_ex_co_sn.setOnAction(e -> {
    	URL url = CarregadorRecursos.get().getResource("/exemplos/condicionais_senao.txt");
		CarregaExemplo(url);
    });

    mi_ex_lc_r.setOnAction(e -> {
    	URL url = CarregadorRecursos.get().getResource("/exemplos/laços_repita.txt");
		CarregaExemplo(url);
    });

    mi_ex_lc_p.setOnAction(e -> {
    	URL url = CarregadorRecursos.get().getResource("/exemplos/laços_para.txt");
		CarregaExemplo(url);
    });

    mi_ex_lc_e.setOnAction(e -> {
    	URL url = CarregadorRecursos.get().getResource("/exemplos/laços_enquanto.txt");
		CarregaExemplo(url);
    });

    mi_ex_lc_g.setOnAction(e -> {
    	URL url = CarregadorRecursos.get().getResource("/exemplos/laços.txt");
		CarregaExemplo(url);
    });

    btn_executar.setOnAction(e -> {
      if (btn_executar.getText().equals("Parar")) {
        consoleTraducaoService.pararExecucao();
        setConsoleVisible(false);
        btn_executar.setText("Executar");
        icon_exec.setGlyphName("PLAY");
      } else {
        setConsoleVisible(true);
        icon_exec.setGlyphName("STOP");
        consoleTraducaoService.executarTexto(this.area_cod.getText().trim(), false);
      }

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

    btn_home.getStylesheets().add(CarregadorRecursos.get().getResourceExternalForm(Estilos.BOTOES.getUrl()));

    btn_close.setOnMouseEntered(e -> {
      btn_close.setStyle("-fx-background-color: #1b1b1b;");
    });

    btn_close.setOnMouseExited(e -> {
      btn_close.setStyle("");
    });

    btn_close.setOnAction(e -> { // fechar aplicacao
    	if(this.arquivoService.checkAlteracoesNaoSalvas()) {
    		System.exit(0);
    	}
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

    // btn_left_inicio.setOnAction(e -> {
    // try {
    // this.baseService.carregaTela(Templates.INICIO.getUrl());
    // } catch (Exception e1) {
    // // TODO Auto-generated catch block
    // e1.printStackTrace();
    // }
    // });
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
    
    mi_cf_dark.setOnAction(e -> {
    	String backup_cod = area_cod.getText();
    	ConfigService.get().setCod(backup_cod);
    	ConfigService.get().setCss(Tema.Dark.getUrl());
    	 try {
    	    this.baseService.carregaTela(Templates.CODIGO.getUrl());
    	 } catch (Exception e1) {
    	    // TODO Auto-generated catch block
    	    e1.printStackTrace();
    	 }
    });
    mi_cf_light.setOnAction(e -> {
    	String backup_cod = area_cod.getText();
    	ConfigService.get().setCod(backup_cod);
    	ConfigService.get().setCss(Tema.Light.getUrl());
    	 try {
     	    this.baseService.carregaTela(Templates.CODIGO.getUrl());
     	 } catch (Exception e1) {
     	    // TODO Auto-generated catch block
     	    e1.printStackTrace();
     	 }
    });
    mi_cf_pb.setOnAction(e -> {
    	String backup_cod = area_cod.getText();
    	ConfigService.get().setCod(backup_cod);
    	ConfigService.get().setCss(Tema.Contraste.getUrl());
    	 try {
     	    this.baseService.carregaTela(Templates.CODIGO.getUrl());
     	 } catch (Exception e1) {
     	    // TODO Auto-generated catch block
     	    e1.printStackTrace();
     	 }
    });
    
    mn_spinner.getValueFactory().setValue(Integer.parseInt(ConfigService.get().getTamanhoFonte()));
    mn_spinner.setOnMouseClicked(e->{
    	setTamanhoFonte(mn_spinner.getValue());
    });

    mn_spinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
    	  if (!newValue) {
    		  setTamanhoFonte(mn_spinner.getValue());
    	  }
    });
    
    // css
    split_vertical.getStylesheets()
        .add(CarregadorRecursos.get().getResourceExternalForm(Estilos.SPLITPANE.getUrl()));
    split_horizontal.getStylesheets()
        .add(CarregadorRecursos.get().getResourceExternalForm(Estilos.SPLITPANE.getUrl()));
    m_bar.getStylesheets()
        .add(CarregadorRecursos.get().getResourceExternalForm(Estilos.MENUBAR.getUrl()));

    atualizarCss();
    
    //area_trad.getStylesheets().add(ConfigService.get().getCss());
    area_trad.setParagraphGraphicFactory(LineNumberFactory.get(area_trad));
    area_trad.setWrapText(true);
    area_trad.setLineHighlighterOn(true);
    area_trad.setEditable(false);
    area_trad.clear();
    area_trad.appendText("Selecione a linguagem no menu superior.");

    area_console.setWrapText(false);
    area_console.setLineHighlighterOn(false);
    // area_console.appendText("texte");

    //area_cod.getStylesheets().add(ConfigService.get().getCss());
    area_cod.setParagraphGraphicFactory(LineNumberFactory.get(area_cod));
    area_cod.setWrapText(true);
    area_cod.setLineHighlighterOn(true);

    if (this.codAreaHighlighter != null)
      this.codAreaHighlighter.stop();

    this.codAreaHighlighter =
        estiloLinguagensService.setHighlighterLinguagem(area_cod, Linguagem.PORTUGOL);
    area_cod.clear();
    area_cod.appendText(ConfigService.get().getCod());
    
    resize.DraggableStage(m_bar);
    

  }
  
  private void atualizarCss() {
	  
	  area_lateral.getStyleClass().add("area_lateral");
	  btn_executar.getStyleClass().add("btn_lateral");
	  btn_debug.getStyleClass().add("btn_lateral");
	  btn_traduzir.getStyleClass().add("btn_lateral");
	  btn_left_news.getStyleClass().add("btn_lateral");
	  btn_left_sobre.getStyleClass().add("btn_lateral");
	  btn_left_tutoriais.getStyleClass().add("btn_lateral");
	  ap_centerIncial.getStyleClass().add("area_fundo");
	  
	  icon_exec.getStyleClass().add("bt_exec");
	  icon_debug.getStyleClass().add("bt_debug");
	  icon_code.getStyleClass().add("bt_code");
	    
	  area_cod.getStylesheets().add(ConfigService.get().getCss());
	  area_trad.getStylesheets().add(ConfigService.get().getCss());
	  area_console.getStylesheets().add(ConfigService.get().getCss());
	  bd_inicial.getStylesheets().add(ConfigService.get().getCss());
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

  @Override
  public void terminar() {
    if (this.codAreaHighlighter != null)
      this.codAreaHighlighter.stop();
    if (this.tradAreaHighlighter != null)
      this.tradAreaHighlighter.stop();

  }

  

}
