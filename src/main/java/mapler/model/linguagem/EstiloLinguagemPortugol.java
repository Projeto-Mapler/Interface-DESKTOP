package mapler.model.linguagem;

import java.util.HashSet;

public class EstiloLinguagemPortugol extends EstiloLinguagem {

  public EstiloLinguagemPortugol() {
    super();
  }

  @Override
  protected void iniciaTipos() {

    reservadas = new HashSet<String>();
    reservadas.add("variaveis");
    reservadas.add("inicio");
    reservadas.add("fim");
    reservadas.add("ler");
    reservadas.add("escrever");
    reservadas.add("se");
    reservadas.add("entao");
    reservadas.add("senao");
    reservadas.add("e");
    reservadas.add("verdadeiro");
    reservadas.add("falso");
    reservadas.add("caso");
    reservadas.add("ou");
    reservadas.add("nao");
    reservadas.add("faca");
    reservadas.add("inicio");
    reservadas.add("enquanto");
    reservadas.add("para");
    reservadas.add("de");
    reservadas.add("repita");
    reservadas.add("ate");
    reservadas.add("passo");

    tipos = new HashSet<String>();
    tipos.add("real");
    tipos.add("inteiro");
    tipos.add("logico");
    tipos.add("cadeia");
    tipos.add("caractere");
    tipos.add("vetor");
    tipos.add("modulo");
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
