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
public class ConsoleStyleClassedTextArea extends StyleClassedTextArea {

  private Boolean esperandoInputUsuario = false; // informa se o console esta esperando input do usuário
  private EspectadorInputConsole espectador; // quem vai ser notificado quando o usuario realizar input

  public ConsoleStyleClassedTextArea() {
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
    String texto = getText().trim();
    String[] linhas = texto.split("\n");
    String ultimaLinha = linhas[linhas.length - 1].trim();
    String valor = ultimaLinha.substring(1); // remove o '>'

    this.espectador.notificarInput(valor);// notifica o espectador do input

    this.esperandoInputUsuario = false;
    this.setEditable(false);
  }

  public void imprimirMsgComQuebraLinha(String text) {
    imprimirMsg(text + "\n");
  }

  /**
   * Imprime uma mensagem no console
   * 
   * @param text
   */
  public void imprimirMsg(String text) {
    
    appendText(text);
    setStyleClass(0, getText().length(), "normal");
   // setStyleClass(getText().length(), getText().length(), "variaveis");
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
    setStyleClass(0, getText().length(), "erro");
  }

  public void limparConsole() {
    //this.setEditable(true);
    //((StyleClassedTextArea)this).replaceText("");
    //this.setEditable(false);
    if(getLength() > 0)
    super.replaceText(0, getLength(), "");
    
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

  public void setEspectador(EspectadorInputConsole espectador) {

    this.espectador = espectador;
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