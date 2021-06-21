package mapler.model;

import conversores.ConversorStrategy;
import javafx.scene.control.MenuItem;

public class MenuItemTraducao extends MenuItem {
	private Linguagem linguagem;

	public Linguagem getLinguagem() {
		return linguagem;
	}

	public void setLinguagem(Linguagem linguagem) {
		this.linguagem = linguagem;
	}
	
	public ConversorStrategy getConversorStrategy() {
		return linguagem.getConversorStrategy();
	}
	
}
