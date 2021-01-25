package mapler.model.linguagem;

import java.util.HashSet;

public class LinguagemJava extends Linguagem {

  public LinguagemJava() {
    super();
  }

  @Override
  protected void iniciaTipos() {

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
}
