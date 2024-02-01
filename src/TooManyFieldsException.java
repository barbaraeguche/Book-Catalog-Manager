
/**
 * This is the Too Many Fields Exception class. It is thrown a book has many fields ie when the fields of such book is greater than 6.
 */
public class TooManyFieldsException extends Exception {

    /**
     * This is the default constructor
     */
    public TooManyFieldsException() {
        super();
    }

    /**
     * This is the parameterized constructor
     * @param message the message to be displayed
     */
    public TooManyFieldsException(String message) {
        super(message);
    }
}