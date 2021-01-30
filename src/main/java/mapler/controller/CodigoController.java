package mapler.controller;

import java.net.URL;
import java.util.ResourceBundle;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.StyleClassedTextArea;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import mapler.model.resource.Estilos;
import mapler.model.resource.Templates;
import mapler.service.BaseService;
import mapler.service.EstiloLinguagensService;
import mapler.service.InicioService;
import mapler.util.CarregadorRecursos;

/**
 * Controller para tela_codigo.fxml
 *
 */
public class CodigoController implements Initializable {

  @FXML
  TabPane tabp_pai, tabp_filho;

  @FXML
  Tab tab_cod, tab_traducao, tab_terminal;

  @FXML
  StyleClassedTextArea area_cod, area_terminal, area_traducao;
  
  @FXML
  BorderPane bd_inicial;

  @FXML
  VBox vb_topo;

  @FXML
  AnchorPane ap_barraPrimaria, ap_barraSecundaria;

  @FXML
  JFXButton btn_home;

  @FXML
  MenuBar m_bar;

  @FXML
  Menu mn_exibir;

  @FXML // arquivo
  MenuItem mi_novo, mi_abrir, mi_salvar, mi_salvarc;

  @FXML
  MenuItem mi_traducao, mi_console;

  @FXML
  JFXButton btn_minus, btn_max, btn_close;

  @FXML
  AnchorPane ap_centerIncial;

  @FXML
  JFXButton btn_left_inicio, btn_left_tutoriais, btn_left_exemplos, btn_left_sobre, btn_left_news;

  /*private File arquivo; // referencia do arquivo que esta sendo manipulado
  private boolean arquivoComAlteracoesNaoSalvas = false;// TODO: implementar detecção de mudancas feitas e não salvas
  public CodigoController(File arquivo) {
    this.arquivo = arquivo;
  }*/
  
  private EstiloLinguagensService estiloLinguagensService;
  private InicioService inicialService;
  private BaseService baseService;

