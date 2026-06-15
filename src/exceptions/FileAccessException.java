package exceptions;

public class FileAccessException extends Exception {

    public FileAccessException(String filename, String reason) {
        super("Cannot access '" + filename + "': " + reason);
    }

    public FileAccessException(String filename, String reason, Throwable cause) {
        super("Cannot access '" + filename + "': " + reason, cause);
    }
}
