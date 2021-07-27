package mapler.model.highlight;

import org.fxmisc.richtext.StyleClassedTextArea;

public class PascalHighlight extends SyntaxHighlighter {

	public PascalHighlight(StyleClassedTextArea codeArea) {
		super(codeArea);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String[] initKeywords() {
		return new String[] { "break", "case", "continue", "default", "then", "end", "begin", "readln", "to", "repeat",
				"until", "procedure", "program", "var", "do", "else", "for", "goto", "if", "return", "or", "and", "not",
				"switch", "while", "true", "false", "of", "write" };
	}

	@Override
	public String[] initTypes() {
		return new String[] { "boolean", "char", "real", "integer", "string", "void" };
	}

}
