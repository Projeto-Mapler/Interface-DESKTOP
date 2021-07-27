package mapler.model.highlight;

import org.fxmisc.richtext.StyleClassedTextArea;

public class CppHighlight extends SyntaxHighlighter {

	public CppHighlight(StyleClassedTextArea codeArea) {
		super(codeArea);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String[] initKeywords() {
		return new String[] { "break", "case", "continue", "default", "do", "else", "for", "goto", "if", "return",
				"switch", "while", "true", "false", "include", "strcpy", "main", "cout", "cin", "using",
				"namespace" };
	}

	@Override
	public String[] initTypes() {
		return new String[] { "bool", "boolean", "byte", "char", "double", "float", "int", "long", "short", "void",
				"string" };
	}

}
