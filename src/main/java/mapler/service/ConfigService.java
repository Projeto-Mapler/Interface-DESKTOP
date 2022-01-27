package mapler.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.spi.FileSystemProvider;
import java.util.Collections;
import java.util.Properties;

import mapler.util.CarregadorRecursos;

public class ConfigService {

	
	private static ConfigService instance;
	private String css;
	private String tamanhoFonte;
	private String cod; //salvar codigo antes de reiniciar a pagina
	
	private ConfigService() {}
	
	public static ConfigService get() {
		  if(instance == null) {
			  instance = new ConfigService();
			  instance.setCss(null);
			  instance.setTamanhoFonte(null);
			  instance.setCod("variaveis\r\n" + "  //declare aqui suas variaveis\r\n" + "inicio\r\n" + "  //auto gerado pelo MAPLER\r\n" + "  escrever \"Ola mundo!\";\r\n" + "fim");
		  } 
		  return instance;
	  }
	
	public String getCss() {
		return CarregadorRecursos.get().getResourceExternalForm(css);
	}
	
	public void setTamanhoFonte(String tamanho) {
		try {
			if(tamanho != null) {
				setProp("fonte", tamanho);
				this.tamanhoFonte = tamanho;
			}else {
				Properties prop = getProp();
				this.tamanhoFonte = prop.getProperty("fonte");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getTamanhoFonte() {
		return this.tamanhoFonte;
	}
	
	public void setCod(String cod) {
		this.cod = cod;
	}
	
	public String getCod() {
		return this.cod;
	}
	
	public void setCss(String novolink){
		if(novolink != null) {
			try {
				setProp("tema", novolink);
				this.css = novolink;
				
		    }catch (Exception e) {
		    	// TODO: handle exception
		    	e.getMessage();
		    }
	}else {
       
		try {
			Properties prop = getProp();
			css = prop.getProperty("tema");
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
  }
	
  private Properties getProp() throws IOException {
		Properties props = new Properties();
		
		String path = System.getProperty("user.home") + File.separator + "Documents";
		path += File.separator + "mapler";
		File customDir = new File(path);

		if (customDir.exists()) {
			try{
				FileInputStream file = new FileInputStream(path+"/config.properties");
				props.load(file);
			}catch(Exception e) {
				FileWriter myWriter = new FileWriter(path+"/config.properties");
			      myWriter.write("tema=/css/config-dark.css\r\n"
			      				+ "fonte=16");
			      myWriter.close();
			      FileInputStream file = new FileInputStream(path+"/config.properties");
				  props.load(file);
			}
			
		}else if(customDir.mkdirs()) {
			  try {
				  FileWriter myWriter = new FileWriter(path+"/config.properties");
			      myWriter.write("tema=/css/config-dark.css\r\n"
			      				+ "fonte=16");
			      myWriter.close();
			      FileInputStream file = new FileInputStream(path+"/config.properties");
				  props.load(file);
			    } catch (IOException e) {
			      e.printStackTrace();
			    }
		} else {
		      System.out.println(customDir + " was not created");
		}
		 
		return props;

	}
  
  private Properties setProp(String property, String value) {
		Properties props = new Properties();
		
		String path = System.getProperty("user.home") + File.separator + "Documents";
		path += File.separator + "mapler";
		File customDir = new File(path);

		if (customDir.exists() || customDir.mkdirs()) {
			  try {
				  FileInputStream file = new FileInputStream(path+"/config.properties");
				  props.load(file);
					props.setProperty(property, value);
					File arq = new File(path+"/config.properties");
					FileOutputStream fos = new FileOutputStream(arq);
					props.store(fos,"Arquivo de propriedades.");
					fos.close();
					
			    } catch (IOException e) {
			      e.printStackTrace();
			    }
		} else {
		      System.out.println(customDir + " was not created");
		}
		return props;
		
	}
  
}
