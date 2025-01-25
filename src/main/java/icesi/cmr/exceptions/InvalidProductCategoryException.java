package icesi.cmr.exceptions;

public class InvalidProductCategoryException extends RuntimeException {

    public InvalidProductCategoryException(String message) {
        super(message);
    }

    public InvalidProductCategoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
