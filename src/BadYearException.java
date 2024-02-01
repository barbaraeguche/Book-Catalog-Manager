
/**
 * This is the Bad Year Exception class. It is thrown when the publication year of a book doesn't fall between the range of [1995, 2010].
 */
public class BadYearException extends Exception {

    /**
     * This is the default constructor
     */
    public BadYearException() {
        super();
    }

    /**
     * This is the parameterized constructor
     * @param message the message to be displayed
     */
    public BadYearException(String message) {
        super(message);
    }
}
