package by.alexeysavchic.exception;

public class CannotReadPropertyFileException extends ApplicationException {
  public CannotReadPropertyFileException(String message, Throwable cause, boolean supressed, boolean writableStack) {
    super(message, cause, supressed, writableStack);
  }
}