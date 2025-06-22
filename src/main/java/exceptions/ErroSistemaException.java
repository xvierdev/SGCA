package exceptions;

/**
 * Exceção personalizada para representar erros genéricos de sistema ou de
 * infraestrutura,
 * especialmente aqueles que se originam da camada de persistência (DAO) e não
 * são
 * diretamente erros de validação de negócio de uma entidade específica.
 *
 * Esta exceção pode ser usada para encapsular SQLExceptions ou outras exceções
 * de baixo nível, fornecendo uma camada de abstração mais amigável para a
 * lógica de negócio.
 */
public class ErroSistemaException extends RuntimeException {

    /**
     * Construtor que aceita uma mensagem de erro.
     * 
     * @param message A mensagem detalhando o erro do sistema.
     */
    public ErroSistemaException(String message) {
        super(message);
    }

    /**
     * Construtor que aceita uma mensagem de erro e a causa original (Throwable).
     * Útil para encapsular exceções de baixo nível, como SQLException, mantendo
     * a rastreabilidade da causa raiz.
     * 
     * @param message A mensagem detalhando o erro do sistema.
     * @param cause   A exceção original que causou este erro (ex: SQLException).
     */
    public ErroSistemaException(String message, Throwable cause) {
        super(message, cause);
    }
}