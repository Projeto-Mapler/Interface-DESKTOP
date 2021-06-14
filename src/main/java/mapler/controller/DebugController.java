package mapler.controller;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import debug.DebugSnapshot;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import mapler.model.resource.Estilos;
import mapler.service.ConsoleTraducaoService;
import mapler.util.CarregadorRecursos;
import modelos.tree.AstDebugNode;

public class DebugController implements Initializable {

	@FXML
	private TableView tbv_table;

	@FXML
	private JFXButton btn_executar, btn_passo, btn_parar;
	private ConsoleTraducaoService consoleTraducaoService;

//	private ObservableList<Map<String, Object>> dados;
	private Map<String, TableColumn<Map, String>> colunas;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		btn_executar.getStylesheets().add(CarregadorRecursos.getResourceExternalForm(Estilos.BOTOES.getUrl()));
		btn_passo.getStylesheets().add(CarregadorRecursos.getResourceExternalForm(Estilos.BOTOES.getUrl()));
		btn_parar.getStylesheets().add(CarregadorRecursos.getResourceExternalForm(Estilos.BOTOES.getUrl()));
		btn_executar.setOnAction(e -> {
//			Map<String, Object> ambienteSnapshot = new HashMap<String, Object>();
//			ambienteSnapshot.put("V", 32);
//			ambienteSnapshot.put("D", 22);
//			addDado(new DebugSnapshot(new AstDebugNode(1), ambienteSnapshot));
			consoleTraducaoService.continuarDebug();
		});
		btn_parar.setOnAction(e -> {
			consoleTraducaoService.pararDebug();
		});
		
		btn_passo.setOnAction(e -> {
			consoleTraducaoService.passoDebug();
		});

		// dados = FXCollections.<Map<String, Object>>observableArrayList();
		colunas = new HashMap<String, TableColumn<Map, String>>();
		criarColuna("Linha");

		// teste
//		Map<String, Object> ambienteSnapshot = new HashMap<String, Object>();
//		ambienteSnapshot.put("V", 32);
//		addDado(new DebugSnapshot(new AstDebugNode(1), ambienteSnapshot));

	}

	public void setConsoleTraducaoService(ConsoleTraducaoService consoleTraducaoService) {
		this.consoleTraducaoService = consoleTraducaoService;
	}

	private void criarColuna(String nome) {
		TableColumn<Map, String> colunaLinha = new TableColumn<>(nome);
		colunaLinha.setCellValueFactory(new MapValueFactory<>(nome));

		colunas.put(nome, colunaLinha);
		tbv_table.getColumns().add(colunaLinha);
	}

	public void addDado(DebugSnapshot value) {

		Map<String, Object> novaLinha = new HashMap<>();

		int linha = value.getLinha();
		novaLinha.put("Linha", linha);

		value.getAmbienteSnapshot().forEach((k, v) -> {

			if (!colunas.containsKey(k)) {
				criarColuna(k);
			}

			novaLinha.put(k, v);
		});
		// dados.add(novaLinha)
		tbv_table.getItems().add(novaLinha);

	}

	public void reset() {
		tbv_table.getItems().clear();
		tbv_table.getColumns().clear();

		colunas.clear();
		criarColuna("Linha");

	}

	public void toggleBtnExecutar(boolean value) {
		btn_executar.setDisable(!value);
	}

	public void toggleBtnPasso(boolean value) {
		btn_passo.setDisable(!value);
	}

	public void toggleBtnParar(boolean value) {
		btn_parar.setDisable(!value);
	}
}
