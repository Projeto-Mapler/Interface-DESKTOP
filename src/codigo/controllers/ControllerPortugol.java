package codigo.controllers;

import java.util.HashSet;
import java.util.Iterator;

import org.fxmisc.richtext.StyleClassedTextArea;

public class ControllerPortugol {
	
	/*
	 * Classe responsavel pelo controle do codigo portugol
	 * 
	 */

	public static StyleClassedTextArea setCores(StyleClassedTextArea area) {
		
		if(area.getText().equals("")){
			area.appendText("variaveis\n\ninicio\n\nfim.");
		}
		
		area.setStyleClass(0, area.getText().length(), "white");
    	Iterator it = getReservadas().iterator();
    	int in;
    	String texto = area.getText();
    	while(it.hasNext()) {
    		String str = it.next().toString();
    		Iterator e = getIndices(str, texto).iterator();
    		while(e.hasNext()) {
    			in =  Integer.parseInt(e.next().toString());
    			if(str.equals(" inicio ") || str.equals(" inicio\n") || str.equals(" inicio\t") ||  str.equals("\ninicio ") || str.equals("\ninicio\n") || str.equals("\ninicio\t") || str.equals("\tinicio ") || str.equals("\tinicio\n") || str.equals("\tinicio\t")) {
    				if(in == 0)
    					continue;
    			}
    			if(str.equals("variaveis ") || str.equals("variaveis\n") || str.equals("variaveis\t") || str.equals("\tvariaveis ") || str.equals("\tvariaveis\n") || str.equals("\tvariaveis\t") || str.equals("\nvariaveis ") || str.equals("\nvariaveis\n") || str.equals("\nvariaveis\t")) {
    				if(in != 0)
    					continue;
    			}
    			area.setStyleClass(in, in+str.length(), "blue");
    		}

    	}
    	
    	it = getTipos().iterator();
    	while(it.hasNext()) {
    		String str = it.next().toString();
    		Iterator e = getIndices(str, texto).iterator();
    		while(e.hasNext()) {
    			in = Integer.parseInt(e.next().toString());
    			area.setStyleClass(in, in+str.length(), "orange");
    		}
    	}
    	
    	it = getConstantes().iterator();
    	while(it.hasNext()) {
    		String str = it.next().toString();
    		Iterator e = getIndices(str, texto).iterator();
    		while(e.hasNext()) {
    			in = Integer.parseInt(e.next().toString());
    			area.setStyleClass(in, in+str.length(), "red");
    		}
    	}
    	
    	in =  texto.toLowerCase().indexOf("'");
    	int out = texto.toLowerCase().indexOf("'",in+1);
    	while(in != -1 && out != -1) {
    		area.setStyleClass(in, out+1, "red");
    		in =  texto.toLowerCase().indexOf("'",out+1);
    		out = texto.toLowerCase().indexOf("'",in+1);
    	}
    	
    	in =  texto.toLowerCase().indexOf("\"");
    	out = texto.toLowerCase().indexOf("\"",in+1);
    	while(in != -1 && out != -1) {
    		area.setStyleClass(in, out+1, "red");
    		in =  texto.toLowerCase().indexOf("\"",out+1);
    		out = texto.toLowerCase().indexOf("\"",in+1);
    	}
    	
    	in =  texto.toLowerCase().indexOf("//");
    	out = texto.toLowerCase().indexOf("\n",in+1);
    	while(in != -1 && out != -1) {
    		area.setStyleClass(in, out+1, "comentario");
    		in =  texto.toLowerCase().indexOf("//",out+1);
    		out = texto.toLowerCase().indexOf("\n",in+1);
    	}
    	
    	in =  texto.toLowerCase().indexOf(";"); //cor do ;
    	while(in != -1) {
    		area.setStyleClass(in, in+1, "orange");
    		in =  texto.toLowerCase().indexOf(";",in+1);
    	}
    	area.setStyleClass(area.getText().length(), area.getText().length(), "white");
    	return area;
    }
	
	private static HashSet<String> getIndices(String palavra, String texto) {
    	HashSet<String> i = new HashSet<String>();
    	String txt = texto;
    	int in = txt.toLowerCase().indexOf(palavra.toLowerCase());
    	while(in != -1) {
    		i.add(""+in);
    		in = txt.toLowerCase().indexOf(palavra.toLowerCase(),in+1);
    	}
    	return i;
    }
	
