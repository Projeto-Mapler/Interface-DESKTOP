package mapler.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import mapler.service.BaseService;
import mapler.service.EstiloService;
import mapler.service.InicialService;
import mapler.util.CarregadorRecursos;
import mapler.util.GerenciadorArquivo;

public class InicialController implements Initializable {

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
  JFXButton btn_novo, btn_abrir;

  @FXML
  AnchorPane ap_centerIncial;

  @FXML
  JFXButton btn_left_inicio, btn_left_tutoriais, btn_left_exemplos, btn_left_sobre, btn_left_news, btn_file1, btn_file2;

  public static MenuItem mni_console;
  private InicialService inicial;


  public InicialController() throws Exception {
    this.inicial = InicialService.getInstancia();
  }

  @Override
  public void initialize(URL url, ResourceBundle rb) {

    /*iniciarModoInicial();// iniciar na tela principal

    barra_controle();
    menuBar();
    botoesMenu();
    barra_segundo();
    barra_left();

    mni_console = mi_console;*/
    styles();
  }
  
  public void styles() {
	    btn_home.setOnAction(e -> {
	       
	    });

	    btn_home.setOnMouseEntered(e -> {
	      btn_home.setStyle(
	          "-fx-background-color: #1b1b1b; -fx-border-color: transparent #4A4949 transparent transparent;");
	    });

	    btn_home.setOnMouseExited(e -> {
	      btn_home.setStyle(
	          "-fx-background-color: transparent; -fx-border-color: transparent #4A4949 transparent transparent;");
	    });
	    
	  btn_close.setOnMouseEntered(e -> {
	      btn_close.setStyle("-fx-background-color: #1b1b1b;");
	  });
	  
	  btn_close.setOnMouseExited(e -> {
	      btn_close.setStyle("");
	  });
	  
	  mi_abrir.setOnAction(e -> {
	      //abrirArquivo();
	  });

	  mi_novo.setOnAction(e -> {
	      //iniciarModoCodigo(null);
		  try {
				BaseService.getInstancia().carregaTela("view/tela_codigos.fxml");
		  } catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
		  }
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
	      int i = this.inicial.maximizar();

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
	      this.inicial.minimizar();
	  });

	  btn_minus.setOnMouseEntered(e -> {
	      btn_minus.setStyle("-fx-background-color: #1b1b1b;");
	  });

	  btn_minus.setOnMouseExited(e -> {
	      btn_minus.setStyle("");
	  });
	  
	  m_bar.getStylesheets().add(EstiloService.menuBar());
	  
	  btn_novo.setOnAction(e -> {
	     // iniciarModoCodigo(null);
		  try {
				BaseService.getInstancia().carregaTela("view/tela_codigos.fxml");
		  } catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
		   }
	  });

	  btn_novo.setOnMouseEntered(e -> {
	    btn_novo.setStyle("-fx-background-color: #2a2a2a;-fx-border-color: white; -fx-border-radius: 5 9 5 5;");
	  });

	  btn_novo.setOnMouseExited(e -> {
	      btn_novo.setStyle("-fx-border-color: white; -fx-border-radius: 5 9 5 5;");
	  });

	  btn_abrir.setOnAction(e -> {
	      //abrirArquivo();
	   });

	  btn_abrir.setOnMouseEntered(e -> {
	      btn_abrir.setStyle("-fx-background-color: #2a2a2a;-fx-border-color: white; -fx-border-radius: 5 9 5 5;");
	  });

	  btn_abrir.setOnMouseExited(e -> {
	      btn_abrir.setStyle("-fx-border-color: white; -fx-border-radius: 5 9 5 5;");
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
	    
	    btn_file1.setOnMouseEntered(e -> {
	      btn_file1.setStyle("-fx-background-color: #666;");
	    });

	    btn_file1.setOnMouseExited(e -> {
	      btn_file1.setStyle("");
	    });

	    btn_file2.setOnMouseEntered(e -> {
	      btn_file2.setStyle("-fx-background-color: #666;");
	    });

	    btn_file2.setOnMouseExited(e -> {
	      btn_file2.setStyle("");
	    });
  }
/*

  private void barra_segundo() {
    
  }

  private void barra_left() {


    
  }

  // metodos de arquivos
  private void abrirArquivo() {
    File f = GerenciadorArquivo.abrirArquivo();
    if (f != null) {
      iniciarModoInicial();
      iniciarModoCodigo(f);
    }
  }

  // metodos de mudanca de interface
  private void iniciarModoInicial() {
    vb_topo.getChildren().clear();
    vb_topo.getChildren().add(ap_barraPrimaria);
    vb_topo.getChildren().add(ap_barraSecundaria);
    mi_salvar.setVisible(false);
    mi_salvarc.setVisible(false);
    mn_exibir.setDisable(!false);

    try {
      AnchorPane ap_codigo =
          FXMLLoader.load(CarregadorRecursos.getResource("view/tela_principal.fxml"));
      setCenter(ap_codigo);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void iniciarModoCodigo(File file) {
    vb_topo.getChildren().clear();
    vb_topo.getChildren().add(ap_barraPrimaria);
    mi_salvar.setVisible(true);
    mi_salvarc.setVisible(true);
    mn_exibir.setDisable(!true);

    try {
      CodigoController controllerCodigo = new CodigoController(file);
      // this.arquivosFachada.removerMapeamento(this.idArquivoAtual);// TODO: remover esse trecho
      // qnd
      // implementar
      // multiplas abas - adionar a aba e um id p ela no gerenciador de abas
      // this.arquivosFachada.mapearArquivoController(this.idArquivoAtual, controllerCodigo);
      FXMLLoader loader = new FXMLLoader();
      loader.setController(controllerCodigo);// seta o controller ja montado com o arquivo
      loader.setLocation(CarregadorRecursos.getResource("view/tela_codigo.fxml"));

      AnchorPane ap_codigo = loader.load();

      setCenter(ap_codigo);

    } catch (Exception e) {
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
*/
}
