package exceptions;

public class CursoInvalidoException extends RuntimeException {
    public CursoInvalidoException(String message) {
        super(message);
    }

    public CursoInvalidoException(String message, Throwable cause) {
        super(message, cause);
    }

}
