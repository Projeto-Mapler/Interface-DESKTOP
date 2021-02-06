package mapler.service;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import mapler.util.CarregadorRecursos;

/**
 * Service para facilitar a comunicação da tela_base com outros controllers e encapsular a de
 * mudança de tela
 */
public final class BaseService {

  private static BaseService instancia; // singleton | instanca unica
  private AnchorPane janela; // janela principal

  private BaseService(AnchorPane janela) {
    this.janela = janela;
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
   * 
   * @param link
   * @return
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

  public void teste(InicioService inicial) {
    Label topLabel = new Label("Arrastar aqui.... fazer do tamanho da tela ate os botoes --->");
    topLabel.setMinHeight(10);
    topLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    topLabel.setStyle("-fx-background-color:#303030; -fx-text-fill:white; -fx-font-weight:bold;");
    topLabel.setAlignment(Pos.CENTER);
    janela.getChildren().add(topLabel);
    inicial.setDragbleNode(topLabel);
  }

}
