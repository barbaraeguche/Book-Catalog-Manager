
/**
 * This is the Too Few Fields Exception class. It is thrown a book has few fields ie when the fields of such book is less than 6.
 */
public class TooFewFieldsException extends Exception {

    /**
     * This is the default constructor
     */
    public TooFewFieldsException() {
        super();
    }

    /**
     * This is the parameterized constructor
     * @param message the message to be displayed
     */
    public TooFewFieldsException(String message) {
        super(message);
    }
}
