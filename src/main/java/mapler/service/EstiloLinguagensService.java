package mapler.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import org.fxmisc.richtext.StyleClassedTextArea;
import mapler.model.Linguagem;
import mapler.model.LinguagemC;
import mapler.model.LinguagemJava;
import mapler.model.LinguagemPortugol;
import mapler.model.Linguagens;

public class EstiloLinguagensService {

  private static EstiloLinguagensService instancia; // singleton
  private Map<Linguagens, Linguagem> linguagens;

  // Construtor
  private EstiloLinguagensService() {
    linguagens = new HashMap<Linguagens, Linguagem>();
    linguagens.put(Linguagens.PORTUGOL, new LinguagemPortugol());
    linguagens.put(Linguagens.C, new LinguagemC());
    linguagens.put(Linguagens.JAVA, new LinguagemJava());
  }

  public static EstiloLinguagensService getInstancia() {
    if (instancia == null)
      instancia = new EstiloLinguagensService();
    return instancia;
  }

  public void setEstiloPortugol(StyleClassedTextArea area, int comecoLinha, int fimLinha) {
    Linguagem linguagem = linguagens.get(Linguagens.PORTUGOL);
    String texto = "";
    try {
      texto = area.getText().replace("\t", " ").replace(";", " ").substring(comecoLinha, fimLinha);
      if (texto.indexOf("\n") == 0)
        comecoLinha++;
      texto = area.getText().replace("\t", " ").replace(";", " ").substring(comecoLinha, fimLinha);

    } catch (Exception e) {
      System.out.println("subs falhou para " + comecoLinha + " e " + fimLinha + " em " + area.getText().replace("\t", " "));
      return;
    }

    try {
      area.setStyleClass(comecoLinha, fimLinha, "variaveis");
    } catch (Exception e) {
      System.out.println("pintar falhou para " + comecoLinha + " e " + fimLinha + " em " + area.getText().replace("\t", " "));
      return;
    }
    
    setEstiloColecao(linguagem.getReservadas(), area, texto, "reservadas", comecoLinha);
    setEstiloColecao(linguagem.getTipos(), area, texto, "tipos", comecoLinha);
    setEstiloColecao(linguagem.getConstantes(), area, texto, "constantes", comecoLinha);

    setEstiloString("\"", texto, area, "texto", comecoLinha);
    setEstiloString("'", texto, area, "texto", comecoLinha);
    
  }

  public void setEstiloTraducao(Linguagens lgn, StyleClassedTextArea area) {
    Linguagem linguagem = null;
    switch (lgn) {
      case C:
      case JAVA:
        linguagem = linguagens.get(lgn);
        break;
      default:
        return;
    }

    area.setStyleClass(0, area.getText().length(), "variaveis");
    String texto = area.getText();

    setEstiloColecao(linguagem.getReservadas(), area, texto, "reservadas", 0);
    setEstiloColecao(linguagem.getTipos(), area, texto, "tipos", 0);
    setEstiloColecao(linguagem.getConstantes(), area, texto, "constantes", 0);

    setEstiloString("\"", texto, area, "texto", 0);
    setEstiloString("'", texto, area, "texto", 0);

    area.setStyleClass(area.getText().length(), area.getText().length(), "variaveis");
  }

  /**
   * @param palavra
   * @param texto
   * @return - coleção com o index inicial de toda ocorrencia da palavra no texto
   */
  private HashSet<Integer> getIndexsInicio(String palavra, String texto) {
    HashSet<Integer> retorno = new HashSet<Integer>();
    int index = texto.toLowerCase().indexOf(palavra.toLowerCase());
    while (index != -1) {
      retorno.add(index);
      index = texto.toLowerCase().indexOf(palavra.toLowerCase(), index + 1);
    }
    return retorno;
  }

  /**
   * Seta o estilo para tipos de palavras da linguagem
   * 
   * @param colecao - coleção com as palavras daquele tipo
   * @param area - textArea do editor
   * @param texto - texto no textArea
   * @param styleClass - class CSS que deve ser aplicada
   * @param comecoDoTexto - index do inicio do texto, usado para aplicar estilo apenas em uma parte do texto | 0 default
   * 
   */
  private void setEstiloColecao(HashSet<String> colecao, StyleClassedTextArea area, String texto, String styleClass, int comecoDoTexto) {
    Iterator<String> palavras = colecao.iterator();
    int indexInicioPalavra;
    while (palavras.hasNext()) {
      String palavra = palavras.next();
      Iterator<Integer> indexs = getIndexsInicio(palavra, texto).iterator();
      while (indexs.hasNext()) {
        indexInicioPalavra = indexs.next() + comecoDoTexto;
        area.setStyleClass(indexInicioPalavra, indexInicioPalavra + palavra.length(), styleClass);
      }
    }
  }

  /**
   * Seta o estilo de string no codigo ex: "hello", 'c' ...
   * 
   * @param delimitador - palavra ou simbolo que delimita a string no pseudocodigo
   * @param texto - texto no textArea
   * @param area - textArea do editor
   * @param styleClass - class CSS que deve ser aplicada
   * @param comecoDoTexto - index do inicio do texto, usado para aplicar estilo apenas em uma parte do texto | 0 default
   */
  private void setEstiloString(String delimitador, String texto, StyleClassedTextArea area, String styleClass, int comecoDoTexto) {
    int in = texto.toLowerCase().indexOf(delimitador);
    int out = texto.toLowerCase().indexOf(delimitador, in + 1);
    while (in != -1 && out != -1) {
      area.setStyleClass(in + comecoDoTexto, out + comecoDoTexto + 1, styleClass);
      in = texto.toLowerCase().indexOf(delimitador, out + 1);
      out = texto.toLowerCase().indexOf(delimitador, in + 1);
    }
  }
}
