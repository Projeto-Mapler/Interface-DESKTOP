package mapler.service;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import mapler.model.resource.Estilos;
import mapler.util.CarregadorRecursos;

/**
 * Exibe alertas da IDE
 */
public final class AlertaService {
  private static final String ESTILO_CSS = CarregadorRecursos.getResourceExternalForm(Estilos.ARLETAS.getUrl());
  /**
   * Classe estática
   */
  private AlertaService() {}

  public static void showAviso(String aviso) {
    Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
    DialogPane dialogPane = dialogoInfo.getDialogPane();
    dialogPane.getStylesheets().add(ESTILO_CSS);
    dialogPane.getStyleClass().add("myDialog");
    dialogoInfo.setTitle("AVISO");
    dialogoInfo.setHeaderText(aviso);
    dialogoInfo.setContentText("");
    dialogoInfo.showAndWait();
  }

  public static int showConfirm(String aviso) {
    Alert dialogoInfo = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.CLOSE);
    DialogPane dialogPane = dialogoInfo.getDialogPane();
    dialogPane.getStylesheets().add(ESTILO_CSS);
    dialogPane.getStyleClass().add("myDialog");
    dialogoInfo.setTitle("CONFIRMAÇÃO");
    dialogoInfo.setHeaderText(aviso);
    dialogoInfo.setContentText("");
    ButtonType btnSim = new ButtonType("Sim");
    ButtonType btnNao = new ButtonType("Não");
    ButtonType btnCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
    dialogoInfo.getButtonTypes().setAll(btnSim, btnNao, btnCancelar);
    Optional<ButtonType> result = dialogoInfo.showAndWait();
    if (result.isPresent() && result.get() == btnSim) {
      return 1;
    } else if (result.isPresent() && result.get() == btnNao) {
      return 0;
    } else {
      return -1;
    }
  }
}
