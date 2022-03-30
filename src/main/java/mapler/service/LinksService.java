package mapler.service;

import java.awt.Desktop;
import java.net.URI;

public class LinksService {
	
	private static LinksService instancia;
	
	private LinksService() {
		// TODO Auto-generated constructor stub
	}

	public static LinksService get() {
		if(instancia == null) {
			instancia = new LinksService();
		}
		return instancia;
	}

	public void setInstancia(LinksService instancia) {
		this.instancia = instancia;
	}
	
	public boolean openWebpage(URI uri) {
		Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
		if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
			try {
				desktop.browse(uri);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
}
