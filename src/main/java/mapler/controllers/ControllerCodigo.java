package mapler.controllers;

import java.io.FileReader;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.StyleClassedTextArea;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;
import mapler.Arquivo;
import mapler.FXMaster;

public class ControllerCodigo implements Initializable {

  @FXML
  TabPane tabp_pai, tabp_filho;

  @FXML
  Tab tab_cod, tab_traducao, tab_terminal;

  @FXML
  StyleClassedTextArea area_cod, area_terminal, area_traducao;


  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    // TODO Auto-generated method stub

    if (Arquivo.abrir) {
      Arquivo.abrir = false;
      try {
        Scanner scanner = new Scanner(new FileReader(Arquivo.arquivo.getPath().toString()));
        scanner.useDelimiter("\n");
        String str = "";
        while (scanner.hasNext())
          str = str + scanner.next() + "\n";
        area_cod.deleteText(0, area_cod.getText().length());
        area_cod.appendText(str);
      } catch (Exception es) {
        es.printStackTrace();
      }

    }

    // de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons.k

    tabPane();
    areasStyle();
    controlesCodigo();

  }

  /*
   * Metodos padroes
   */

  public void setTraducao(String str, String lgn) {
    area_traducao.deleteText(0, area_traducao.getText().length());
    area_traducao.appendText(str);
    ControllerLinguagens.setLinguagem(lgn, area_traducao);
  }

  public String getPortugol() {
    return area_cod.getText();
  }

  /*
   * Metodos de Eventos e Estilos
   */


  private void tabPane() {
    tabp_pai.getStylesheets().add(FXMaster.tabPanePai());
    tabp_filho.getStylesheets().add(FXMaster.tabPaneFilho());
  }


  private void areasStyle() {
    area_traducao.setStyle(
        "-fx-font-size: 24; -fx-font-weight: bold; -fx-background-color: #5c6770; -fx-border-color: #5c6770;");
    area_traducao.getStylesheets().add(FXMaster.codigo());
    area_traducao.setParagraphGraphicFactory(LineNumberFactory.get(area_traducao));
    area_traducao.setWrapText(true);
    area_traducao.setLineHighlighterOn(false);
    area_traducao.setLineHighlighterFill(Paint.valueOf("#353535"));
    area_traducao.setEditable(false);
    // area_codigo = ControllerLinguagens.setLinguagem("C", area_traducao);
    // go to line -> area.displaceCaret(numeroLinha.length);

    area_terminal.setStyle(
        "-fx-font-size: 20; -fx-font-weight: bold; -fx-background-color: #5c6770; -fx-border-color: #5c6770;");
    area_terminal.getStylesheets().add(FXMaster.codigo());
    area_terminal.setWrapText(false);
    area_terminal.setLineHighlighterOn(false);

    area_cod.setStyle(
        "-fx-font-size: 24; -fx-font-weight: bold; -fx-background-color: #5c6770; -fx-border-color: #5c6770;");
    area_cod.getStylesheets().add(FXMaster.codigo());
    area_cod.setParagraphGraphicFactory(LineNumberFactory.get(area_cod));
    area_cod.setWrapText(true);
    area_cod.setLineHighlighterOn(false);
    area_cod.setLineHighlighterFill(Paint.valueOf("#353535"));
    area_cod.setStyleClass(0, 0, "variaveis");

  }



  private void controlesCodigo() {

    area_cod.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent ke) {

        if (ke.getText().equals(";") || ke.getText().equals(".") || ke.getText().equals("'")
            || ke.getCode().equals(KeyCode.ENTER) || ke.getCode().equals(KeyCode.BACK_SPACE)
            || ke.getCode().equals(KeyCode.DELETE) || ke.getCode().equals(KeyCode.SPACE)
            || ke.getCode().equals(KeyCode.TAB)) {
          int comecoDaLinha = area_cod.getText().lastIndexOf("\n", area_cod.getCaretPosition() - 1);
          int finalDaLinha = area_cod.getText().indexOf("\n", area_cod.getCaretPosition()); // se ==
                                                                                            // -1 �
                                                                                            // a
                                                                                            // ultima
          int comecoDaLinhaAnt = 0;

          if (finalDaLinha == -1 && comecoDaLinha != -1)
            area_cod = Portugol.colorirArea(area_cod, comecoDaLinha + 1);
          else if (finalDaLinha == -1 && comecoDaLinha == -1)
            area_cod = Portugol.colorirArea(area_cod, 0);
          else if (finalDaLinha != -1 && comecoDaLinha == -1)
            area_cod = Portugol.colorirArea(area_cod, 0, finalDaLinha);
          else
            area_cod = Portugol.colorirArea(area_cod, comecoDaLinha, finalDaLinha);

          if (comecoDaLinha > 1) {
            comecoDaLinhaAnt = area_cod.getText().lastIndexOf("\n", comecoDaLinha - 1);
            if (comecoDaLinhaAnt == -1)
              area_cod = Portugol.colorirArea(area_cod, 0, comecoDaLinha);
            else
              area_cod = Portugol.colorirArea(area_cod, comecoDaLinhaAnt, comecoDaLinha);
          }
        }
      }
    });


    /*
     * btn_debug.setOnAction(e -> {
     * 
     * });
     * 
     * btn_play.setOnAction(e -> { Interface(2); String caminho = this.getCaminhoArquivo();
     * console.executar(caminho); });
     * 
     * btn_save.setOnAction(e -> { Arquivo.salvarArquivo(Arquivo.arquivo, true,
     * ControllerCodigo.getPortugol()); });
     */

  }



  private String getCaminhoArquivo() {
    if (Arquivo.arquivo == null) {
      Arquivo.salvarArquivo(Arquivo.arquivo, true, getPortugol());
    }
    return Arquivo.arquivo.getAbsolutePath();
  }


}
