package mapler.model;

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
    return this.getTipos();
  }

  public HashSet<String> getReservadas() {
    return this.getReservadas();
  }

  public HashSet<String> getConstantes() {
    return this.getConstantes();
  }


}
