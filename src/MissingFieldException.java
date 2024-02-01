
/**
 * This is the Missing Field Exception class. It is thrown when a field of in a book is missing.
 */
public class MissingFieldException extends Exception {

    /**
     * This is the default constructor
     */
    public MissingFieldException() {
        super();
    }

    /**
     * This is the parameterized constructor
     * @param message the message to be displayed
     */
    public MissingFieldException(String message) {
        super(message);
    }
}
