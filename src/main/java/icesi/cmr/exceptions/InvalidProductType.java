package icesi.cmr.exceptions;

public class InvalidProductType extends RuntimeException {

    public InvalidProductType() {
        super("Invalid product type");
    }

    public InvalidProductType(String message) {
        super(message);
    }
}
