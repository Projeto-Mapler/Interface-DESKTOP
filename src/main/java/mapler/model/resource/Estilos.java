package mapler.model.resource;

public enum Estilos {
  ARLETAS("/css/alertas.css"),
  MENUBAR("/css/menubar.css"),
  SPLITPANE("/css/splitpane.css"),
  TABFILHO("/css/tabFilho.css"),
  TABPAI("/css/tabPai.css"),
  TEXTO("/css/texto.css"),
  BOTOES("/css/botoes.css"),
  TABAREACOD("/css/tabAreacod.css"),
  SCROLL("/css/scroll.css"),
  CONSOLE("/css/console.css");
  
  
  private String url;
  private Estilos(String url){
    this.url = url;
  }
  public String getUrl() {
    return this.url;
  }
}
