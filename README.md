# Interface-MAPLER


SOBRE O MAPLER
>Este projeto visa o desenvolvimento e produção de uma ferramenta de compilação da pseudo-linguagem português estruturado (portugol) e desenvolvimento de fluxogramas
para auxiliar estudantes brasileiros iniciando em atividades de programação. Para mais informacoes, acesse [Site do Mapler](https://portugol.sourceforge.io/)

SOBRE A INTERFACE
>A interface foi desenvolvida para atender ao [interpretador](https://github.com/kerlyson/interpretadorPtEstruturadoJava), de forma que ambos se comuniquem e sejam capazes de oferecer aos usuarios a capacidade de escrever, compilar, debugar e traduzir codigos em portugol.
>A interface possui capacidade para desenvolver algoritmos em fluxograma e traduzir os mesmos para Portugol.


![tela inicio](https://github.com/EliasRLima/Interface-MAPLER/blob/master/images/inicio.png)

![tela traducao](https://github.com/EliasRLima/Interface-MAPLER/blob/master/images/traducao.png)

![tela debug](https://github.com/EliasRLima/Interface-MAPLER/blob/master/images/debug_rodando.png)

![tela fluxograma](https://github.com/EliasRLima/Interface-MAPLER/blob/master/images/fluxograma.PNG)

>veja mais imagens em [github/mapler](https://github.com/EliasRLima/Interface-MAPLER/tree/master/images)

INTRODUCAO
>Mapler Studio é uma ferramenta educacional para aprendizagem e
desenvolvimento de algoritmos utilizando pseudo-código com a linguagem
português estruturado (ou “portugol”).
Atualmente desenvolvido e mantido por alunos do Instituto Federal
do Maranhão (IFMA), a ferramenta encontra-se em fase beta de desenvolvimento e testes, 
disponibilizando as seguintes funcionalidades básicas:

- Execução do pseudo-código;
- Depuração (debug) passo-a-passo;
- Tradução do pseudo-código para linguagens alto nível: Java, C, C++,
Pascal, Python;
- Desenvolvimento de Fluxogramas
- Tradução de Fluxograma para Portugol

INSTALACAO
>Atualmente, o Mapler Studio está compilado em um executável .jar
para fácil execução em qualquer sistema operacional com Java instalado

DOWNLOAD
>Para baixar a versao mais atualizada acesse [sourceforge/portugol](https://sourceforge.net/projects/portugol/files/latest/download)

REQUERIMENTOS
>Java 11 ou superior, caso necessite voce pode encontrar o java para download nesse [link](https://www.oracle.com/java/technologies/downloads/).

COMO UTILIZAR
>Um pequeno manual sobre o uso do compilador esta disponivel em [sourceforge](https://portugol.sourceforge.io/manuais.html).

BUILD
> Dependencias:```choco install -y innosetup wixtoolset```

>```mvn clean  package -P<profile> -Djavafx.platform=<plataforma>```

Onde `<profile>` e `<plataforma>` aceitam os valores:

- win (default)
- linux
- mac

