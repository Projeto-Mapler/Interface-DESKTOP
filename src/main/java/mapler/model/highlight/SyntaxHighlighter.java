package mapler.model.highlight;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.fxmisc.richtext.StyleClassedTextArea;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.reactfx.Subscription;

import javafx.concurrent.Task;

public abstract class SyntaxHighlighter {

	private final String[] KEYWORDS, TYPES;
	private final Pattern PATTERN;

	private final String TYPE_PATTERN;
	private final String KEYWORD_PATTERN;
	private final String PAREN_PATTERN = "\\(|\\)";
	private final String BRACE_PATTERN = "\\{|\\}";
	private final String BRACKET_PATTERN = "\\[|\\]";
	private final String ATR_PATTERN = "\\<-"; // <-
	private final String INTER_PATTEN = "\\.{2}"; // ..
	private final String ATR_TYPE_PATTERN = "\\:";// : 
	private final String SEMICOLON_PATTERN = "\\;";
	private final String STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"";
	private final String COMMENT_PATTERN = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/";

	private ExecutorService executor; // para thread
	private StyleClassedTextArea codeArea;
	private Subscription cleanupWhenDone;

	public SyntaxHighlighter(StyleClassedTextArea codeArea) {
		this.KEYWORDS = initKeywords();
		this.TYPES = initTypes();

		TYPE_PATTERN = "\\b(" + String.join("|", TYPES) + ")\\b";
		KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
		// (?<GRUPO> + tokens ) + | (?<....
		PATTERN = Pattern.compile(
						"(?<TYPE>" + TYPE_PATTERN + ")" + 
						"|(?<KEYWORD>" + KEYWORD_PATTERN + ")"+ 	
						"|(?<INTER>" + INTER_PATTEN + ")" + 
						"|(?<PAREN>" + PAREN_PATTERN + ")" + 
						"|(?<ATR>" + ATR_PATTERN + ")" + 
						"|(?<ATRTYPE>" + ATR_TYPE_PATTERN + ")" + 
						"|(?<BRACE>" + BRACE_PATTERN + ")" + 
						"|(?<BRACKET>"	+ BRACKET_PATTERN + ")" + 
						"|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")" + 
						"|(?<STRING>" + STRING_PATTERN + ")" + 
						"|(?<COMMENT>" + COMMENT_PATTERN + ")"
						);

		this.codeArea = codeArea;
		executor = Executors.newSingleThreadExecutor();

		init();
	}

	/**
	 * 
	 * @return Deve retornar todas as palavras chave da linguagem alvo
	 */
	public abstract String[] initKeywords();

	/**
	 * 
	 * return Deve retornar todos os tipos primitivos da linguagem alvo
	 */
	public abstract String[] initTypes();

	public void stop() {
		cleanupWhenDone.unsubscribe();
		executor.shutdown();
	}

	private void init() {
		this.cleanupWhenDone = codeArea.multiPlainChanges().successionEnds(Duration.ofMillis(200)) // espera para
																									// colorir
				.supplyTask(this::computeHighlightingAsync).awaitLatest(codeArea.multiPlainChanges()).filterMap(t -> {
					if (t.isSuccess()) {
						return Optional.of(t.get());
					} else {
						t.getFailure().printStackTrace();
						return Optional.empty();
					}
				}).subscribe(this::applyHighlighting);
	}

	private Task<StyleSpans<Collection<String>>> computeHighlightingAsync() {
		String text = codeArea.getText();
		Task<StyleSpans<Collection<String>>> task = new Task<StyleSpans<Collection<String>>>() {
			@Override
			protected StyleSpans<Collection<String>> call() throws Exception {
				return computeHighlighting(text);
			}
		};
		executor.execute(task);
		return task;
	}

	private void applyHighlighting(StyleSpans<Collection<String>> highlighting) {
		codeArea.setStyleSpans(0, highlighting);
	}

	private StyleSpans<Collection<String>> computeHighlighting(String text) {
		Matcher matcher = PATTERN.matcher(text);
		int lastKwEnd = 0;
		StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
		while (matcher.find()) {
			// procura a qual grupo a palavra pertence
			// e atribui a classe css correta
			String styleClass = matcher.group("KEYWORD") != null ? "keyword"
					: matcher.group("PAREN") != null ? "paren"
							: matcher.group("BRACE") != null ? "brace"
									: matcher.group("BRACKET") != null ? "bracket"
											: matcher.group("SEMICOLON") != null ? "semicolon"
													: matcher.group("ATR") != null ? "atr"
															: matcher.group("ATRTYPE") != null ? "atr"
																	: matcher.group("INTER") != null ? "atr"
																			: matcher.group("STRING") != null ? "string"
																					: matcher.group("COMMENT") != null
																							? "comment"
																							: matcher.group(
																									"TYPE") != null
																											? "type"
																											: null;
			/* never happens */ assert styleClass != null;
			spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
			spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
			lastKwEnd = matcher.end();
		}
		spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
		return spansBuilder.create();
	}

}
