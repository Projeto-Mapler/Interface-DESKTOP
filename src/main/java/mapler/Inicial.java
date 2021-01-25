package mapler;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import mapler.model.ResizeListener;
import mapler.service.InicialService;
import mapler.util.CarregadorRecursos;

/**
 * Classe principal para executar a aplicação
 */
public class Inicial extends Application {
  @Override
  public void start(Stage stage) throws Exception {
	InicialService inicial = InicialService.iniciarClasse(stage);
    stage.initStyle(StageStyle.UNDECORATED); // removendo botoes padrao
    Parent root = FXMLLoader.load(CarregadorRecursos.getResource("view/tela_base.fxml"));
    Scene scene = new Scene(root, 960, 720); // resolucao inicial
    stage.setScene(scene);
    stage.setTitle("MAPLER STUDIO");
    stage.setMinHeight(500);
    stage.setMinWidth(600);

    stage.focusedProperty().addListener(new ChangeListener<Boolean>() {
      @Override
      public void changed(javafx.beans.value.ObservableValue<? extends Boolean> arg0, Boolean arg1,
          Boolean arg2) {
        // ajustar cobrir barra
      }
    });

    new ResizeListener().aplicarAoStage(stage);// adiciona resize listener ao stage
    stage.show();
    
   // inicial.maximizar();// iniciar maximizado
  }

  public static void main(String[] args) {
    launch(args);
  }
}
