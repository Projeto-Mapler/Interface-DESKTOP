package mapler.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import mapler.service.BaseService;

/**
 * Controller para tela_base.fxml
 *
 */
public class BaseController implements Initializable {

  @FXML
  AnchorPane area_principal;

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
	  BaseService baseService = BaseService.iniciarClasse(area_principal);
	  baseService.carregaTela("view/tela_inicio.fxml");
  }

}
