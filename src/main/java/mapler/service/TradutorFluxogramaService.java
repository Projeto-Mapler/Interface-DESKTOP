package mapler.service;

import java.util.ArrayList;

import javafx.scene.layout.AnchorPane;
import mapler.fluxograma.diagrama.Associacao;
import mapler.fluxograma.diagrama.Fluxograma;

public class TradutorFluxogramaService {

	private String continuacao;
	private static TradutorFluxogramaService instancia;
	
	private TradutorFluxogramaService() {
		// TODO Auto-generated constructor stub
	}
	
	public static TradutorFluxogramaService get() {
		if(instancia == null) {
			instancia = new TradutorFluxogramaService();
		}
		return instancia;
	}
	
	public String getTraducao2Portugol(Fluxograma fluxograma) {

		String blocoVariaveis = "variaveis\n  variavel: cadeia;\n";
		String blocoInicio = "\ninicio\n";
		String traducao;
		continuacao = "";
		AnchorPane inicio = fluxograma.getInicio();
		AnchorPane fim = fluxograma.getFim();

		if (inicio == null || fim == null) {
			return null;
		}

		traducao = blocoVariaveis + blocoInicio + getTrechoCodigo(fluxograma, inicio) + "\nfim";
		traducao = traducao.replace("enquanto varivavel nao \"valor\" faca\r\nfim enquanto;\n", "");
		return traducao;
	}

	
	//busca fim
	private boolean caminhoFim(Fluxograma fluxograma, AnchorPane paneProibido, AnchorPane paneAtual) {
		if(paneAtual.equals(paneProibido)) {
			return false;
		}
		if(paneAtual.getId().contains("fim")) {
			return true;
		}
		
		ArrayList<Associacao> lista = fluxograma.getAssociacoesByPane1(paneAtual);
		if(lista.size() > 1) {
			boolean boo;
			for(Associacao associacao : lista) {
				boo = caminhoFim(fluxograma, paneProibido, associacao.getPane2());
				if(boo)
					return true;
			}
			return false;
		}else {
			return caminhoFim(fluxograma, paneProibido, lista.get(0).getPane2());
		}
		 
	}

	// percorre o caminho inverso //usado para buscar se chega até o inicial
	// novamente
	private String getCaminhoReverso(Fluxograma fluxo, AnchorPane inicial, AnchorPane atual) {
		String retorno = "";
		if (atual.equals(inicial)) {
			return getCodigoByPane(fluxo, atual);
		}
		if (atual.getId().contains("inicio")) {
			return "";
		}

		ArrayList<Associacao> lista = fluxo.getAssociacoesByPane2(atual);
		for (Associacao a : lista) {
			String retornado =  getCaminhoReverso(fluxo, inicial, a.getPane1()) + getCodigoByPane(fluxo, atual);
			
			if(a.getPane1().getId().contains("decisao")) {
				if (a.getLabel().getText().equals("Sim")) {
					retorno = getCaminhoReverso(fluxo, inicial, a.getPane1()) + "se variavel = \"valor\" entao\n" + getCodigoByPane(fluxo, atual) + "fim se;\n";
				}else {
					retorno = getCaminhoReverso(fluxo, inicial, a.getPane1()) + "se variavel nao \"valor\" entao\n" + getCodigoByPane(fluxo, atual) + "fim se;\n";
				}
				
				ArrayList<Associacao> listaInterna = fluxo.getAssociacoesByPane1(a.getPane1());
				for(Associacao as : listaInterna) {
					if(as.getPane2().equals(a.getPane2()))
						continue;
					Boolean bool = caminhoFim(fluxo, a.getPane1(), as.getPane2());
					if(bool)
					   continuacao = getTrechoCodigo(fluxo, as.getPane2());
				}
				
				return retorno;
				
			}else {
				retornado =  getCaminhoReverso(fluxo, inicial, a.getPane1()) + getCodigoByPane(fluxo, atual);
			}
			
			if (retornado.equals("")) {
				continue;
			}
			
			if(a.getPane1().getId().contains("decisao")) {
				return retornado + "fim se;\n";
			}
			
			return retornado;
		}

		return retorno;
	}

	private String getCodigoLoop(Fluxograma fluxo, Associacao associacao) {
		String bool;
		bool = "enquanto variavel = \"valor\" faca\n" + getCaminhoReverso(fluxo, associacao.getPane2(), associacao.getPane1()) + "fim enquanto;\n";
		if( !continuacao.equals(""))
			bool += continuacao;
		return bool;
	}

	private String getTrechoCodigo(Fluxograma fluxo, AnchorPane ap) {

		String trecho = "";
		ArrayList<Associacao> lista = fluxo.getAssociacoesByPane(ap);
		int loop = 0;

		// buscar possivel loop
		ArrayList<Associacao> two = fluxo.getAssociacoesByPane2(ap);
		if (two.size() >= 2 && !ap.getId().contains("fim")) {
			String isLoop = "";
			
			for(Associacao p : fluxo.getAssociacoesByPane2(ap)) {
				String nomometodo = getCodigoLoop(fluxo, p);
				if(nomometodo.equals("enquanto variavel = \"valor\" faca\nfim enquanto;\n")) {
					continue;
				}
				return  nomometodo;
			}
		}

		if (loop == 0) {
			for (Associacao a : lista) {
				if (a.getPane1().equals(ap)) {
					if (ap.getId().contains("decisao")) {
						if (a.getLabel().getText().equals("Sim")) {
							if (trecho.equals("")) {
								trecho = "se variavel = \"valor\" entao\n" + getTrechoCodigo(fluxo, a.getPane2());
							} else {
								trecho = "se variavel = \"valor\" entao\n" + getTrechoCodigo(fluxo, a.getPane2())
										+ trecho;
							}
						} else {
							if (trecho.equals("")) {
								trecho = "senao\n" + getTrechoCodigo(fluxo, a.getPane2());
							} else {
								trecho = trecho + "senao\n" + getTrechoCodigo(fluxo, a.getPane2());
							}
						}
					} else {
						trecho = getCodigoByPane(fluxo, ap) + getTrechoCodigo(fluxo, a.getPane2());
					}
				}
			}

			if (ap.getId().contains("decisao")) {
				trecho += "fim se;\n";
			}
		}

		return trecho;
	}

	private String getCodigoByPane(Fluxograma flx, AnchorPane ap) {

		if (ap.getId().contains("inicio")) {
			return "";
		} else if (ap.getId().contains("fim")) {
			return "";
		} else if (ap.getId().contains("entrada")) {
			return "leia variavel;\n";
		} else if (ap.getId().contains("saida")) {
			return "escrever variavel;\n";
		} else if (ap.getId().contains("decisao")) {
			return "";
		} else if (ap.getId().contains("processamento")) {
			return "variavel <- \"valor\";\n";
		}

		return null;
	}
}
