package mapler.service;

import java.io.IOException;
import java.util.Arrays;

import org.fxmisc.richtext.StyleClassedTextArea;

import conversores.ConversorStrategy;
import debug.DebugSnapshot;
import debug.EstadoDebug;
import debug.PassoAPassoDebugStrategy;
import evento.EventoInterpretador;
import interpretador.AcaoInterpretador;
import interpretador.InterpretadorService;
import interpretador.LeitorEntradaConsole;
import javafx.application.Platform;
import mapler.controller.DebugController;
import mapler.model.ConsoleStyleClassedTextArea;
import mapler.model.EspectadorInputConsole;
import modelos.excecao.ParserError;
import modelos.excecao.RuntimeError;

/**
 * Intermediaria entre a IDE (console e traducao) e o Interpretador
 */
public class ConsoleTraducaoService implements AcaoInterpretador, EspectadorInputConsole {
	private InterpretadorService interpretador;
	LeitorEntradaConsole leitor;
	private ConsoleStyleClassedTextArea consoleTextArea;
	private StyleClassedTextArea areaTraducao;
	private DebugController debugController;

	public ConsoleTraducaoService(DebugController debugController, ConsoleStyleClassedTextArea areaConsole,
			StyleClassedTextArea areaTraducao) {
		this.consoleTextArea = areaConsole;
		this.consoleTextArea.setEspectador(this);
		this.areaTraducao = areaTraducao;
		this.interpretador = new InterpretadorService(this);
		this.debugController = debugController;
		debugController.setConsoleTraducaoService(this);
		this.interpretador.setEventosLog(Arrays.asList(EventoInterpretador.values()));
		this.interpretador.setLogAtivo(false);
	}

	private void initDebug(boolean debugOn) {
		if (debugOn) {

			debugController.reset();
			interpretador.setDebugStrategy(new PassoAPassoDebugStrategy());
		}
			interpretador.setDebugAtivo(debugOn);
		
	}

	public void executar(String pathFile, boolean debugOn) {

		initDebug(debugOn);

		// TODO: logica de salvar e talz... tlvz deve ficar no codigoController antes de
		// chamar esse metodo
		try {
			this.interpretador.executarViaArquivo(pathFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void continuarDebug() {
		interpretador.debugContinuar();
	}

	public void passoDebug() {
		interpretador.debugProxPasso();
	}

	public void pararDebug() {
		interpretador.debugParar();
	}

	public void executarTexto(String texto, boolean debugOn) {
		initDebug(debugOn);
		this.consoleTextArea.limparConsole();
		this.interpretador.executarViaTexto(texto);
	}

	public void setTraducao(String pathFile, ConversorStrategy tipoConversao) {
		String traducao = null;
		try {
			traducao = this.interpretador.traduzirDoArquivo(pathFile, tipoConversao);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (traducao != null && !traducao.isEmpty()) {
			this.areaTraducao.clear();
			this.areaTraducao.appendText(traducao);
		}
	}

	public void setTraducaoTexto(String texto, ConversorStrategy tipoConversao) {
		String traducao = null;
		try {
			traducao = this.interpretador.traduzirDoTexto(texto, tipoConversao);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (traducao != null && !traducao.isEmpty()) {
			this.areaTraducao.clear();
			this.areaTraducao.appendText(traducao);
		}
	}

	@Override
	public void notificarInput(String input) {
		this.leitor.setValor(input); // informa o interpretador
		this.leitor = null;
	}

	@Override
	public void onInput(LeitorEntradaConsole leitor) {
		this.leitor = leitor;
		Platform.runLater(() -> {
			this.consoleTextArea.solicitarInputUsuario();
		});
	}

	@Override
	public void onOutput(String output) {
		// Platform usado pois ha chamada da area_console dentro do interpretador que
		// n??o faz parte do
		// sistema FX
		Platform.runLater(() -> {
			consoleTextArea.imprimirMsgComQuebraLinha(output);
		});
	}

	@Override
	public void onInterpretacaoConcluida(double tempoExecucao) {
		this.printConclusao(tempoExecucao);
	}

	@Override
	public void onInterpretacaoInterrompida(double tempoExecucao) {
		this.printConclusao(tempoExecucao);
	}

	private void printConclusao(double payload) {
		Platform.runLater(() -> {
			consoleTextArea.imprimirMsgComQuebraLinha("Fim execu????o. " + (Double) payload);
		});
	}

	@Override
	public void onDebugMudancaEstado(EstadoDebug novoEstado) {
		Platform.runLater(() -> {
			switch (novoEstado) {
			case EXECUTANDO:
				debugController.toggleBtnExecutar(false);
				debugController.toggleBtnParar(true);
				debugController.toggleBtnPasso(false);
				return;
			case INICIAL:
				debugController.toggleBtnExecutar(false);
				debugController.toggleBtnParar(false);
				debugController.toggleBtnPasso(false);
				return;

			case PAUSADO:
				debugController.toggleBtnExecutar(true);
				debugController.toggleBtnParar(true);
				debugController.toggleBtnPasso(true);
				return;
			}
		});
		return;
	}

	@Override
	public void onDebugPassoExecutado(DebugSnapshot snapshot) {
		Platform.runLater(() -> {
			debugController.addDado(snapshot);
		});
		return;
	}

	@Override
	public void onErro(RuntimeException erro) {
		if (erro instanceof ParserError) {
			ParserError pe = (ParserError) erro;
			Platform.runLater(() -> {
				consoleTextArea.imprimirErro(pe.linha, pe.getLexeme(), pe.mensagem);
			});
		} else if (erro instanceof RuntimeError) {
			RuntimeError re = (RuntimeError) erro;
			Platform.runLater(() -> {
				consoleTextArea.imprimirErro(re.getLinha(), re.getLexeme(), re.getMessage());
			});
		}
	}

	@Override
	public void onLog(String msgLog) {
		System.out.println(msgLog);
	}

	public void pararExecucao() {
		this.consoleTextArea.resetEsperandoInputUsuario();
		this.interpretador.destruir();
		
	}

	
}