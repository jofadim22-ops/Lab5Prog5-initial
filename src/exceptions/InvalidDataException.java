package exceptions;
import exceptions.InvalidDataException;

public class InvalidDataException extends Exception {

    public InvalidDataException(String message) {
        super(message);
    }

    public InvalidDataException(String field, String expected, String actual) {
        super("Field '" + field + "': expected " + expected + ", got '" + actual + "'");
    }

    public InvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
