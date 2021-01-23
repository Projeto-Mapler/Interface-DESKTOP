package mapler.service;

import java.util.HashSet;
import java.util.Iterator;
import org.fxmisc.richtext.StyleClassedTextArea;
import mapler.model.Linguagem;
import mapler.model.LinguagemC;
import mapler.model.LinguagemJava;

public class EstiloLinguagensService {

  private static EstiloLinguagensService instancia; // singleton
  private Linguagem linguagemAtual;
  private Linguagem linguagens[] = {new LinguagemC(), // 0 - C
      new LinguagemJava() // 1 - JAVA
  };

  private EstiloLinguagensService() {}

  public static EstiloLinguagensService getInstancia() {
    if (instancia == null)
      instancia = new EstiloLinguagensService();
    return instancia;
  }


  public StyleClassedTextArea setLinguagem(String lgn, StyleClassedTextArea area) {

    if (lgn.equals("C")) {
      setC();
    } else if (lgn.equals("Java")) {
      setJava();
    } else {
      return area;
    }

    area.setStyleClass(0, area.getText().length(), "variaveis");

    Iterator it = linguagemAtual.getReservadas().iterator();
    int in;
    String texto = area.getText();
    while (it.hasNext()) {
      String str = it.next().toString();
      Iterator e = getIndices(str, texto).iterator();
      while (e.hasNext()) {
        in = Integer.parseInt(e.next().toString());
        area.setStyleClass(in, in + str.length(), "reservadas");
      }
    }

    it = linguagemAtual.getTipos().iterator();
    while (it.hasNext()) {
      String str = it.next().toString();
      Iterator e = getIndices(str, texto).iterator();
      while (e.hasNext()) {
        in = Integer.parseInt(e.next().toString());
        area.setStyleClass(in, in + str.length(), "tipos");
      }
    }

    it = linguagemAtual.getConstantes().iterator();
    while (it.hasNext()) {
      String str = it.next().toString();
      Iterator e = getIndices(str, texto).iterator();
      while (e.hasNext()) {
        in = Integer.parseInt(e.next().toString());
        area.setStyleClass(in, in + str.length(), "constantes");
      }
    }

    in = texto.toLowerCase().indexOf("\"");
    int out = texto.toLowerCase().indexOf("\"", in + 1);
    while (in != -1 && out != -1) {
      area.setStyleClass(in, out + 1, "texto");
      in = texto.toLowerCase().indexOf("\"", out + 1);
      out = texto.toLowerCase().indexOf("\"", in + 1);
    }

    in = texto.toLowerCase().indexOf("'");
    out = texto.toLowerCase().indexOf("'", in + 1);
    while (in != -1 && out != -1) {
      area.setStyleClass(in, out + 1, "texto");
      in = texto.toLowerCase().indexOf("'", out + 1);
      out = texto.toLowerCase().indexOf("'", in + 1);
    }
    area.setStyleClass(area.getText().length(), area.getText().length(), "variaveis");
    return area;
  }

  public static HashSet<String> getIndices(String palavra, String texto) {
    HashSet<String> i = new HashSet<String>();
    String txt = texto;
    int in = txt.toLowerCase().indexOf(palavra.toLowerCase());
    while (in != -1) {
      i.add("" + in);
      in = txt.toLowerCase().indexOf(palavra.toLowerCase(), in + 1);
    }
    return i;
  }

  private void setC() {
    if (linguagemAtual instanceof LinguagemC)
      return;
    linguagemAtual = linguagens[0]; // 0 - C
  }

  private void setJava() {
    if (linguagemAtual instanceof LinguagemJava)
      return;
    linguagemAtual = linguagens[1]; // 1 - Java
  }

  private void setPython() {

  }

  private void setPascal() {

  }


}
