package exceptions;

public class AlunoInvalidoException extends RuntimeException {
    public AlunoInvalidoException(String message) {
        super(message);
    }

    public AlunoInvalidoException(String message, Throwable cause) {
        super(message, cause);
    }
}
