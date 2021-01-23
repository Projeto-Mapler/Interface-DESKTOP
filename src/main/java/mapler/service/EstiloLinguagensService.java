package mapler.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import org.fxmisc.richtext.StyleClassedTextArea;
import mapler.model.Linguagem;
import mapler.model.LinguagemC;
import mapler.model.LinguagemJava;
import mapler.model.Linguagens;

public class EstiloLinguagensService {

  private static EstiloLinguagensService instancia; // singleton
  private Map<Linguagens, Linguagem> linguagens;


  private EstiloLinguagensService() {
    linguagens = new HashMap<Linguagens, Linguagem>();
    linguagens.put(Linguagens.C, new LinguagemC());
    linguagens.put(Linguagens.JAVA, new LinguagemJava());
  }

  public static EstiloLinguagensService getInstancia() {
    if (instancia == null)
      instancia = new EstiloLinguagensService();
    return instancia;
  }


  public StyleClassedTextArea setLinguagem(Linguagens lgn, StyleClassedTextArea area) {
    Linguagem linguagem = null;
    switch (lgn) {
      case C:
      case JAVA:
        linguagem = linguagens.get(lgn);
        break;
      default:
        return area;
    }

    area.setStyleClass(0, area.getText().length(), "variaveis");

    Iterator it = linguagem.getReservadas().iterator();
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

    it = linguagem.getTipos().iterator();
    while (it.hasNext()) {
      String str = it.next().toString();
      Iterator e = getIndices(str, texto).iterator();
      while (e.hasNext()) {
        in = Integer.parseInt(e.next().toString());
        area.setStyleClass(in, in + str.length(), "tipos");
      }
    }

    it = linguagem.getConstantes().iterator();
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
}
