package mapler.model.highlight;

import org.fxmisc.richtext.StyleClassedTextArea;

public class JavaHighlight extends SyntaxHighlighter {

	public JavaHighlight(StyleClassedTextArea codeArea) {
		super(codeArea);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String[] initKeywords() {
		return new String[] { "abstract", "assert", "break", "case", "catch", "class", "const", "continue", "default",
				"do", "else", "enum", "extends", "final", "finally", "for", "goto", "if", "implements", "import",
				"instanceof", "interface", "native", "new", "package", "private", "protected", "public", "return",
				"static", "strictfp", "super", "switch", "synchronized", "this", "throw", "throws", "transient", "try",
				"volatile", "while", "true", "false" };
	}

	@Override
	public String[] initTypes() {
		return new String[] { "boolean", "byte", "char", "double", "float", "int", "long", "short", "void", "Boolean", "Interger", "String", "Double", "Scanner" };
	}

}
