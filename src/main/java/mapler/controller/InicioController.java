package mapler.controller;

import java.awt.Desktop;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.event.HyperlinkEvent;

import com.jfoenix.controls.JFXButton;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import mapler.model.resource.Tema;
import mapler.model.resource.Templates;
import mapler.service.ArquivoService;
import mapler.service.BaseService;
import mapler.service.ConfigService;
import mapler.service.InicioService;
import mapler.util.CarregadorRecursos;
import javafx.scene.control.Hyperlink;
import javafx.scene.shape.Circle;
import javafx.scene.control.Label;

/**
 * Controller para tela_inicio.fxml
 *
 */
public class InicioController implements Initializable {

	@FXML
	BorderPane bd_inicial;

	@FXML
	VBox vb_topo;

	@FXML
	AnchorPane ap_barraPrimaria, ap_barraSecundaria;

	@FXML
	JFXButton btn_minus, btn_max, btn_close, btn_tema;

	@FXML
	JFXButton btn_novo, btn_abrir, btn_aprender_repita;

	@FXML
	AnchorPane ap_centerIncial, area_lateral, ap_area_foto;

	@FXML
	JFXButton btn_left_tutoriais, btn_left_exemplos, btn_left_sobre;
	
	@FXML
	Hyperlink link_cod_enquanto, link_cod_repita, link_cod_para;
	
	@FXML
	Hyperlink link_cod_lit, link_cod_log, link_cod_num;
	
	@FXML
	Hyperlink link_cod_fimse, link_cod_senao, link_cod_se;
	
	@FXML
	Hyperlink link_cod_fim, link_cod_inicio, link_cod_var;
	
	@FXML
	Hyperlink lk_portugol, lk_macp;
	
	@FXML
	HBox hb_exemplos;
	
	@FXML
	Label lb_pri_logo, lb_sub_logo, lb_novo, lb_abrir, lb_tutoriais, lb_exemplos, lb_center1, lb_center2, lb_center3, lb_center4, lb_center5, lb_center6, lb_center7, lb_center8;
	
	@FXML
	Circle cc_logo;
	
	private InicioService inicioService;
	private BaseService baseService;

