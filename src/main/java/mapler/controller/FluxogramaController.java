package mapler.controller;

import static javafx.scene.input.KeyCode.S;
import static org.fxmisc.wellbehaved.event.EventPattern.keyPressed;

import java.net.URL;
import java.util.ResourceBundle;

import org.fxmisc.wellbehaved.event.InputMap;
import org.fxmisc.wellbehaved.event.Nodes;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import mapler.fluxograma.diagrama.Associacao;
import mapler.fluxograma.diagrama.FMX;
import mapler.fluxograma.diagrama.Fluxograma;
import mapler.fluxograma.diagrama.Tradutor;
import mapler.fluxograma.figuras.Decisao;
import mapler.fluxograma.figuras.Entrada;
import mapler.fluxograma.figuras.Fim;
import mapler.fluxograma.figuras.Inicio;
import mapler.fluxograma.figuras.Processamento;
import mapler.fluxograma.figuras.Saida;
import mapler.model.ResizeListener;
import mapler.model.resource.Estilos;
import mapler.model.resource.Templates;
import mapler.service.AlertaService;
import mapler.service.ArquivoFluxogramaService;
import mapler.service.ArquivoService;
import mapler.service.BaseService;
import mapler.service.ConfigService;
import mapler.service.ConsoleService;
import mapler.service.FigurasService;
import mapler.service.InicioService;
import mapler.util.CarregadorRecursos;

/**
 * Controller para fluxograma.fxml
 *
 */
public class FluxogramaController implements Initializable {

	@FXML
	MenuBar m_bar;

	@FXML
	JFXButton btn_minus, btn_max, btn_close;

	@FXML
	JFXButton btn_inicio, btn_fim, btn_decisao, btn_processamento, btn_entrada, btn_saida;

	@FXML
	JFXButton btn_move, btn_associate, btn_remove, btn_home;

	@FXML
	MenuItem mn_novo, mn_abrir, mn_salvar, mn_salvarcomo, mn_sair, mn_traduzir_pt, mn_sb_portugol, mn_sb_fluxogramas,
			mn_sobre;

	@FXML
	AnchorPane root;

	Canvas canvas = new Canvas(600, 300);
	GraphicsContext ctx = canvas.getGraphicsContext2D();
	double x = 0, y = 0;

	
	private FigurasService figurasService = new FigurasService();
	private InicioService inicialService;
	private BaseService baseService;
	private ResizeListener resize;

	// estrutura dos dados
	private Fluxograma fluxograma;
	
	//console
	private ConsoleService consoleService;
	

	public FluxogramaController() throws Exception {
		this.fluxograma = Fluxograma.getInstancia();
		this.fluxograma.reiniciar();
		this.inicialService = InicioService.getInstancia();
		this.baseService = BaseService.getInstancia();
		this.resize = ResizeListener.getInstancia();
		this.consoleService.getInstancia();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		btns();
		setEventos();
		root.getChildren().add(canvas);
		root.setCursor(Cursor.CLOSED_HAND);
		this.consoleService.getInstancia().startConsole(root);
		btn_move.setStyle("-fx-border-color: #fff;");
		btn_associate.setStyle("");
		btn_remove.setStyle("");

		String conteudo = ArquivoFluxogramaService.getInstance().getConteudo();
		if (conteudo != null) {
			carregarFluxogramaDeArquivo(this.consoleService.getInstancia().getTextoConsole());
		}

	}

	private FMX carregarFluxograma() {
		FMX processador_fluxograma = new FMX();
		processador_fluxograma.fluxograma2String(root, fluxograma);
		return processador_fluxograma;
	}

	private void setEventos() {

		InputMap<Event> salvar = InputMap
				.consume(keyPressed(S, KeyCodeCombination.CONTROL_DOWN, KeyCodeCombination.SHORTCUT_ANY), e -> {
					ArquivoFluxogramaService.getInstance().salvar(carregarFluxograma());
				}

				);

		InputMap<Event> salvarComo = InputMap.consume(keyPressed(S, KeyCodeCombination.CONTROL_DOWN,
				KeyCodeCombination.SHIFT_DOWN, KeyCodeCombination.SHORTCUT_ANY), e -> {
					FMX aux = new FMX();
					aux.fluxograma2String(root, fluxograma);
					ArquivoFluxogramaService.getInstance().salvarComo(aux.getString());
				}

		);

		Nodes.addInputMap(baseService.getJanela(), salvar);
		Nodes.addInputMap(baseService.getJanela(), salvarComo);

	}

