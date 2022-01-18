package mapler;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import mapler.model.ResizeListener;
import mapler.model.resource.Templates;
import mapler.service.BaseService;
import mapler.service.InicioService;
import mapler.util.CarregadorRecursos;

/**
 * Classe principal para executar a aplicação
 */
public class Principal extends Application {
	@Override
	public void start(Stage stage) throws Exception {
		InicioService.iniciarClasse(stage);
		stage.initStyle(StageStyle.UNDECORATED); // removendo botoes padrao 
		
		Parent root = FXMLLoader.load(CarregadorRecursos.get().getResource(Templates.BASE.getUrl()));
		Scene scene = new Scene(root, 960, 720); // resolucao inicial
	 
		stage.getIcons().add(new Image("/images/icone-color.png"));//icone bandeja
		
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

		ResizeListener.getInstancia().aplicarAoStage(stage);// adiciona resize listener ao stage
		stage.show();

		// inicial.maximizar();// iniciar maximizado
	}

	@Override
	public void stop() throws Exception {
		super.stop();
		BaseService.getInstancia().terminarTodosTerminaveis();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
