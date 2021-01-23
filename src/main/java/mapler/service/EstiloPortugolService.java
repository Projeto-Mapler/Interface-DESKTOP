package mapler.service;

import java.util.Arrays;
import org.fxmisc.richtext.StyleClassedTextArea;

public class EstiloPortugolService {
  
  public static StyleClassedTextArea colorirArea(StyleClassedTextArea area, int in, int out) {
    String texto = "";
    try {
      texto = area.getText().replace("\t", " ").replace(";", " ").substring(in, out);
      if (texto.indexOf("\n") == 0)
        in++;
      texto = area.getText().replace("\t", " ").replace(";", " ").substring(in, out);

    } catch (Exception e) {
      System.out.println(
          "subs falhou para " + in + " e " + out + " em " + area.getText().replace("\t", " "));
      return area;
    }

    String linha[] = texto.split(" ", -1);

    try {
      area.setStyleClass(in, out, "variaveis");
    } catch (Exception e) {
      System.out.println(
          "pintar falhou para " + in + " e " + out + " em " + area.getText().replace("\t", " "));
      return area;
    }
    for (int loop = 0; loop < linha.length; loop++) {
      if (Arrays.asList(EstiloService.getReservadas()).contains(linha[loop])
          || Arrays.asList(EstiloService.getReservadas()).contains("\n" + linha[loop])) { // verifica se
                                                                                     // eh reservada
        int i = texto.indexOf(linha[loop]);
        while (i >= 0) {
          area.setStyleClass(in + i, in + i + linha[loop].length(), "reservadas");
          i = texto.indexOf(linha[loop], i + 1);
        }
      } else if (Arrays.asList(EstiloService.getTipos()).contains(linha[loop])
          || Arrays.asList(EstiloService.getTipos()).contains("\n" + linha[loop])) { // verifica se eh
                                                                                // tipo
        int i = texto.indexOf(linha[loop]);
        while (i >= 0) {
          area.setStyleClass(in + i, in + i + linha[loop].length(), "tipos");
          i = texto.indexOf(linha[loop], i + 1);
        }
      } else if (EstiloService.isNumeric(linha[loop])
          || EstiloService.isNumeric(linha[loop].replace("\n", ""))) { // verificar se eh number
        int i = texto.indexOf(linha[loop]);
        while (i >= 0) {
          area.setStyleClass(in + i, in + i + linha[loop].length(), "constantes");
          i = texto.indexOf(linha[loop], i + 1);
        }
      }
    }

    if (texto.indexOf("\"") >= 0) {
      int inString = texto.indexOf("\"");
      int outString = texto.indexOf("\"", inString + 1);
      if (outString >= 0) {
        area.setStyleClass(in + inString, in + outString + 1, "texto");
      } else {
        area.setStyleClass(in + inString, in + inString, "texto");
      }
    }

    if (texto.indexOf("'") >= 0) {
      int inString = texto.indexOf("'");
      int outString = texto.indexOf("'", inString + 1);
      if (outString >= 0) {
        area.setStyleClass(in + inString, in + outString + 1, "texto");
      } else {
        area.setStyleClass(in + inString, in + inString, "texto");
      }
    }

    return area;
  }

  public static StyleClassedTextArea colorirArea(StyleClassedTextArea area, int in) {
    colorirArea(area, in, area.getText().length());

    return area;
  }

}
