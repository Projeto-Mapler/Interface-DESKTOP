package resources.bibliotecas;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;



public class Arquivo {
	
	public static File arquivo;
	public static boolean abrir = false;
	public static boolean salvar = false;
	
	public static File openJanelaArquivo() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll( new ExtensionFilter("Portugol Files", "*.txt"));
		File open = fileChooser.showOpenDialog(null);
		if(open != null) {
			arquivo = open;
			return open;
		}
		else {
			//Alertas("File selection cancelled.");
			return null;
		}
	}
	
	public static boolean salvarArquivo(File arquivo,boolean aviso, String txt) {
		 if(arquivo != null) {   
			 try {
			   FileWriter writer = new FileWriter(arquivo);
			   writer.write(txt);
			   writer.close();
			   if(aviso)
				  Alertas.showAviso("O arquivo foi salvo!");
			   return true;
	    	   }catch(IOException e) {
	    		   arquivo = null;
	    		   return false;
	    	   }
	       }else {
	    	   if(aviso)
	    		   return SalvarComo(arquivo, txt);
	    	   
	       }
		return false;
	}
	
	public static boolean SalvarComo(File arquivo, String txt) {
		FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Save file");
    	fileChooser.setInitialFileName("codigo");
    	fileChooser.getExtensionFilters().addAll( new ExtensionFilter("Portugol Files", ".txt"),new ExtensionFilter("Portugol Files", "*"));
    	arquivo = fileChooser.showSaveDialog(null);
    	
    	if (arquivo != null) {
    	   return salvarArquivo(arquivo,false,txt);
    	}
    	else {
    		return false;
    	}
	}
}
