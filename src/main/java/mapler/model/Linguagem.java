package mapler.model;

import conversores.ConversorStrategy;

public enum Linguagem {

	JAVA(ConversorStrategy.JAVA), C(ConversorStrategy.C), Cpp(ConversorStrategy.Cpp), PASCAL(ConversorStrategy.PASCAL),
	PYTHON(ConversorStrategy.PYTHON),
	PORTUGOL(null);

	private final ConversorStrategy conversorStrategy;

	private Linguagem(ConversorStrategy conversorStrategy) {
		this.conversorStrategy = conversorStrategy;
	}

	public ConversorStrategy getConversorStrategy() {
		return this.conversorStrategy;
	}
}
