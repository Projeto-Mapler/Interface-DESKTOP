package mapler.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import mapler.service.AlertaService;

/**
 * Gerencia a manipulação de arquivos (abrir, salvar, salvar como)
 */
public class GerenciadorArquivo {

  /**
   *  classe estatica
   */
  private GerenciadorArquivo() {}

  public static File abrirArquivo() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Portugol Files", "*.txt"));
    File open = fileChooser.showOpenDialog(null);
    if (open != null) {
      return open;
    } else {
      return null;
    }
  }

  /**
   * Salva o arquivo sobreescrevendo seu conteudo
   * 
   * @param arquivo - referencia do arquivo
   * @param aviso - se deve exibir um aviso
   * @param txt - texto que deve ser escrito no arquivo
   * @return - se foi salvo com sucesso ou nao
   */
  public static boolean salvarArquivo(File arquivo, boolean aviso, String txt) {
    if (arquivo != null) {
      try {
        FileWriter writer = new FileWriter(arquivo);
        writer.write(txt);
        writer.close();

        if (aviso)
          AlertaService.showAviso("O arquivo foi salvo!");
        return true;
      } catch (IOException e) {
        arquivo = null;
        return false;
      }
    } else {
      if (aviso)
        return salvarArquivoComo(arquivo, txt);

    }
    return false;
  }

  /**
   * Sobreescreve o texto do arquivo e permite salvar em outro local
   * 
   * @param arquivo - referencia do arquivo salvo
   * @param txt - texto para escrever no arquivo
   * @return - se foi salvo com sucesso ou nao
   */
  public static boolean salvarArquivoComo(File arquivo, String txt) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Save file");
    fileChooser.setInitialFileName("codigo");
    fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Portugol Files", "*.txt"));
    arquivo = fileChooser.showSaveDialog(null);

    if (arquivo != null) {
      return salvarArquivo(arquivo, false, txt);
    } else {
      return false;
    }
  }

  /**
   * Salva o texto em um arquivo
   * 
   * @param txt - texto para ser escrito no arquivo
   * @return - O Objeto File do arquivo salvo ou null se não pode salvar o arquivo
   */
  public static File salvarArquivo(String txt) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Save file");
    fileChooser.setInitialFileName("codigo");
    fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Portugol Files", "*.txt"));
    File arquivo = fileChooser.showSaveDialog(null);
    boolean isArquivoSalvo = salvarArquivo(arquivo, false, txt);
    if (isArquivoSalvo) {
      return arquivo;
    } else {
      return null;
    }
  }
}
