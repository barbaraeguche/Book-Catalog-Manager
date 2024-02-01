
/**
 * This is the Bad ISBN-10 Exception class. It is thrown when an ISBN of length 10 is not a multiple of 11.
 */
public class BadIsbn10Exception extends Exception {

    /**
     * This is the default constructor
     */
    public BadIsbn10Exception() {
        super();
    }

    /**
     * This is the parameterized constructor
     * @param message the message to be displayed
     */
    public BadIsbn10Exception(String message) {
        super(message);
    }
}