package mapler.model.linguagem;

import java.util.HashSet;

/**
 * Modelo representando uma linguagem suportada pela tradução
 * 
 * @author Kerlyson
 *
 */
public abstract class Linguagem {

  protected HashSet<String> tipos, reservadas, constantes;

  public Linguagem() {
    this.iniciaTipos();
  }

  /**
   * Deve popular as variaveis
   */
  protected abstract void iniciaTipos();

  public HashSet<String> getTipos() {
    return this.tipos;
  }

  public HashSet<String> getReservadas() {
    return this.reservadas;
  }

  public HashSet<String> getConstantes() {
    return this.constantes;
  }


}