  public CodigoController() throws Exception {
    this.estiloLinguagensService = EstiloLinguagensService.getInstancia();
    this.inicialService = InicioService.getInstancia();
    this.baseService = BaseService.getInstancia();
  }  

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    /*if (arquivo != null) { // abri um arquivo
      try {
        Scanner scanner = new Scanner(new FileReader(arquivo));
        scanner.useDelimiter("\n");
        String str = "";
        while (scanner.hasNext())
          str = str + scanner.next() + "\n";
        area_cod.deleteText(0, area_cod.getText().length());
        area_cod.appendText(str);
      } catch (Exception es) {
        es.printStackTrace();
      }

    }*/
	setStyle();
  }
  
  private void setStyle() {
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
	  
	  mi_salvar.setOnAction(e -> {
	      // Arquivo.salvarArquivo(Arquivo.arquivo, true, ControllerCodigo.getPortugol());
	  });

	  mi_salvarc.setOnAction(e -> {
	      // Arquivo.SalvarComo(Arquivo.arquivo, ControllerCodigo.getPortugol());
	  });
	  
	  btn_close.setOnAction(e -> { // fechar aplicacao
	      /*
	       * if(Arquivo.salvar) { int alerta = Alertas.showConfirm("Deseja salvar o projeto?");
	       * if(alerta == 0) { System.exit(0); }else if(alerta == 1) { if(Arquivo.arquivo == null) {
	       * boolean salvar = Arquivo.SalvarComo(Arquivo.arquivo, ControllerCodigo.getPortugol());
	       * if(salvar) System.exit(0); }else { if(Arquivo.salvarArquivo(Arquivo.arquivo, true,
	       * ControllerCodigo.getPortugol())) System.exit(0); } }
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
	  
	  btn_left_inicio.setOnMouseEntered(e -> {
	      btn_left_inicio.setStyle("-fx-background-color: #ddd;");
	      btn_left_inicio.setTextFill(Paint.valueOf("#272727"));
	    });

	    btn_left_inicio.setOnMouseExited(e -> {
	      btn_left_inicio.setStyle("");
	      btn_left_inicio.setTextFill(Paint.valueOf("white"));
	    });

	    btn_left_tutoriais.setOnMouseEntered(e -> {
	      btn_left_tutoriais.setStyle("-fx-background-color: white;");
	      btn_left_tutoriais.setTextFill(Paint.valueOf("#272727"));
	    });

	    btn_left_tutoriais.setOnMouseExited(e -> {
	      btn_left_tutoriais.setStyle("");
	      btn_left_tutoriais.setTextFill(Paint.valueOf("white"));
	    });

	    btn_left_exemplos.setOnMouseEntered(e -> {
	      btn_left_exemplos.setStyle("-fx-background-color: white;");
	      btn_left_exemplos.setTextFill(Paint.valueOf("#272727"));
	    });

	    btn_left_exemplos.setOnMouseExited(e -> {
	      btn_left_exemplos.setStyle("");
	      btn_left_exemplos.setTextFill(Paint.valueOf("white"));
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
	  
	  m_bar.getStylesheets().add(CarregadorRecursos.getResourceExternalForm(Estilos.MENUBAR.getUrl()));
	  tabp_pai.getStylesheets().add(CarregadorRecursos.getResourceExternalForm(Estilos.TABPAI.getUrl()));
	  tabp_filho.getStylesheets().add(CarregadorRecursos.getResourceExternalForm(Estilos.TABFILHO.getUrl()));
	  
	  area_traducao.getStylesheets().add(CarregadorRecursos.getResourceExternalForm(Estilos.TEXTO.getUrl()));
	  area_traducao.setParagraphGraphicFactory(LineNumberFactory.get(area_traducao));
	  area_traducao.setWrapText(true);
	  area_traducao.setLineHighlighterOn(false);
	  area_traducao.setEditable(false);
	  
	  area_terminal.getStylesheets().add(CarregadorRecursos.getResourceExternalForm(Estilos.TEXTO.getUrl()));
	  area_terminal.setWrapText(false);
	  area_terminal.setLineHighlighterOn(false);
	  area_terminal.appendText("texte");
	  
	  area_cod.getStylesheets().add(CarregadorRecursos.getResourceExternalForm(Estilos.TEXTO.getUrl()));
	  area_cod.setParagraphGraphicFactory(LineNumberFactory.get(area_cod));
	  area_cod.setWrapText(true);
	  area_cod.setLineHighlighterOn(true);
	  area_cod.setStyleClass(0, 0, "variaveis");
	  
	  area_cod.setOnKeyPressed(new EventHandler<KeyEvent>() {
	      @Override
	      public void handle(KeyEvent ke) {

	        if (ke.getText().equals(";") || 
	            ke.getText().equals(".") || 
	            ke.getText().equals("'") || 
	            ke.getCode().equals(KeyCode.ENTER) || 
	            ke.getCode().equals(KeyCode.BACK_SPACE) || 
	            ke.getCode().equals(KeyCode.DELETE) || 
	            ke.getCode().equals(KeyCode.SPACE) || 
	            ke.getCode().equals(KeyCode.TAB)
	         ) {
	          
	          int comecoDaLinha = area_cod.getText().lastIndexOf("\n", area_cod.getCaretPosition() - 1);
	          int finalDaLinha = area_cod.getText().indexOf("\n", area_cod.getCaretPosition()); // se == -1 � a ultima
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

/*

  public boolean salvarArquivo(boolean isSalvarComo) {
    String txt = this.getTextoPortugol();
    if (txt.isEmpty()) {
      txt = new String("");
    }
    if (isSalvarComo) {
      return GerenciadorArquivo.salvarArquivoComo(arquivo, txt);
    } else {
      return GerenciadorArquivo.salvarArquivo(arquivo, true, txt);
    }
  }

  private String getCaminhoArquivo() {
    if (arquivo == null) {
      this.salvarArquivo(false);
    }
    return arquivo.getAbsolutePath();
  }

  public boolean isArquivoComAlteracoesNaoSalvas() {
    return arquivoComAlteracoesNaoSalvas;
  }

  public void setArquivoComAlteracoesNaoSalvas(boolean arquivoComAlteracoesNaoSalvas) {
    this.arquivoComAlteracoesNaoSalvas = arquivoComAlteracoesNaoSalvas;
  }*/

}
