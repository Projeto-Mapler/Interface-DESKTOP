package resources.bibliotecas;

import java.io.IOException;
import java.nio.charset.Charset;

import org.fxmisc.richtext.StyleClassedTextArea;

import conversores.ConversorStrategy;
import debug.Debugador;
import debug.EventoListener;
import debug.GerenciadorEventos;
import debug.TiposEvento;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import main.Principal;
import modelos.LeitorEntradaConsole;
import modelos.ParserError;
import modelos.RuntimeError;

public class Console extends StyleClassedTextArea implements EventoListener {

    // SUPORTE PARA UTF-8 no area console
    public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
    public static final Charset UTF_8 = Charset.forName("UTF-8");

    private Principal principal;
    private GerenciadorEventos ge;

    private Boolean modoLeitura = false;
    LeitorEntradaConsole leitor;

    public Console() {
	setOnKeyPressed(new EventHandler<KeyEvent>() {

	    @Override
	    public void handle(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER && modoLeitura) {
		    lerDado();
		}

	    }
	});
    }

    @Override
    public void replaceText(int start, int end, String text) {
	String current = getText();
	// only insert if no new lines after insert position:
	if (!current.substring(start).contains("\n")) {
	    super.replaceText(start, end, text);
	}
    }

    @Override
    public void replaceSelection(String text) {
	String current = getText();
	int selectionStart = getSelection().getStart();
	if (!current.substring(selectionStart).contains("\n")) {
	    super.replaceSelection(text);
	}
    }

    @Override
    public void update(TiposEvento tipoEvento, Object payload) {

	switch (tipoEvento) {
	case ESCREVER_EVENTO:
	    // converte a string para UTF-8:
	    // cast normal de object nao funciona
	    byte[] ptext = ((String) payload).getBytes(ISO_8859_1);
	    String msg = new String(ptext, UTF_8);

	    // Platform usado pois ha chamada da area_console dentro do interpretador
	    // que não faz parte do sistema FX
	    Platform.runLater(() -> {
		imprimirConsoleQuebraLinha(msg);
	    });

	    return;
	case LER_EVENTO:
	    this.modoLeitura = true;
	    this.leitor = (LeitorEntradaConsole) payload;
	    this.setEditable(true);
	    Platform.runLater(() -> {
		imprimirConsole(">");
	    });
	    return;
	case ERRO_PARSE:
	    ParserError parserErro = (ParserError) payload;

	    Platform.runLater(() -> {
		imprimirErro(parserErro.linha, parserErro.getLexeme(), parserErro.mensagem);
	    });

	    return;
	case ERRO_RUNTIME:
	    RuntimeError runtimeErro = (RuntimeError) payload;

	    Platform.runLater(() -> {
		imprimirErro(runtimeErro.getLinha(), runtimeErro.getLexeme(), runtimeErro.getMessage());
	    });
	    return;

	default:
	    return;
	}

    }
    
    /**
     * Configura a classe principal do Interpretador.
     * Realiza inscrição nos eventos do Interpretador.
     * @param ge - Gerenciador de Eventos
     * @param debugador - Debugador do arquivo
     */
    public void setPrincipal(GerenciadorEventos ge, Debugador debugador) {
	this.ge = ge;
	this.ge.inscrever(TiposEvento.ESCREVER_EVENTO, this);
	this.ge.inscrever(TiposEvento.LER_EVENTO, this);
	this.ge.inscrever(TiposEvento.ERRO_PARSE, this);
	this.ge.inscrever(TiposEvento.ERRO_RUNTIME, this);
	this.principal = new Principal(ge, debugador);

    }
    /**
     * Deve ser chamado quando o console for destruido.
     * Remove inscrições do Gerenciador de Eventos.
     */
    public void fechar() {
	this.ge.desinscrever(TiposEvento.ESCREVER_EVENTO, this);
	this.ge.desinscrever(TiposEvento.LER_EVENTO, this);
	this.ge.desinscrever(TiposEvento.ERRO_PARSE, this);
	this.ge.desinscrever(TiposEvento.ERRO_RUNTIME, this);
    }

    
    /**
     * Dispara o processo de interpretação
     * @param pathFile - caminho do arquivo para ser interpretado
     */
    public void executar(String pathFile) {
	this.clear();
	setEditable(false);

	try {
	    this.principal.runFile(pathFile);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
    
    public String getTraducao(String pathFile, ConversorStrategy tipoConversao) {
	String retorno = null;
	try {
	    retorno =  this.principal.getConversao(pathFile, tipoConversao);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return retorno;
    }
    
    
    /**
     * Pega o valor informado pelo usuario e repassa para o interpretador.
     */
    private void lerDado() {
	String texto = getText();
	String[] linhas = texto.split("\n");
	String ultimaLinha = linhas[linhas.length - 1];
	String valor = ultimaLinha.substring(1); // remove o '>'
	leitor.setValor(valor);
	this.modoLeitura = false;
	this.setEditable(false);
    }

    
    private void imprimirErro(int linha, String onde, String msg) {
	if (onde == null) {
	    imprimirConsoleQuebraLinha("> Erro: linha " + linha + ". " + msg);
	} else {
	    imprimirConsoleQuebraLinha("> Erro: linha " + linha + ", em '" + onde + "'. " + msg);
	}
    }
    private void imprimirConsole(String text) {
	setStyleClass(getText().length(), getText().length(), "texto");
	appendText(text);
	setStyleClass(getText().length(), getText().length(), "variaveis");
    }
    private void imprimirConsoleQuebraLinha(String text) {
	imprimirConsole(text + "\n");
    }
}
