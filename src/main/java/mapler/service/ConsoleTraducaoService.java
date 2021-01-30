package mapler.service;

import java.io.IOException;
import org.fxmisc.richtext.StyleClassedTextArea;
import conversores.ConversorStrategy;
import debug.Debugador;
import debug.EventoListener;
import debug.GerenciadorEventos;
import debug.TiposEvento;
import javafx.application.Platform;
import main.Principal;
import mapler.model.ConsoleStyleClassedTextArea;
import mapler.model.EspectadorInputConsole;
import mapler.util.StringUtil;
import modelos.LeitorEntradaConsole;
import modelos.ParserError;
import modelos.RuntimeError;

/**
 * Intermediaria entre a IDE (console e traducao) e o Interpretador
 */
public class ConsoleTraducaoService implements EventoListener, EspectadorInputConsole {

  private Principal principal;
  private GerenciadorEventos gerenciadorEventos;
  private Debugador debugador;
  LeitorEntradaConsole leitor;
  private ConsoleStyleClassedTextArea consoleTextArea;
  private StyleClassedTextArea areaTraducao;

  public ConsoleTraducaoService(StyleClassedTextArea areaConsole, StyleClassedTextArea areaTraducao) {
    this.consoleTextArea = new ConsoleStyleClassedTextArea(areaConsole, this);
    this.areaTraducao = areaTraducao;
    this.gerenciadorEventos = new GerenciadorEventos();
    // EVENTOS DO INTERPRETADOR QUE QUEREMOS OBSERVAR
    this.gerenciadorEventos.inscrever(TiposEvento.ESCREVER_EVENTO, this);
    this.gerenciadorEventos.inscrever(TiposEvento.LER_EVENTO, this);
    this.gerenciadorEventos.inscrever(TiposEvento.ERRO_PARSE, this);
    this.gerenciadorEventos.inscrever(TiposEvento.ERRO_RUNTIME, this);

    this.debugador = new Debugador(gerenciadorEventos, false);
    this.principal = new Principal(gerenciadorEventos, debugador);
  }

  /**
   * Deve ser chamado quando o console for destruido. Remove inscricoes do Gerenciador de Eventos.
   */
  public void fechar() {
    this.gerenciadorEventos.desinscrever(TiposEvento.ESCREVER_EVENTO, this);
    this.gerenciadorEventos.desinscrever(TiposEvento.LER_EVENTO, this);
    this.gerenciadorEventos.desinscrever(TiposEvento.ERRO_PARSE, this);
    this.gerenciadorEventos.desinscrever(TiposEvento.ERRO_RUNTIME, this);
  }
  
  public void executar(String pathFile) {
    // TODO: logica de salvar e talz... tlvz deve ficar no codigoController antes de chamar esse metodo
    try {
      this.principal.runFile(pathFile);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public void setTraducao(String pathFile, ConversorStrategy tipoConversao) {
    String traducao = null;
    try {
      traducao = this.principal.getConversao(pathFile, tipoConversao);
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (traducao != null && !traducao.isEmpty()) {
      this.areaTraducao.clear();
      this.areaTraducao.appendText(traducao);
    }
  }

  @Override
  public void notificarInput(String input) {
    this.leitor.setValor(input); // informa o interpretador
  }

  @Override
  public void update(TiposEvento tipoEvento, Object payload) {
    switch (tipoEvento) {
      case ESCREVER_EVENTO:
        String msg = StringUtil.getStringUtf8((String) payload);
        // Platform usado pois ha chamada da area_console dentro do interpretador que não faz parte do
        // sistema FX
        Platform.runLater(() -> {
          consoleTextArea.imprimirMsg(msg);
        });
        return;
      case LER_EVENTO:
        this.leitor = (LeitorEntradaConsole) payload;
        Platform.runLater(() -> {
          this.consoleTextArea.solicitarInputUsuario();
        });
        return;
      case ERRO_PARSE:
        ParserError parserErro = (ParserError) payload;
        Platform.runLater(() -> {
          consoleTextArea.imprimirErro(parserErro.linha, parserErro.getLexeme(), parserErro.mensagem);
        });
        return;
      case ERRO_RUNTIME:
        RuntimeError runtimeErro = (RuntimeError) payload;
        Platform.runLater(() -> {
          consoleTextArea.imprimirErro(runtimeErro.getLinha(), runtimeErro.getLexeme(), runtimeErro.getMessage());
        });
        return;
      default:
        // ignora outros eventos
        return;
    }
  }
}