	private static HashSet<String> getReservadas(){
		HashSet<String> portugolReservadas = new HashSet<String>();
		portugolReservadas.add("variaveis ");
		portugolReservadas.add("variaveis\n");
		portugolReservadas.add("variaveis\t");
		portugolReservadas.add(" variaveis ");
		portugolReservadas.add(" variaveis\n");
		portugolReservadas.add("\nvariaveis ");
		portugolReservadas.add("\nvariaveis\n");
		portugolReservadas.add("\tvariaveis ");
		portugolReservadas.add("\tvariaveis\n");
		portugolReservadas.add("\tvariaveis\t");
		portugolReservadas.add("inicio ");
		portugolReservadas.add(" inicio ");
		portugolReservadas.add(" inicio\n");
		portugolReservadas.add("\ninicio ");
		portugolReservadas.add("\ninicio\n");
		portugolReservadas.add("\tinicio ");
		portugolReservadas.add("\tinicio\n");
		portugolReservadas.add("\tinicio\t");
    	//portugolReservadas.add("fim ");
		portugolReservadas.add(" fim ");
		portugolReservadas.add(" fim\n");
		portugolReservadas.add("\nfim ");
		portugolReservadas.add("\nfim\n");
		portugolReservadas.add("\nfim");
		portugolReservadas.add("\tfim ");
		portugolReservadas.add("\tfim\n");
		portugolReservadas.add("\tfim\t");
    	//portugolReservadas.add("ler ");
		portugolReservadas.add(" ler ");
		portugolReservadas.add(" ler\n");
		portugolReservadas.add("\nler ");
		portugolReservadas.add("\nler\n");
		portugolReservadas.add("\tler ");
		portugolReservadas.add("\tler\n");
		portugolReservadas.add("\tler\t");
    	//portugolReservadas.add("escrever ");
		portugolReservadas.add(" escrever ");
		portugolReservadas.add(" escrever\n");
		portugolReservadas.add("\nescrever ");
		portugolReservadas.add("\nescrever\n");
		portugolReservadas.add("\tescrever ");
		portugolReservadas.add("\tescrever\n");
		portugolReservadas.add("\tescrever\t");
    	//portugolReservadas.add("se ");
		portugolReservadas.add(" se ");
		portugolReservadas.add(" se\n");
		portugolReservadas.add("\nse ");
		portugolReservadas.add("\nse\n");
		portugolReservadas.add("\tse ");
		portugolReservadas.add("\tse\n");
		portugolReservadas.add("\tse\t");
    	//hs.add("entao ");
		portugolReservadas.add(" entao ");
		portugolReservadas.add(" entao\n");
		portugolReservadas.add("\nentao ");
		portugolReservadas.add("\nentao\n");
		portugolReservadas.add("\tentao ");
		portugolReservadas.add("\tentao\n");
		portugolReservadas.add("\tentao\t");
    	//hs.add("senao ");
		portugolReservadas.add(" senao ");
		portugolReservadas.add(" senao\n");
		portugolReservadas.add("\nsenao ");
		portugolReservadas.add("\tsenao ");
		portugolReservadas.add("\tsenao\n");
		portugolReservadas.add("\nsenao\n");
		portugolReservadas.add("\tsenao ");
		portugolReservadas.add("\tsenao\n");
		portugolReservadas.add("\tsenao\t");
		portugolReservadas.add(" e ");
		portugolReservadas.add(" e\n");
		portugolReservadas.add("\ne ");
		portugolReservadas.add("\ne\n");
		portugolReservadas.add("\te ");
		portugolReservadas.add("\te\n");
		portugolReservadas.add("\te\t");
		portugolReservadas.add(" verdadeiro ");
		portugolReservadas.add(" verdadeiro\n");
		portugolReservadas.add("\nverdadeiro ");
		portugolReservadas.add("\nverdadeiro\n");
		portugolReservadas.add("\tverdadeiro ");
		portugolReservadas.add("\tverdadeiro\n");
		portugolReservadas.add("\tverdadeiro\t");
		portugolReservadas.add("\nverdadeiro;");
		portugolReservadas.add("\tverdadeiro;");
		portugolReservadas.add(" verdadeiro;");
		portugolReservadas.add(" falso ");
		portugolReservadas.add(" falso\n");
		portugolReservadas.add("\nfalso ");
		portugolReservadas.add("\nfalso\n");
		portugolReservadas.add("\tfalso ");
		portugolReservadas.add("\tfalso\n");
		portugolReservadas.add("\tfalso\t");
		portugolReservadas.add("\nfalso;");
		portugolReservadas.add("\tfalso;");
		portugolReservadas.add(" falso;");
		portugolReservadas.add(" caso ");
		portugolReservadas.add(" caso\n");
		portugolReservadas.add("\ncaso ");
		portugolReservadas.add("\ncaso\n");
		portugolReservadas.add("\tcaso ");
		portugolReservadas.add("\tcaso\n");
		portugolReservadas.add("\tcaso\t");
		portugolReservadas.add(" ou ");
		portugolReservadas.add(" ou\n");
		portugolReservadas.add("\nou ");
		portugolReservadas.add("\nou\n");
		portugolReservadas.add("\tou ");
		portugolReservadas.add("\tou\n");
		portugolReservadas.add("\tou\t");
		portugolReservadas.add(" nao ");
		portugolReservadas.add(" nao\n");
		portugolReservadas.add("\nnao ");
		portugolReservadas.add("\nnao\n");
		portugolReservadas.add("\tnao ");
		portugolReservadas.add("\tnao\n");
		portugolReservadas.add("\tnao\t");
		portugolReservadas.add(" faca ");
		portugolReservadas.add(" faca\n");
		portugolReservadas.add("\nfaca ");
		portugolReservadas.add("\nfaca\n");
		portugolReservadas.add("\tinicio ");
		portugolReservadas.add("\tinicio\n");
		portugolReservadas.add("\tinicio\t");
		portugolReservadas.add(" enquanto ");
		portugolReservadas.add(" enquanto\n");
		portugolReservadas.add("\nenquanto ");
		portugolReservadas.add("\nenquanto\n");
		portugolReservadas.add("\tenquanto ");
		portugolReservadas.add("\tenquanto\n");
		portugolReservadas.add("\tenquanto\t");
		portugolReservadas.add(" para ");
		portugolReservadas.add(" para\n");
		portugolReservadas.add("\npara ");
		portugolReservadas.add("\npara\n");
		portugolReservadas.add("\tpara ");
		portugolReservadas.add("\tpara\n");
		portugolReservadas.add("\tpara\t");
		portugolReservadas.add(" de ");
		portugolReservadas.add(" de\n");
		portugolReservadas.add("\nde ");
		portugolReservadas.add("\nde\n");
		portugolReservadas.add("\tde ");
		portugolReservadas.add("\tde\n");
		portugolReservadas.add("\tde\t");
		portugolReservadas.add(" repita ");
		portugolReservadas.add(" repita\n");
		portugolReservadas.add("\nrepita ");
		portugolReservadas.add("\nrepita\n");
		portugolReservadas.add("\trepita ");
		portugolReservadas.add("\trepita\n");
		portugolReservadas.add("\trepita\t");
		portugolReservadas.add(" ate ");
		portugolReservadas.add(" ate\n");
		portugolReservadas.add("\nate ");
		portugolReservadas.add("\nate\n");
		portugolReservadas.add("\tate ");
		portugolReservadas.add("\tate\n");
		portugolReservadas.add("\tate\t");
		portugolReservadas.add(" passo ");
		portugolReservadas.add(" passo\n");
		portugolReservadas.add("\npasso ");
		portugolReservadas.add("\npasso\n");
		portugolReservadas.add("\tpasso ");
		portugolReservadas.add("\tpasso\n");
		portugolReservadas.add("\tpasso\t");
		return portugolReservadas;
	}
	
	private static HashSet<String> getTipos(){
		HashSet<String> portugolTipos = new HashSet<String>();
		portugolTipos.add("real;");
		portugolTipos.add("cadeia;");
		portugolTipos.add("inteiro;");
		portugolTipos.add("logico;");
		portugolTipos.add("vetor[");
		portugolTipos.add("vetor ");
		portugolTipos.add("caractere;");
		portugolTipos.add("{");
		portugolTipos.add("}");
		portugolTipos.add("(");
		portugolTipos.add(")");
		portugolTipos.add(">");
		portugolTipos.add("<");
		portugolTipos.add("+");
		portugolTipos.add("=");
		portugolTipos.add("-");
		portugolTipos.add("*");
		portugolTipos.add("/");
		return portugolTipos;
	}
	
	private static HashSet<String> getConstantes(){
		HashSet<String> portugolC = new HashSet<String>();
		portugolC.add("1");
		portugolC.add("2");
		portugolC.add("3");
		portugolC.add("4");
		portugolC.add("5");
		portugolC.add("6");
		portugolC.add("7");
		portugolC.add("8");
		portugolC.add("9");
		portugolC.add("0");
		return portugolC;
	}
	
}
