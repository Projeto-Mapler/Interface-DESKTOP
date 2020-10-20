package codigo.controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

import org.fxmisc.richtext.StyleClassedTextArea;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import resources.css.FXMaster;

public class ControllerPrincipal implements Initializable{

    @FXML
    JFXButton btn_file1, btn_file2, btn_info, btn_saber;

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		
		btn_file1.setOnMouseEntered(e ->{
			btn_file1.setStyle("-fx-background-color: #666;");
    	});
    	
		btn_file1.setOnMouseExited(e ->{
			btn_file1.setStyle("");
    	});
		
		btn_file2.setOnMouseEntered(e ->{
			btn_file2.setStyle("-fx-background-color: #666;");
    	});
    	
		btn_file2.setOnMouseExited(e ->{
			btn_file2.setStyle("");
    	});
		
		
	}

}
