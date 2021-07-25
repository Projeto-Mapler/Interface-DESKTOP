package mapler.service;

import org.fxmisc.richtext.StyleClassedTextArea;

import mapler.model.Linguagem;
import mapler.model.highlight.CHighlight;
import mapler.model.highlight.CppHighlight;
import mapler.model.highlight.JavaHighlight;
import mapler.model.highlight.PascalHighlight;
import mapler.model.highlight.PortugolHighlight;
import mapler.model.highlight.PythonHighlight;
import mapler.model.highlight.SyntaxHighlighter;

/**
 * Service para aplicar estilo css (colorir) os textos de código portugol e
 * traduzido para outras llinguagens.
 *
 */
public class EstiloLinguagensService {

	private static EstiloLinguagensService instancia; // singleton

	// Construtor
	private EstiloLinguagensService() {
	}

	public static EstiloLinguagensService getInstancia() {
		if (instancia == null)
			instancia = new EstiloLinguagensService();
		return instancia;
	}

	public SyntaxHighlighter setHighlighterLinguagem(StyleClassedTextArea area, Linguagem linguagem) {
		switch (linguagem) {
		case PORTUGOL:
			return new PortugolHighlight(area);
		case JAVA:
			return new JavaHighlight(area);
		case C:
			return new CHighlight(area);
		case Cpp:
			return new CppHighlight(area);
		case PASCAL:
			return new PascalHighlight(area);
		case PYTHON:
			return new PythonHighlight(area);
		default:
			return null;
		}

	}

}
