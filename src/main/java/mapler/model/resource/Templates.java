package mapler.model.resource;

public enum Templates {
  
  BASE("view/tela_base.fxml"),
  CODIGO("view/tela_codigo.fxml"),
  INICIO("view/tela_inicio.fxml");  
  
  private String url;
  private Templates(String url){
    this.url = url;
  }
  public String getUrl() {
    return this.url;
  }
}
