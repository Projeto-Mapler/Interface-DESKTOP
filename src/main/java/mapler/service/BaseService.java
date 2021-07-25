package mapler.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import mapler.model.Terminavel;
import mapler.util.CarregadorRecursos;

/**
 * Service para facilitar a comunicação da tela_base com outros controllers e encapsular a
 * de mudança de tela
 */
public final class BaseService  {

  private static BaseService instancia; // singleton | instanca unica
  private AnchorPane janela; // janela principal
  
  private Set<Terminavel> terminaveis;

  private BaseService(AnchorPane janela) {
    this.janela = janela;
    this.terminaveis = new HashSet<Terminavel>();
  }

  /**
   * Inicia a classe com o AnchorPane da tela
   * 
   * @param janela - AnchorPane da tela_base.fxml
   * @return - instancia criada da classe
   */
  public static BaseService iniciarClasse(AnchorPane janela) {
    instancia = new BaseService(janela);
    return instancia;
  }

  /**
   * 
   * @return - a instancia unica da classe
   * @throws Exception
   */
  public static BaseService getInstancia() throws Exception {
    if (instancia == null)
      throw new Exception("Instancia da classe BaseService não foi criada!");
    return instancia;
  }

  /**
   * Troca o fxml sendo exibido no AnchorPane da tela_base
   * @param link
   * @return
   */
  public int carregaTela(String link) {
    try {
      janela.getChildren().clear();
      FXMLLoader loader = new FXMLLoader(CarregadorRecursos.getResource(link));
      
      Object controller = loader.getController();
      
      if(controller instanceof Terminavel) {
    	  this.terminaveis.add((Terminavel) controller);
      }
      
      AnchorPane ap_codigo = loader.load();
   
      janela.getChildren().add(ap_codigo);
      janela.setBottomAnchor(ap_codigo, 1.0);
      janela.setLeftAnchor(ap_codigo, 0.0);
      janela.setTopAnchor(ap_codigo, 0.0);
      janela.setRightAnchor(ap_codigo, 1.0);
      return 1;

    } catch (IOException e) {
      e.printStackTrace();
      return 0;
    }
  }


  public void terminarTodosTerminaveis() {
	  this.terminaveis.forEach(t -> t.terminar());
  }

}
