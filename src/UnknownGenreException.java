
/**
 * This is the Unknown Genre Exception class. It is thrown a book has a genre that is not part of the valid book genres.
 */
public class UnknownGenreException extends Exception {

    /**
     * This is the default constructor
     */
    public UnknownGenreException() {
        super();
    }

    /**
     * This is the parameterized constructor
     * @param message the message to be displayed
     */
    public UnknownGenreException(String message) {
        super(message);
    }
}
