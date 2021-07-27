package mapler.model.highlight;

import org.fxmisc.richtext.StyleClassedTextArea;

public class CHighlight extends SyntaxHighlighter {

	public CHighlight(StyleClassedTextArea codeArea) {
		super(codeArea);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String[] initKeywords() {
		return new String[] {  "break", "case", "continue", "default",
				"do", "else", "for", "goto", "if",  "return", "printf", "gets", "scanf",
				 "switch", "while", "true", "false","include" , "strcpy", "main"};
	}

	@Override
	public String[] initTypes() {
		return new String[] { "bool", "boolean", "byte", "char", "double", "float", "int", "long", "short", "void"};
	}

}
