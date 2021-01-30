package mapler.model;

import org.fxmisc.richtext.StyleClassedTextArea;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * StyleClassedTextArea com comportamento alterado para as necessidades do console Mapler
 * 
 * @author Kerlyson
 *
 */
public class ConsoleStyleClassedTextArea extends StyleClassedTextAreaWrapper {

  private Boolean esperandoInputUsuario = false; // informa se o console esta esperando input do usuário
  private EspectadorInputConsole espectador; // quem vai ser notificado quando o usuario realizar input

  public ConsoleStyleClassedTextArea(StyleClassedTextArea areaConsoleOriginal, EspectadorInputConsole espectador) {
    super(areaConsoleOriginal); // salva a referencia do original para funcionar normalmente
    this.espectador = espectador;
    setEditable(false);

    // Evento de input:
    setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        // quando o usuario realizar um input com enter:
        if (event.getCode() == KeyCode.ENTER && esperandoInputUsuario) {
          lerInputUsuario(); // ler o valor
        }
      }
    });
  }

  private void lerInputUsuario() {
    String texto = getText();
    String[] linhas = texto.split("\n");
    String ultimaLinha = linhas[linhas.length - 1];
    String valor = ultimaLinha.substring(1); // remove o '>'

    this.espectador.notificarInput(valor);// notifica o espectador do input

    this.esperandoInputUsuario = false;
    this.setEditable(false);
  }

  private void imprimirMsgComQuebraLinha(String text) {
    imprimirMsg(text + "\n");
  }

  /**
   * Imprime uma mensagem no console
   * 
   * @param text
   */
  public void imprimirMsg(String text) {
    setStyleClass(getText().length(), getText().length(), "texto");
    appendText(text);
    setStyleClass(getText().length(), getText().length(), "variaveis");
  }

  /**
   * Imprime uma mensagem de erro no console
   * 
   * @param linha - numero da linha
   * @param onde - em qual token
   * @param msg - descrição do erro
   */
  public void imprimirErro(int linha, String onde, String msg) {
    if (onde == null) {
      imprimirMsgComQuebraLinha("> Erro: linha " + linha + ". " + msg);
    } else {
      imprimirMsgComQuebraLinha("> Erro: linha " + linha + ", em '" + onde + "'. " + msg);
    }
  }

  public void limparConsole() {
    clear();
  }

  public Boolean isEsperandoInputUsuario() {
    return this.esperandoInputUsuario;
  }

  public void solicitarInputUsuario() {
    if (!this.esperandoInputUsuario) {
      this.esperandoInputUsuario = true;
      setEditable(true);
      imprimirMsg(">");
    }
  }

  /* SOBRECARGAS PARA O USUÁRIO EDITAR APENAS A LINHA ATUAL DO CONSOLE */
  @Override
  public void replaceText(int start, int end, String text) {
    String current = getText();
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
  /* FIM SOBRECARGAS */
}
