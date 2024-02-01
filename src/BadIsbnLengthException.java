
/**
 * This is the Bad ISBN Length Exception class. Although not amongst the exceptions asked for in the assignment,
 * It is thrown when an ISBN length is neither a 10-digit nor a 13-digit.
 */
public class BadIsbnLengthException extends Exception {

    /**
     * This is the default constructor
     */
    public BadIsbnLengthException() {
        super();
    }

    /**
     * This is the parameterized constructor
     * @param message the message to be displayed
     */
    public BadIsbnLengthException(String message) {
        super(message);
    }
}
