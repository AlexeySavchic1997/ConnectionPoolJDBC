package by.alexeysavchic.exception;

public class PoolConnectionException extends ApplicationException
{
    public PoolConnectionException(String message, Throwable cause, boolean supressed, boolean writableStack) {
        super(message, cause, supressed, writableStack);
    }
}
