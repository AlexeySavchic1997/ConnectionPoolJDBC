package by.alexeysavchic.exception;

public class ApplicationException extends RuntimeException
{
    public ApplicationException(String message, Throwable cause, boolean supressed, boolean writableStack) {
        super(message, cause, supressed, writableStack);
    }
}
