package mapler.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import mapler.service.BaseService;
import mapler.util.CarregadorRecursos;

public class BaseController implements Initializable {

  @FXML
  AnchorPane area_principal;


  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    // TODO Auto-generated method stub

	  BaseService inicial = BaseService.iniciarClasse(area_principal);
	  inicial.carregaTela("view/tela_inicio.fxml");
  }

}
