package codigo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import resources.css.FXMaster;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class Inicial extends Application {
       
	private static double xOffset = 0;
    private static double yOffset = 0;
    private static Stage janela; //janela principal
    
    @Override
    public void start(Stage stage) throws Exception {
    	
    	Inicial.janela = stage;
    	stage.initStyle(StageStyle.UNDECORATED); //removendo botoes padrao
    	Parent root = FXMLLoader.load(getClass().getResource("/resources/view/inicial.fxml")); 
    	Scene scene = new Scene(root,900,600);  //resolucao inicial   
        stage.setScene(scene);
        stage.setTitle("MAPLER STUDIO");
        FXMaster.addResizeListener(stage); //resize + move
        //stage.getIcons().add(new Image(getClass().getResourceAsStream("madein.png")));
        stage.show();
        
    }
    
    public static int maximizar() {
    	if(Inicial.janela.isMaximized()) {
    		janela.setMaximized(false);
    		return 0;
    	}else {
    		janela.setMaximized(true);
    		janela.setY(janela.getY()-1);
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
