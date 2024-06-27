package exceptions;

public class ManagerLoadException extends RuntimeException {
    public ManagerLoadException(final String message, Throwable cause) {
        super(message, cause);
    }
}