	public InicioController() throws Exception {
		this.inicioService = InicioService.getInstancia();
		this.baseService = BaseService.getInstancia();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		styles();
		atualizarCss();
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

	public void styles() {

//	    
		btn_close.setOnMouseEntered(e -> {
			btn_close.setStyle("-fx-background-color: #1b1b1b;");
		});

		btn_close.setOnMouseExited(e -> {
			btn_close.setStyle("");
		});
		
		btn_tema.setOnAction(e -> {
			String url = ConfigService.get().getCss();
			if(url.equals(CarregadorRecursos.get().getResourceExternalForm(Tema.Dark.getUrl()))) {
				ConfigService.get().setCss(Tema.Light.getUrl());
				try {
			        this.baseService.carregaTela(Templates.INICIO.getUrl());
			      } catch (Exception e1) {
			        // TODO Auto-generated catch block
			        e1.printStackTrace();
			      }
			}else if(url.equals(CarregadorRecursos.get().getResourceExternalForm(Tema.Light.getUrl()))) {
				ConfigService.get().setCss(Tema.Contraste.getUrl());
				try {
			        this.baseService.carregaTela(Templates.INICIO.getUrl());
			      } catch (Exception e1) {
			        // TODO Auto-generated catch block
			        e1.printStackTrace();
			      }
			}else {
				ConfigService.get().setCss(Tema.Dark.getUrl());
				try {
			        this.baseService.carregaTela(Templates.INICIO.getUrl());
			      } catch (Exception e1) {
			        // TODO Auto-generated catch block
			        e1.printStackTrace();
			      }
			}
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
			int i = this.inicioService.maximizar();

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
			this.inicioService.minimizar();
		});

		btn_minus.setOnMouseEntered(e -> {
			btn_minus.setStyle("-fx-background-color: #1b1b1b;");
		});

		btn_minus.setOnMouseExited(e -> {
			btn_minus.setStyle("");
		});

		btn_novo.setOnAction(e -> {
			// iniciarModoCodigo(null);
			try {
				BaseService.getInstancia().carregaTela(Templates.CODIGO.getUrl());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		btn_abrir.setOnAction(e -> {
			boolean abriu = ArquivoService.getInstance().abrir();
			if (abriu) {
				try {
					BaseService.getInstancia().carregaTela(Templates.CODIGO.getUrl());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

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
		
		btn_left_exemplos.setOnAction(e -> {
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
		
		link_cod_enquanto.setOnAction(e -> {
			try {
				URL url = new URL("https://portugol.sourceforge.io/exemplos.html");
				boolean boo = openWebpage(url.toURI());
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		link_cod_repita.setOnAction(e -> {
			try {
				URL url = new URL("https://portugol.sourceforge.io/exemplos.html");
				boolean boo = openWebpage(url.toURI());
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		link_cod_para.setOnAction(e -> {
			try {
				URL url = new URL("https://portugol.sourceforge.io/exemplos.html");
				boolean boo = openWebpage(url.toURI());
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		link_cod_lit.setOnAction(e -> {
			try {
				URL url = new URL("https://portugol.sourceforge.io/exemplos.html");
				boolean boo = openWebpage(url.toURI());
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}); 
		
		link_cod_log.setOnAction(e -> {
			try {
				URL url = new URL("https://portugol.sourceforge.io/exemplos.html");
				boolean boo = openWebpage(url.toURI());
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}); 
		
		link_cod_num.setOnAction(e -> {
			try {
				URL url = new URL("https://portugol.sourceforge.io/exemplos.html");
				boolean boo = openWebpage(url.toURI());
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		link_cod_fimse.setOnAction(e -> {
			try {
				URL url = new URL("https://portugol.sourceforge.io/exemplos.html");
				boolean boo = openWebpage(url.toURI());
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		link_cod_senao.setOnAction(e -> {
			try {
				URL url = new URL("https://portugol.sourceforge.io/exemplos.html");
				boolean boo = openWebpage(url.toURI());
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		link_cod_se.setOnAction(e -> {
			try {
				URL url = new URL("https://portugol.sourceforge.io/exemplos.html");
				boolean boo = openWebpage(url.toURI());
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		link_cod_fim.setOnAction(e -> {
			try {
				URL url = new URL("https://portugol.sourceforge.io/exemplos.html");
				boolean boo = openWebpage(url.toURI());
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		link_cod_inicio.setOnAction(e -> {
			try {
				URL url = new URL("https://portugol.sourceforge.io/exemplos.html");
				boolean boo = openWebpage(url.toURI());
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		link_cod_var.setOnAction(e -> {
			try {
				URL url = new URL("https://portugol.sourceforge.io/exemplos.html");
				boolean boo = openWebpage(url.toURI());
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		lk_portugol.setOnAction(e -> {
		try {
			URL url = new URL("https://portugol.sourceforge.io/exemplos.html");
			boolean boo = openWebpage(url.toURI());
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		});
		
		lk_macp.setOnAction(e -> {
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
		
	}
	
	private void atualizarCss() {
		btn_left_sobre.getStyleClass().add("btn_lateral");
		lb_novo.getStyleClass().add("label_logo"); 
		lb_abrir.getStyleClass().add("label_logo");
		lb_tutoriais.getStyleClass().add("label_logo");
		lb_exemplos.getStyleClass().add("label_logo");
		lb_center1.getStyleClass().add("label_logo");
		lb_center2.getStyleClass().add("label_logo");
		lb_center3.getStyleClass().add("label_logo");
		lb_center4.getStyleClass().add("label_logo");
		lb_center5.getStyleClass().add("label_logo");
		lb_center6.getStyleClass().add("label_logo");
		lb_center7.getStyleClass().add("label_logo");
		lb_center8.getStyleClass().add("label_logo");
		btn_aprender_repita.getStyleClass().add("label_logo");
		area_lateral.getStyleClass().add("area_lateral");
		btn_novo.getStyleClass().add("btn_lateral_inicio");
		btn_abrir.getStyleClass().add("btn_lateral_inicio");
		btn_left_tutoriais.getStyleClass().add("btn_lateral_inicio");
		btn_left_exemplos.getStyleClass().add("btn_lateral_inicio");
		hb_exemplos.getStyleClass().add("area_central");
		ap_area_foto.getStyleClass().add("area_central");
		ap_centerIncial.getStyleClass().add("area_inicio");
		lb_pri_logo.getStyleClass().add("label_logo");
		lb_sub_logo.getStyleClass().add("label_logo");
		cc_logo.getStyleClass().add("circulo_logo");
		bd_inicial.getStylesheets().add(ConfigService.get().getCss());
	}
	/*
	 * 
	 * private void barra_segundo() {
	 * 
	 * }
	 * 
	 * private void barra_left() {
	 * 
	 * 
	 * 
	 * }
	 * 
	 * // metodos de arquivos private void abrirArquivo() { File f =
	 * GerenciadorArquivo.abrirArquivo(); if (f != null) { iniciarModoInicial();
	 * iniciarModoCodigo(f); } }
	 * 
	 * // metodos de mudanca de interface private void iniciarModoInicial() {
	 * vb_topo.getChildren().clear(); vb_topo.getChildren().add(ap_barraPrimaria);
	 * vb_topo.getChildren().add(ap_barraSecundaria); mi_salvar.setVisible(false);
	 * mi_salvarc.setVisible(false); mn_exibir.setDisable(!false);
	 * 
	 * try { AnchorPane ap_codigo =
	 * FXMLLoader.load(CarregadorRecursos.getResource("view/tela_principal.fxml"));
	 * setCenter(ap_codigo);
	 * 
	 * } catch (IOException e) { e.printStackTrace(); } }
	 * 
	 * private void iniciarModoCodigo(File file) { vb_topo.getChildren().clear();
	 * vb_topo.getChildren().add(ap_barraPrimaria); mi_salvar.setVisible(true);
	 * mi_salvarc.setVisible(true); mn_exibir.setDisable(!true);
	 * 
	 * try { CodigoController controllerCodigo = new CodigoController(file); //
	 * this.arquivosFachada.removerMapeamento(this.idArquivoAtual);// TODO: remover
	 * esse trecho // qnd // implementar // multiplas abas - adionar a aba e um id p
	 * ela no gerenciador de abas //
	 * this.arquivosFachada.mapearArquivoController(this.idArquivoAtual,
	 * controllerCodigo); FXMLLoader loader = new FXMLLoader();
	 * loader.setController(controllerCodigo);// seta o controller ja montado com o
	 * arquivo
	 * loader.setLocation(CarregadorRecursos.getResource("view/tela_codigo.fxml"));
	 * 
	 * AnchorPane ap_codigo = loader.load();
	 * 
	 * setCenter(ap_codigo);
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } }
	 * 
	 * private void setCenter(AnchorPane ap) {
	 * ap_centerIncial.getChildren().clear(); ap_centerIncial.getChildren().add(ap);
	 * ap_centerIncial.setBottomAnchor(ap, 0.0); ap_centerIncial.setLeftAnchor(ap,
	 * 0.0); ap_centerIncial.setTopAnchor(ap, 0.0);
	 * ap_centerIncial.setRightAnchor(ap, 0.0); }
	 */
}
