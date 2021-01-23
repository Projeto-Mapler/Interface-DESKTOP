package mapler.util;

import java.nio.charset.Charset;

public class StringUtil {
  // SUPORTE PARA UTF-8 no area console
  private static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
  private static final Charset UTF_8 = Charset.forName("UTF-8");

  private StringUtil() {
    // TODO Auto-generated constructor stub
  }

  /**
   * Retorna a string no formato UTF-8
   * 
   * @param string - string a ser convertida
   * @return - string convertida para utf-8
   */
  public static String getStringUtf8(String string) {
    byte[] ptext = string.getBytes(ISO_8859_1);
    return new String(ptext, UTF_8);
  }
}
