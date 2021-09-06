package mapler.util;

import java.net.URL;

/**
 * Carrega arquivos da pasta de recursos
 */
public class CarregadorRecursos {
//  private static ClassLoader classLoader;

//  static {
//    classLoader = CarregadorRecursos.class.getClassLoader();
//  }
	private static CarregadorRecursos instance;
  
  /**
   * classe estatica
   */
  private CarregadorRecursos() {}
  
  public static CarregadorRecursos get() {
	  if(instance == null) instance = new CarregadorRecursos();
	  return instance;
  }

  public URL getResource(String path) {
    return getClass().getResource(path);
  }

  public String getResourceExternalForm(String path) {
    return getClass().getResource(path).toExternalForm();
  }
}
