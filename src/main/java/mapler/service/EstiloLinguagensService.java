package mapler.service;

import java.util.HashSet;
import java.util.Iterator;
import org.fxmisc.richtext.StyleClassedTextArea;

public class EstiloLinguagensService {

  private static String linguagem = "";
  private static HashSet<String> reservadas, tipos, constantes;

  public static StyleClassedTextArea setLinguagem(String lgn, StyleClassedTextArea area) {

    if (lgn.equals("C")) {
      setC();
    } else if (lgn.equals("Java")) {
      setJava();
    } else {
      return area;
    }

    area.setStyleClass(0, area.getText().length(), "variaveis");

    Iterator it = reservadas.iterator();
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

    it = tipos.iterator();
    while (it.hasNext()) {
      String str = it.next().toString();
      Iterator e = getIndices(str, texto).iterator();
      while (e.hasNext()) {
        in = Integer.parseInt(e.next().toString());
        area.setStyleClass(in, in + str.length(), "tipos");
      }
    }

    it = constantes.iterator();
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

  private static void setC() {
    if (linguagem.equals("C"))
      return;

    linguagem = "C";

    reservadas = new HashSet<String>();
    reservadas.add("asm");
    reservadas.add("auto");
    reservadas.add("break");
    reservadas.add("case");
    reservadas.add("const");
    reservadas.add("continue");
    reservadas.add("default");
    reservadas.add("do");
    reservadas.add("else");
    reservadas.add("enum");
    reservadas.add("extern");
    reservadas.add("for");
    reservadas.add("goto");
    reservadas.add("if");
    reservadas.add("main");
    reservadas.add("printf");
    reservadas.add("scanf");
    reservadas.add("register");
    reservadas.add("return");
    reservadas.add("signed");
    reservadas.add("sizeof");
    reservadas.add("static");
    reservadas.add("struct");
    reservadas.add("switch");
    reservadas.add("typedef");
    reservadas.add("union");
    reservadas.add("unsigned");
    reservadas.add("volatile");
    reservadas.add("while");
    reservadas.add("#include");

    tipos = new HashSet<String>();
    tipos.add("char ");
    tipos.add("double ");
    tipos.add("float ");
    tipos.add("int ");
    tipos.add("long ");
    tipos.add("short ");
    tipos.add("void ");
    tipos.add("{");
    tipos.add("}");
    tipos.add("(");
    tipos.add(")");
    tipos.add(">");
    tipos.add("<");
    tipos.add("+");
    tipos.add("=");
    tipos.add("-");
    tipos.add("*");
    tipos.add("/");
    tipos.add(";");

    constantes = new HashSet<String>();
    constantes.add("1");
    constantes.add("2");
    constantes.add("3");
    constantes.add("4");
    constantes.add("5");
    constantes.add("6");
    constantes.add("7");
    constantes.add("8");
    constantes.add("9");
    constantes.add("0");

  }

  private static void setJava() {

    if (linguagem.equals("Java"))
      return;

    linguagem = "Java";

    reservadas = new HashSet<String>();
    reservadas.add("private");
    reservadas.add("protected");
    reservadas.add("public");
    reservadas.add("main");
    reservadas.add("abstract");
    reservadas.add("class");
    reservadas.add("extends");
    reservadas.add("final");
    reservadas.add("implements");
    reservadas.add("interface");
    reservadas.add("native");
    reservadas.add("new");
    reservadas.add("static");
    reservadas.add("strictfp");
    reservadas.add("synchronized");
    reservadas.add("transient");
    reservadas.add("volatile");
    reservadas.add("break");
    reservadas.add("case");
    reservadas.add("continue");
    reservadas.add("default");
    reservadas.add("do");
    reservadas.add("else");
    reservadas.add("for");
    reservadas.add("if");
    reservadas.add("instanceof");
    reservadas.add("return");
    reservadas.add("switch");
    reservadas.add("while");
    reservadas.add("assert");
    reservadas.add("catch");
    reservadas.add("finally");
    reservadas.add("throw");
    reservadas.add("throws");
    reservadas.add("try");
    reservadas.add("import");
    reservadas.add("package");
    reservadas.add("super");
    reservadas.add("this");
    reservadas.add("const");
    reservadas.add("goto");

    tipos = new HashSet<String>();
    tipos.add("boolean ");
    tipos.add("byte ");
    tipos.add("char ");
    tipos.add("double ");
    tipos.add("float ");
    tipos.add("int ");
    tipos.add("long ");
    tipos.add("short ");
    tipos.add("String ");
    tipos.add("void ");
    tipos.add("{");
    tipos.add("}");
    tipos.add("(");
    tipos.add(")");
    tipos.add(">");
    tipos.add("<");
    tipos.add("+");
    tipos.add("=");
    tipos.add("-");
    tipos.add("*");
    tipos.add("/");
    tipos.add(";");

    constantes = new HashSet<String>();
    constantes.add("1");
    constantes.add("2");
    constantes.add("3");
    constantes.add("4");
    constantes.add("5");
    constantes.add("6");
    constantes.add("7");
    constantes.add("8");
    constantes.add("9");
    constantes.add("0");

  }

  private static void setPython() {

  }

  private static void setPascal() {

  }


}
