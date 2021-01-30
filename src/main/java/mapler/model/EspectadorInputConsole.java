package mapler.model;

/**
 * Classes que querem o valor que o usuario informa no console devem
 * implementar essa interface
 * @author Kerlyson
 *
 */
public interface EspectadorInputConsole {
  public void notificarInput(String input);
}
