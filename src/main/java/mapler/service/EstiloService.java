package mapler.service;

import mapler.util.CarregadorRecursos;

/**
 * Classe responsavel pelo design do FXML como CSS e Animações e Estilos
 *
 */
public class EstiloService {

  public static String[] getReservadas() {
    String[] reservadas = {"variaveis", "inicio", "fim", "ler", "escrever", "se", "entao", "senao",
        "e", "verdadeiro", "falso", "caso", "ou", "nao", "faca", "inicio", "enquanto", "para", "de",
        "repita", "ate", "passo"};
    return reservadas;
  }

  public static String[] getTipos() {
    String[] tipos = {"real", "cadeia", "inteiro", "logico;", "vetor", "caractere", "{", "}", "(",
        ")", ">", "<", "+", "=", "-", "*", "/"};
    return tipos;
  }

  public static boolean isNumeric(String strNum) {
    if (strNum == null) {
      return false;
    }
    try {
      double d = Double.parseDouble(strNum);
    } catch (NumberFormatException nfe) {
      return false;
    }
    return true;
  }

  public static String menuBar() {
    return CarregadorRecursos.getResourceExternalForm("css/menubar.css");
  }

  public static String tabPanePai() {
    return CarregadorRecursos.getResourceExternalForm("css/tabP.css");
  }

  public static String tabPaneFilho() {
    return CarregadorRecursos.getResourceExternalForm("css/tabF.css");
  }

  public static String splitPane() {
    return CarregadorRecursos.getResourceExternalForm("css/splitpane.css");
  }

  public static String codigo() {
    return CarregadorRecursos.getResourceExternalForm("css/txt.css");
  }

  public static String alerta() {
    return CarregadorRecursos.getResourceExternalForm("css/alertas.css");
  }
}
