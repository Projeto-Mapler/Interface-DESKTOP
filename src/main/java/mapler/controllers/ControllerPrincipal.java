package mapler.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class ControllerPrincipal implements Initializable {

  @FXML
  JFXButton btn_file1, btn_file2, btn_info, btn_saber;


  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    // TODO Auto-generated method stub


    btn_file1.setOnMouseEntered(e -> {
      btn_file1.setStyle("-fx-background-color: #666;");
    });

    btn_file1.setOnMouseExited(e -> {
      btn_file1.setStyle("");
    });

    btn_file2.setOnMouseEntered(e -> {
      btn_file2.setStyle("-fx-background-color: #666;");
    });

    btn_file2.setOnMouseExited(e -> {
      btn_file2.setStyle("");
    });


  }

}
