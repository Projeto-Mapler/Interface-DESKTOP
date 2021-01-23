package mapler;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Inicial extends Application {

  private static double xOffset = 0;
  private static double yOffset = 0;
  private static Stage janela; // janela principal
  private static Rectangle2D bounds;

  @Override
  public void start(Stage stage) throws Exception {

    Inicial.janela = stage;
    Screen screen = Screen.getPrimary();
    bounds = screen.getVisualBounds();

    stage.initStyle(StageStyle.UNDECORATED); // removendo botoes padrao
    Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/inicial.fxml"));
    Scene scene = new Scene(root, 960, 720); // resolucao inicial
    stage.setScene(scene);
    stage.setTitle("MAPLER STUDIO");
    stage.setMinHeight(500);
    stage.setMinWidth(600);
    stage.setMaxWidth(bounds.getMaxX());
    stage.setMaxWidth(bounds.getMaxY());

    stage.focusedProperty().addListener(new ChangeListener<Boolean>() {
      @Override
      public void changed(javafx.beans.value.ObservableValue<? extends Boolean> arg0, Boolean arg1,
          Boolean arg2) {
        // ajustar cobrir barra
      }
    });

    FXMaster.addResizeListener(stage);
    stage.show();

  }

  public static int maximizar() {
    if (Inicial.janela.isMaximized()) {
      janela.setMaximized(false);
      return 0;
    } else {
      janela.setMaximized(true);
      janela.setY(janela.getY() - 1);
      janela.setX(bounds.getMinX());
      janela.setY(bounds.getMinY());
      janela.setWidth(bounds.getWidth());
      janela.setHeight(bounds.getHeight());
      return 1;
    }
  }

  public static void minimizar() {
    Inicial.janela.setIconified(true);

  }

  public static void main(String[] args) {
    launch(args);
  }

}
