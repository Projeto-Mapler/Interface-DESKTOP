package mapler.service;

import javafx.scene.Node;
import javafx.scene.control.Tab;

public class TabService extends Tab {
	private String titulo;
	private String conteudo;
	
	public TabService(String titulo, String conteudo) {
		super();
		this.titulo = titulo;
		this.conteudo = conteudo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
	
	
}
