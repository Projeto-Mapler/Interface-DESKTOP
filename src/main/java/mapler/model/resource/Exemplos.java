package mapler.model.resource;

public enum Exemplos {
	
	  ESTRUTURA("/exemplos/estrutura.txt"),
	  IO("/exemplos/io.txt"),
	  CONDICIONAIS_SE("/exemplos/condicionais.txt"),
	  CONDICIONAIS_SENAO("/exemplos/condicionais.txt"),
	  OPERACOES("/exemplos/operacoes.txt"),
	  OPERACOES_ADD("/exemplos/operacao_add.txt"),
	  OPERACOES_SUB("/exemplos/operacao_sub.txt"),
	  OPERACOES_MULT("/exemplos/operacao_mult.txt"),
	  OPERACOES_DIV("/exemplos/operacao_div.txt"),
	  OPERACOES_PRECEDENCIA("/exemplos/operacao_precedencia.txt"),
	  LACO("/exemplos/laços.txt"),
	  LACO_PARA("/exemplos/laços_para.txt"),
	  LACO_REPITA("/exemplos/laços_repita.txt"),
	  LACO_ENQUANTO("/exemplos/laços_enquanto.txt"),
	  VARIAVEIS("/exemplos/variaveis.txt"),
	  VAR_NUMERICO("/exemplos/variaveis_numericos.txt"),
	  VAR_LITERAL("/exemplos/variaveis_literais.txt"),
	  VAR_BOOLEANO("/exemplos/variaveis_booleano.txt"),
	  VAR_VETORES("/exemplos/vetores.txt");  
	  
	  private String url;
	  
	  private Exemplos(String url){
	    this.url = url;
	  }
	  
	  public String getUrl() {
	    return this.url;
	  }
}
