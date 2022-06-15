package exceptions;

public class InvalidFileException extends RuntimeException {
    public InvalidFileException(String msg) { super(msg); }
}
