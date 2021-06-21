package mapler.model.linguagem;

import java.util.HashSet;

public class EstiloLinguagemC extends EstiloLinguagem {

  public EstiloLinguagemC() {
    super();
  }

  @Override
  protected void iniciaTipos() {

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
}
