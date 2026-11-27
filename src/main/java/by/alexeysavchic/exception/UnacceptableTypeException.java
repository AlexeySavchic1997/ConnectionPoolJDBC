package by.alexeysavchic.exception;

public class UnacceptableTypeException extends ApplicationException {
    public UnacceptableTypeException(String message, Throwable cause, boolean supressed, boolean writableStack) {
        super(message, cause, supressed, writableStack);
    }
}
