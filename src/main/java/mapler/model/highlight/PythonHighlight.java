package mapler.model.highlight;

import org.fxmisc.richtext.StyleClassedTextArea;

public class PythonHighlight extends SyntaxHighlighter {

	public PythonHighlight(StyleClassedTextArea codeArea) {
		super(codeArea);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String[] initKeywords() {
		return new String[] {  "break", "case", "continue", "not",
				"do", "else", "for", "goto", "if",  "return", "print", "input", "def",
				 "switch", "while", "true", "false", "or", "and"};
	}

	@Override
	public String[] initTypes() {
		return new String[] { "bool", "list", "bytes", "float", "int", "str", "None"};
	}

}
