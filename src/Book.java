import java.io.Serializable;
import java.util.Arrays;

/**
 * This is the Book class which implements Serializable. This class has six instance variables and the following methods: default constructor,
 * parameterized constructor, copy constructor, getters and setters, equals and toString.
 */
public class Book implements Serializable {

    //instance variables
    private String title, author, isbn, genre; private double price; private int year;

    /**
     * This is the default constructor
     */
    public Book() {}

    /**
     * This is the parameterized constructor to initialize the Book object
     * @param title the title of the book
     * @param author the author of the book
     * @param price the price of the book
     * @param isbn the isbn of the book
     * @param genre the genre of the book
     * @param year the year the book was published
     * @throws BadPriceException when a price is not positive ie negative
     * @throws BadIsbn10Exception when a 10-digit ISBN is not valid
     * @throws BadIsbn13Exception when a 13-digit ISBN is not valid
     * @throws BadIsbnLengthException when an ISBN length is neither 10-digit nor 13-digit
     * @throws UnknownGenreException when a genre is not among the valid specified genre
     * @throws BadYearException when a year is not within the range of 1998 to 2010
     */
    public Book(String title, String author, double price, String isbn, String genre, int year) throws BadPriceException, BadIsbn10Exception, BadIsbn13Exception, BadIsbnLengthException, UnknownGenreException, BadYearException {
        this.title = title;
        this.author = author;
        this.setPrice(price);
        this.setIsbn(isbn);
        this.setGenre(genre);
        this.setYear(year);
    }

    /**
     * This is the copy constructor
     * @param book the Book object to be copied
     * @throws BadPriceException when a price is not positive ie negative
     * @throws BadIsbn10Exception when a 10-digit ISBN is not valid
     * @throws BadIsbn13Exception when a 13-digit ISBN is not valid
     * @throws BadIsbnLengthException when an ISBN length is neither 10-digit nor 13-digit
     * @throws UnknownGenreException when a genre is not among the valid specified genre
     * @throws BadYearException when a year is not within the range of 1998 to 2010
     */
    public Book(Book book) throws BadPriceException, BadIsbn10Exception, BadIsbn13Exception, BadIsbnLengthException, UnknownGenreException, BadYearException {
        this.setTitle(book.getTitle());
        this.setAuthor(book.getAuthor());
        this.setPrice(book.getPrice());
        this.setIsbn(book.getIsbn());
        this.setGenre(book.getGenre());
        this.setYear(book.getYear());
    }

    /**
     * An accessor method to get the title of the book
     * @return the title of the book
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * A mutator method to set the title of the book
     * @param title the title of the book to be set to
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * An accessor method to get the author of the book
     * @return the author of the book
     */
    public String getAuthor() {
        return this.author;
    }

    /**
     * A mutator method to set the author of the book
     * @param author the author of the book to be set to
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * An accessor method to get the price of the book
     * @return the price of the book
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * A mutator method to set the price of the book
     * @param price the price of the book to be set to
     * @throws BadPriceException when a price is not positive ie negative
     */
    public void setPrice(double price) throws BadPriceException {
        if(price < 0) throw new BadPriceException("Bad Price");
        else this.price = price;
    }

    /**
     * An accessor method to get the isbn of the book
     * @return the isbn of the book
     */
    public String getIsbn() {
        return this.isbn;
    }

    /**
     * A mutator method to set the isbn of the book
     * @param isbn the isbn of the book to be set to
     */
    public void setIsbn(String isbn) throws BadIsbnLengthException, BadIsbn10Exception, BadIsbn13Exception {
        this.isbn = validateIsbn1013X(isbn);
    }

    /**
     * An accessor method to get the genre of the book
     * @return the genre of the book
     */
    public String getGenre() {
        return this.genre;
    }

    /**
     * A mutator method to set the genre of the book
     * @param genre the genre of the book to be set to
     * @throws UnknownGenreException when a genre is not among the valid specified genre
     */
    public void setGenre(String genre) throws UnknownGenreException {
        String[] validGenre = {"CCB", "HCB", "MTV", "MRB", "NEB", "OTR", "SSM", "TPA"};
        if(!Arrays.asList(validGenre).contains(genre.toUpperCase())) throw new UnknownGenreException("Unknown Genre");
        else this.genre = genre;
    }

    /**
     * An accessor method to get the year the book was published
     * @return the year the book was published
     */
    public int getYear() {
        return this.year;
    }

    /**
     * A mutator method to set the year the book was published
     * @param year the year the book was published to be set to
     * @throws BadYearException when a year is not within the range of 1998 to 2010
     */
    public void setYear(int year) throws BadYearException {
        if(year < 1995 || year > 2010) throw new BadYearException("Bad Year");
        else this.year = year;
    }

    /**
     * This is the equals method that checks if two objects are equal in terms of class and instance variables
     * @param obj object being compared to
     * @return true if two objects are of the same class and have equal values of all class attributes, and false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) return false;

        Book book = (Book) obj;
        return (title.equals(book.title) && author.equals(book.author) && Double.compare(price, book.price) == 0
                && isbn.equals(book.isbn) && genre.equals(book.genre) && year == book.year);
    }

    /**
     * This is the toString method that returns a string representation of the Book object
     * @return the title, author, price, isbn, genre and the publication year of a Book object
     */
    @Override
    public String toString() {
        return this.title + ", " + this.author + ", " + this.price + ", " + this.isbn + ", " + this.genre + ", " + this.year;
    }

    /**
     * This method validates all the isbn fields of the Book object
     * @param isbn the isbn of the book to be set to
     * @return a validated isbn number
     * @throws BadIsbn10Exception when a 10-digit ISBN is not valid
     * @throws BadIsbn13Exception when a 13-digit ISBN is not valid
     * @throws BadIsbnLengthException when an ISBN length is neither 10-digit nor 13-digit
     */
    private String validateIsbn1013X(String isbn) throws BadIsbn10Exception, BadIsbn13Exception, BadIsbnLengthException {
        int weight = 10, sum = 0;

        if(isbn.length() == 10) {
            if(isbn.charAt(isbn.length() - 1) == 'X') throw new BadIsbn10Exception("Invalid ISBN-10");
            for(int i = 0; i < isbn.length(); i++) {
                sum += weight * (int)isbn.charAt(i);
                weight--;
            }
            if(sum % 11 != 0) throw new BadIsbn10Exception("Invalid ISBN-10");
        }
        else if(isbn.length() == 13) {
            if(isbn.charAt(isbn.length() - 1) == 'X') throw new BadIsbn13Exception("Invalid ISBN-13");
            for(int i = 0; i < isbn.length(); i++) {
               if(i % 2 != 0){
                   sum += 3 * (int)isbn.charAt(i);
                   continue;
               }
               sum += isbn.charAt(i);
            }
            if(sum % 10 != 0) throw new BadIsbn13Exception("Invalid ISBN-13");
        }
        else throw new BadIsbnLengthException("ISBN length not a 10 or 13 digit");
        return isbn;
    }
}