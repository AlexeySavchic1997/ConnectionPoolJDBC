package by.alexeysavchic.exception;

public class FileDidntExistException extends ApplicationException {
    public FileDidntExistException(String message, Throwable cause, boolean supressed, boolean writableStack) {
        super(message, cause, supressed, writableStack);
    }
}

