
/**
 * This is the Bad Price Exception class. It is thrown when the price of a book is negative.
 */
public class BadPriceException extends Exception {

    /**
     * This is the default constructor
     */
    public BadPriceException() {
        super();
    }

    /**
     * This is the parameterized constructor
     * @param message the message to be displayed
     */
    public BadPriceException(String message) {
        super(message);
    }
}