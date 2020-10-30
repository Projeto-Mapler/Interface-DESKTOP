package codigo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import resources.css.FXMaster;

public class Inicial extends Application {

    private static double xOffset = 0;
    private static double yOffset = 0;
    private static Stage janela; // janela principal

    @Override
    public void start(Stage stage) throws Exception {

	Inicial.janela = stage;
	stage.initStyle(StageStyle.UNDECORATED); // removendo botoes padrao
	Parent root = FXMLLoader.load(getClass().getResource("/resources/view/inicial.fxml"));
	Scene scene = new Scene(root, 1152, 768); // resolucao inicial
	stage.setScene(scene);
	stage.setTitle("MAPLER STUDIO");
	stage.setMinHeight(768);
	stage.setMinWidth(1152);
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
