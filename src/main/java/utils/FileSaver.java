package utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List; // Importa a interface List

public class FileSaver {

    /**
     * Salva uma lista de strings em um arquivo de texto, com cada string em uma
     * nova linha.
     *
     * @param filePath O caminho completo do arquivo (ex: "C:/dados/meuArquivo.txt"
     *                 ou "meuArquivo.txt").
     * @param lines    A lista de strings a serem salvas no arquivo.
     * @throws IOException Se ocorrer um erro de I/O durante a escrita do arquivo.
     */
    public static void saveListToFile(String filePath, List<String> lines) throws IOException {
        // Usamos try-with-resources para garantir que o FileWriter e BufferedWriter
        // sejam fechados automaticamente.
        try (FileWriter fileWriter = new FileWriter(filePath);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

            for (String line : lines) {
                bufferedWriter.write(line); // Escreve a linha
                bufferedWriter.newLine(); // Adiciona uma nova linha após cada string
            }

            System.out.println("Conteúdo salvo com sucesso em: " + filePath);

        } catch (IOException e) {
            // Re-lançamos a exceção para que o código chamador possa lidar com ela.
            System.err.println("Erro ao salvar o arquivo '" + filePath + "': " + e.getMessage());
            throw e; // Lança a exceção novamente para ser capturada por quem chamou o método
        }
    }
}