	private void btns() {

		m_bar.getStylesheets().add(CarregadorRecursos.get().getResourceExternalForm(Estilos.MENUBAR.getUrl()));

		resize.DraggableStage(m_bar);

		btn_home.setOnAction(e -> {
			try {
				ArquivoFluxogramaService.getInstance().newInstance();
				this.baseService.carregaTela(Templates.INICIO.getUrl());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		btn_home.getStylesheets().add(CarregadorRecursos.get().getResourceExternalForm(Estilos.BOTOES.getUrl()));

		btn_minus.setOnAction(e -> { // minimizar aplicacao
			this.inicialService.minimizar();
		});

		btn_minus.setOnMouseEntered(e -> {
			btn_minus.setStyle("-fx-background-color: #1b1b1b;");
		});

		btn_minus.setOnMouseExited(e -> {
			btn_minus.setStyle("");
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

		btn_close.setOnMouseEntered(e -> {
			btn_close.setStyle("-fx-background-color: #1b1b1b;");
		});

		btn_close.setOnMouseExited(e -> {
			btn_close.setStyle("");
		});

		btn_close.setOnAction(e -> { // fechar aplicacao
			if (ArquivoFluxogramaService.getInstance().checkAlteracoesNaoSalvas()) {
				System.exit(0);
			}
		});

		mn_novo.setOnAction(e -> {
			if (ArquivoFluxogramaService.getInstance().checkAlteracoesNaoSalvas()) {
				ArquivoFluxogramaService.getInstance().fechar();
				root.getChildren().clear();
				this.consoleService.getInstancia().startConsole(root);
				fluxograma.reiniciar();
				fluxograma = Fluxograma.getInstancia();
			}
		});

		mn_abrir.setOnAction(e -> {
			boolean boo = ArquivoFluxogramaService.getInstance().abrir();
			if (boo) {
				carregarFluxogramaDeArquivo(this.consoleService.getInstancia().getTextoConsole());
			}
		});

		mn_salvar.setOnAction(e -> {
			ArquivoFluxogramaService.getInstance().salvar(carregarFluxograma());
		});

		mn_salvarcomo.setOnAction(e -> {
			FMX aux = new FMX();
			aux.fluxograma2String(root, fluxograma);
			ArquivoFluxogramaService.getInstance().salvarComo(aux.getString());
		});

		mn_traduzir_pt.setOnAction(e -> {
			int resp = AlertaService.showConfirm("O fluxograma será transformado em portugol. Deseja salvar o esquema do fluxograma?");
			if (resp == 1) {
				ArquivoFluxogramaService.getInstance().salvar(carregarFluxograma());
				
			} else if (resp == -1) {
				return;
			}
			
			String portugol = Tradutor.getTraducao2Portugol(fluxograma);
			ArquivoFluxogramaService.getInstance().setTraducao(portugol);
			if (portugol != null) {
				try {
					BaseService.getInstancia().carregaTela(Templates.CODIGO.getUrl());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}else {
				AlertaService.showAviso("Fluxograma invalido para traducao.");
			}

		});

		btn_processamento.setOnAction(e -> {
			Processamento pt = new Processamento();
			cria_figura(pt.criar_processamento(), 6);
		});

		btn_saida.setOnAction(e -> {
			Saida sd = new Saida();
			cria_figura(sd.criar_saida(), 3);
		});

		btn_entrada.setOnAction(e -> {
			Entrada et = new Entrada();
			cria_figura(et.criar_entrada(), 2);
		});

		btn_inicio.setOnAction(e -> {

			if (!this.fluxograma.existeInicio()) {
				Inicio in = new Inicio();
				AnchorPane ap = in.criar_inicio();
				cria_figura(ap, 5);
				this.fluxograma.setInicio(ap);
			}

		});

		btn_fim.setOnAction(e -> {

			if (!this.fluxograma.existeFim()) {
				Fim fm = new Fim();
				AnchorPane ap = fm.criar_fim();
				cria_figura(ap, 4);
				this.fluxograma.setFim(ap);
			}
		});

		btn_decisao.setOnAction(e -> {
			Decisao dc = new Decisao();
			cria_figura(dc.criar_decisao(), 1);
		});

		// style="-fx-border-color: #790b77;"
		btn_move.setOnAction(e -> {
			btn_move.setStyle("-fx-border-color: #fff;");
			btn_associate.setStyle("");
			btn_remove.setStyle("");

			// mouse_status = 1;//mover
			figurasService.setMouse_status(1);
			root.setCursor(Cursor.CLOSED_HAND);
			figurasService.setAssociarPane(null);
			figurasService.setAssociarTipo(0);
		});

		btn_remove.setOnAction(e -> {
			btn_remove.setStyle("-fx-border-color: #fff;");
			btn_associate.setStyle("");
			btn_move.setStyle("");

			// mouse_status = 2;//remover
			figurasService.setMouse_status(2);
			root.setCursor(Cursor.CROSSHAIR);
			figurasService.setAssociarPane(null);
			figurasService.setAssociarTipo(0);
			// System.out.println(btn_remove.getWidth() + " " + btn_remove.getHeight());
		});

		btn_associate.setOnAction(e -> {
			btn_associate.setStyle("-fx-border-color: #fff;");
			btn_move.setStyle("");
			btn_remove.setStyle("");

			// mouse_status = 3;//ligacoes
			figurasService.setMouse_status(3);
			root.setCursor(Cursor.HAND);
		});

	};

	private void carregarFluxogramaDeArquivo(JFXTextArea console) {
		// TODO Auto-generated method stub
		String aberto = ArquivoFluxogramaService.getInstance().getConteudo();
		root.getChildren().clear();
		fluxograma.iniciaAssociacoes();
		fluxograma.setFim(null);
		fluxograma.setInicio(null);
		root.getChildren().setAll(new FMX().string2Pane(aberto, fluxograma, figurasService,  console).getChildren());
		for (Associacao a : fluxograma.getAssociacoes()) {
			figurasService.arrastaItens(root, a.getPane1(), a.getTipo_pane1(), fluxograma);
			figurasService.arrastaItens(root, a.getPane2(), a.getTipo_pane2(), fluxograma);
			figurasService.criar_linha(root, fluxograma, a);
		}
		this.consoleService.getInstancia().startConsole(root);
	}

	private void cria_figura(AnchorPane ap, int tipo) {
		ap.setLayoutX(0);
		ap.setLayoutY(0);
		// arrastaItens ( ap , tipo );
		figurasService.arrastaItens(root, ap, tipo, fluxograma);
		root.getChildren().add(ap);
	}

}
