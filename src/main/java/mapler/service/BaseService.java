package mapler.service;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import mapler.util.CarregadorRecursos;

/**
 * Service para facilitar a comunicação do stage principal 
 * com outros controllers e encapsular a logica de resize do stage
 */
public final class BaseService {

  private static BaseService instancia; // singleton | instanca unica
  private AnchorPane janela; // janela principal

  private BaseService(AnchorPane janela) {
    this.janela = janela;
    
  }

  /**
   * Inicia a classe com o stage principal
   * 
   * @param janela - principal stage da aplicação
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
  
  /*
   * 
   */
  
  public int carregaTela(String link) {
	  try {
		  janela.getChildren().clear();
	      AnchorPane ap_codigo = FXMLLoader.load(CarregadorRecursos.getResource(link));
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
  

}
