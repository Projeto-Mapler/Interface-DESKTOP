package mapler.service;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.AnchorPane;

public class ConsoleService {

	//classe para controlar o console de fluxograma
	private JFXTextArea textoConsole;
	private static ConsoleService instancia = null;
	
	public static ConsoleService getInstancia() {
		if(instancia == null) {
			instancia = new ConsoleService();
		}
		return instancia;
	}
	
	public static ConsoleService newInstancia() {
		instancia = new ConsoleService();
		return instancia;
	}
	
	public void sendMensagem(String msg) {
		this.textoConsole.clear();
		this.textoConsole.setText("<< Console >>");
		this.textoConsole.appendText("\n"+msg);
	}
	
	public void startConsole(AnchorPane root) {
		AnchorPane area_console = new AnchorPane();
		area_console.setPrefSize(200, 100);

		root.getChildren().add(area_console);
		root.setBottomAnchor(area_console, 0.0);
		root.setRightAnchor(area_console, 0.0);

		JFXTextArea texto = new JFXTextArea();
		texto.setEditable(false);
		texto.setText("<< Console >>");
		texto.setStyle("-fx-background-color: #a7aabe;");

		JFXButton btn_clear_console = new JFXButton(" ");
		btn_clear_console.setStyle("-fx-background-color: #AAA;");
		FontAwesomeIcon icon = new FontAwesomeIcon();
		icon.setGlyphName("FILE_EXCEL_ALT");
		btn_clear_console.setGraphic(icon);
		btn_clear_console.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

		area_console.getChildren().add(texto);
		area_console.setBottomAnchor(texto, 2.0);
		area_console.setLeftAnchor(texto, 10.0);
		area_console.setRightAnchor(texto, 10.0);
		area_console.setTopAnchor(texto, 2.0);

		area_console.getChildren().add(btn_clear_console);
		area_console.setRightAnchor(btn_clear_console, 11.0);
		area_console.setTopAnchor(btn_clear_console, 3.0);

		btn_clear_console.setOnAction(e -> {
			texto.clear();
			texto.setText("<< Console >>");
		});
		
		this.textoConsole = texto;
	}

	public JFXTextArea getTextoConsole() {
		return textoConsole;
	}

	public void setTextoConsole(JFXTextArea textoConsole) {
		this.textoConsole = textoConsole;
	}
	
	
}
