package mapler.model.resource;

public enum Tema {

	Dark("/css/config-dark.css"),
	Light("/css/config-light.css"),
	Contraste("/css/config-daltonismo.css");
	
	private String url;
	  
	  private Tema(String url){
	    this.url = url;
	  }
	  
	  public String getUrl() {
	    return this.url;
	  }
	  
}
