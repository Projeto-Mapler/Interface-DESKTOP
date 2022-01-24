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
			  //instance.setCss("/css/config-dark.css");
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
		URL url = CarregadorRecursos.get().getResource("/config/cfg.properties");
    	FileInputStream file = new FileInputStream(url.getPath().replaceFirst("/C", "C"));
		props.load(file);
		return props;

	}
  
  private Properties setProp(String property, String value) throws IOException {
		Properties props = new Properties();
		URL url = CarregadorRecursos.get().getResource("/config/cfg.properties");
		FileInputStream file = new FileInputStream(url.getPath().replaceFirst("/C", "C"));
		props.load(file);
		props.setProperty(property, value);
		File arq = new File(url.getPath());
		FileOutputStream fos = new FileOutputStream(arq);
		props.store(fos,"Arquivo de propriedades.");
		fos.close();
		return props;
	}
  
}
