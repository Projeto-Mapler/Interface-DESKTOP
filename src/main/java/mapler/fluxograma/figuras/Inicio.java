package mapler.fluxograma.figuras;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class Inicio {

	public AnchorPane criar_inicio() {
		  AnchorPane inicio;
		  try {
			  inicio = FXMLLoader.load(getClass().getResource("inicio.fxml"));
		  }catch(Exception e) {
			  inicio = new AnchorPane();
			  //System.out.println(e.getMessage());
		  }
		  inicio.setId("#inicio"+SequenceIdFigura.getNextID());
		  return inicio;
	  }
	
	public AnchorPane criar_inicio(String id) {
		  AnchorPane inicio;
		  try {
			  inicio = FXMLLoader.load(getClass().getResource("inicio.fxml"));
		  }catch(Exception e) {
			  inicio = new AnchorPane();
			  //System.out.println(e.getMessage());
		  }
		  inicio.setId(id);
		  return inicio;
	  }
	
	  public Inicio() {
		// TODO Auto-generated constructor stub
	  }
}
