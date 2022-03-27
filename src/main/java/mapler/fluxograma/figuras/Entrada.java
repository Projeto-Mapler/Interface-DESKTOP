package mapler.fluxograma.figuras;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import mapler.model.resource.Templates;

public class Entrada extends Figura{
	
	public AnchorPane criar_entrada() {
		  AnchorPane entrada;
		  try {
			  entrada = FXMLLoader.load(getClass().getResource(Templates.FIG_ENTRADA.getUrl()));
		  }catch(Exception e) {
			  entrada = new AnchorPane();
			  System.out.println(e.getMessage());
		  }
		  entrada.setId("#entrada"+SequenceIdFigura.getNextID());
		  return entrada;
	}
	
	public AnchorPane criar_entrada(String id) {
		  AnchorPane entrada;
		  try {
			  entrada = FXMLLoader.load(getClass().getResource(Templates.FIG_ENTRADA.getUrl()));
		  }catch(Exception e) {
			  entrada = new AnchorPane();
			  System.out.println(e.getMessage());
		  }
		  entrada.setId(id);
		  return entrada;
	}

	public Entrada() {
		// TODO Auto-generated constructor stub
	}
	
}
