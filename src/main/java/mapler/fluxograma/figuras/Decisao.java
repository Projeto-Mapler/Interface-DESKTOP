package mapler.fluxograma.figuras;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import mapler.model.resource.Templates;

public class Decisao extends Figura{

	public AnchorPane criar_decisao() {
		  AnchorPane decisao;
		  try {
			  decisao = FXMLLoader.load(getClass().getResource(Templates.FIG_DECISAO.getUrl()));
		  }catch(Exception e) {
			  decisao = new AnchorPane();
			  System.out.println(e.getMessage());
		  }
		  decisao.setId("#decisao"+SequenceIdFigura.getNextID());
		  return decisao;
	}
	
	public AnchorPane criar_decisao(String id) {
		  AnchorPane decisao;
		  try {
			  decisao = FXMLLoader.load(getClass().getResource(Templates.FIG_DECISAO.getUrl()));
		  }catch(Exception e) {
			  decisao = new AnchorPane();
			  System.out.println(e.getMessage());
		  }
		  decisao.setId(id);
		  return decisao;
	}
	
	public Decisao() {
		// TODO Auto-generated constructor stub
	}
	
}
