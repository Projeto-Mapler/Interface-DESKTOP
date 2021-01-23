package mapler.util;

import java.net.URL;

public class CarregadorRecursos {
  private static ClassLoader classLoader;

  static {
    classLoader = CarregadorRecursos.class.getClassLoader();
  }

  /**
   * classe estatica
   */
  private CarregadorRecursos() {}

  public static URL getResource(String path) {
    return classLoader.getResource(path);
  }

  public static String getResourceExternalForm(String path) {
    return classLoader.getResource(path).toExternalForm();
  }
}