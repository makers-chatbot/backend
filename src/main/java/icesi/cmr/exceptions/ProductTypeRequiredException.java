package icesi.cmr.exceptions;

public class ProductTypeRequiredException extends RuntimeException{

    public ProductTypeRequiredException() {
        super("Product type is required");
    }

    public ProductTypeRequiredException(String message) {
        super(message);
    }

}
