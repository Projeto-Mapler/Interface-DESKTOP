package mapler.fluxograma.figuras;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import mapler.model.resource.Templates;

public class Legenda extends Figura{

	public AnchorPane criar_legenda() {
		  AnchorPane legenda;
		  try {
			  legenda = FXMLLoader.load(getClass().getResource(Templates.FIG_LEGENDA.getUrl()));
		  }catch(Exception e) {
			  legenda = new AnchorPane();
			  System.out.println(e.getMessage());
		  }
		  legenda.setId("#legenda"+SequenceIdFigura.getNextID());
		  return legenda;
	  }
	
	public AnchorPane criar_legenda(String id) {
		  AnchorPane legenda;
		  try {
			  legenda = FXMLLoader.load(getClass().getResource(Templates.FIG_LEGENDA.getUrl()));
		  }catch(Exception e) {
			  legenda = new AnchorPane();
			  System.out.println(e.getMessage());
		  }
		  legenda.setId(id);
		  return legenda;
	  }
	
}
