package mapler.model;

import conversores.ConversorStrategy;
import javafx.scene.control.MenuItem;
import mapler.model.linguagem.Linguagem;
import mapler.model.linguagem.Linguagens;

public class MenuItemTraducao extends MenuItem {
	private int linguagemCod;

	public ConversorStrategy getConversorStrategy() {
		switch (linguagemCod) {
		case 1:
			return ConversorStrategy.C;
		case 2:
			return ConversorStrategy.Cpp;
		case 3:
			return ConversorStrategy.PASCAL;
		case 4:
			return ConversorStrategy.JAVA;
		case 5:
			return ConversorStrategy.PYTHON;
		}
		return null;
	}
	
	public Linguagens getLinguagem() {
		switch (linguagemCod) {
		case 1:
			return Linguagens.C;
		case 2:
			return Linguagens.Cpp;
		case 3:
			return Linguagens.PASCAL;
		case 4:
			return Linguagens.JAVA;
		case 5:
			return Linguagens.PYTHON;
		}
		return null;
	}

	public void setLinguagemCod(int linguagem) {
		this.linguagemCod = linguagem;
	}

	public int getLinguagemCod() {
		return linguagemCod;
	}

}
