package mapler.model.highlight;

import org.fxmisc.richtext.StyleClassedTextArea;

public class PortugolHighlight extends SyntaxHighlighter {

	public PortugolHighlight(StyleClassedTextArea codeArea) {
		super(codeArea);

	}

	@Override
	public String[] initKeywords() {

		return new String[] { "variaveis", "inicio", "fim", "ler", "escrever", "se", "entao", "senao", "e",
				"verdadeiro", "falso", "caso", "ou", "nao", "faca", "inicio", "enquanto", "para", "de", "repita", "ate",
				"passo" };

	}

	@Override
	public String[] initTypes() {
		return new String[] { "real", "inteiro", "logico", "cadeia", "caractere", "vetor", "modulo" };
	}
}
