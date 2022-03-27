package mapler.fluxograma.figuras;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import mapler.model.resource.Templates;

public class Saida {

	public AnchorPane criar_saida() {
		  AnchorPane saida;
		  try {
			  saida = FXMLLoader.load(getClass().getResource(Templates.FIG_SAIDA.getUrl()));
		  }catch(Exception e) {
			  saida = new AnchorPane();
			  System.out.println(e.getMessage());
		  }
		  saida.setId("#saida"+SequenceIdFigura.getNextID());
		  return saida;
	  }
	
	public AnchorPane criar_saida(String id) {
		  AnchorPane saida;
		  try {
			  saida = FXMLLoader.load(getClass().getResource(Templates.FIG_SAIDA.getUrl()));
		  }catch(Exception e) {
			  saida = new AnchorPane();
			  System.out.println(e.getMessage());
		  }
		  saida.setId(id);
		  return saida;
	  }
	
	public Saida() {
		// TODO Auto-generated constructor stub
	}
	
}
