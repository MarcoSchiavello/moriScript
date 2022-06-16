package exceptions;

public class InvalidTokenException extends Exception{
    public InvalidTokenException (int line) { super("Invalid token in line " + line); }
}
