package mapler.fluxograma.figuras;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import mapler.model.resource.Templates;

public class Processamento {

	public AnchorPane criar_processamento() {
		  AnchorPane processamento;
		  try {
			  processamento = FXMLLoader.load(getClass().getResource(Templates.FIG_PROCESSAMENTO.getUrl()));
		  }catch(Exception e) {
			  processamento = new AnchorPane();
			  System.out.println(e.getMessage());
		  }
		  processamento.setId("#processamento"+SequenceIdFigura.getNextID());
		  return processamento;
	  }
	
	public AnchorPane criar_processamento(String id) {
		  AnchorPane processamento;
		  try {
			  processamento = FXMLLoader.load(getClass().getResource(Templates.FIG_PROCESSAMENTO.getUrl()));
		  }catch(Exception e) {
			  processamento = new AnchorPane();
			  System.out.println(e.getMessage());
		  }
		  processamento.setId(id);
		  return processamento;
	  }
	
	public Processamento() {
		// TODO Auto-generated constructor stub
	}
}
