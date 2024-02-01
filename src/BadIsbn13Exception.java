
/**
 * This is the Bad ISBN-13 Exception class. It is thrown when an ISBN of length 13 is not a multiple of 10.
 */
public class BadIsbn13Exception extends Exception {

    /**
     * This is the default constructor
     */
    public BadIsbn13Exception() {
        super();
    }

    /**
     * This is the parameterized constructor
     * @param message the message to be displayed
     */
    public BadIsbn13Exception(String message) {
        super(message);
    }
}
