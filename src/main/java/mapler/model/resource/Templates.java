package mapler.model.resource;

public enum Templates {
  
  BASE("/view/tela_base.fxml"),
  CODIGO("/view/tela_codigo.fxml"),
  INICIO("/view/tela_inicio.fxml"),
  FLUXOGRAMA("/view/tela_fluxograma.fxml"),
  SOBRE("/view/tela_sobre.fxml"),
  DEBUG("/view/tela_debug.fxml"),
  FIG_INICIO("/view/figuras/inicio.fxml"),
  FIG_FIM("/view/figuras/fim.fxml"),
  FIG_DECISAO("/view/figuras/decisao.fxml"),
  FIG_LEGENDA("/view/figuras/legenda.fxml"),
  FIG_PROCESSAMENTO("/view/figuras/processamento.fxml"),
  FIG_ENTRADA("/view/figuras/entrada.fxml"),
  FIG_SAIDA("/view/figuras/saida.fxml");  
  
  private String url;
  private Templates(String url){
    this.url = url;
  }
  public String getUrl() {
    return this.url;
  }
}
