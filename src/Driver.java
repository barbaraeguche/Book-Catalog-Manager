import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * This is the Driver class. This class has twelve instance variables, initializes the Book[] array(each grouped by genre) meant to be serialized
 * and the following methods: findIndex, openPrintWriter, closePrintWriter, do_part1, do_part2 and do_part3.
 */
public class Driver {
    public static void main(String[] args) {
        do_part1();
        do_part2();
        do_part3();

        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
        int syntaxErrors = tooFewFields + tooManyFields + unknownGenre + missingFields;
        int semanticErrors = badIsbn10 + badIsbn13 + badIsbnLength + badPrice + badYear;
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

        System.out.println("--------------------------------");
        System.out.println("Here are the details after processing all the files;");
        System.out.println("--------------------------------");
        System.out.printf("Total Books Created: %d", booksCreated);
        System.out.printf("\nTotal Syntax Errors: %d", syntaxErrors);
        System.out.printf("\nTotal Semantic Errors: %d", semanticErrors);
        System.out.println("\n----------------");
        System.out.printf("Total TooFewFields Errors: %d", tooFewFields);
        System.out.printf("\nTotal TooManyFields Errors: %d", tooManyFields);
        System.out.printf("\nTotal MissingField Errors: %d", missingFields);
        System.out.printf("\nTotal InvalidGenre Errors: %d", unknownGenre);
        System.out.println("\n----------------");
        System.out.printf("Total BadPrice Errors: %d", badPrice);
        System.out.printf("\nTotal BadISBN10 Errors: %d", badIsbn10);
        System.out.printf("\nTotal BadISBN13 Errors: %d", badIsbn13);
        System.out.printf("\nTotal BadISBNLength Errors: %d", badIsbnLength);
        System.out.printf("\nTotal BadYear Errors: %d", badYear);

        //prints the closing message
        System.out.print("\nGoodbye Mr. Booker! Hope to see you sometime later! :)");
        //delete the files
        deleteFiles();
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
    private static int tooFewFields = 0, tooManyFields = 0, missingFields = 0, unknownGenre = 0;
    private static int badPrice = 0, badIsbn10 = 0, badIsbn13 = 0, badIsbnLength = 0, badYear = 0;
    private static int current = 0, index = 0, booksCreated = 0;
    // ---------------------------------------------------------------- //
    private static final Map<String, String> validSyntaxFiles = new HashMap<>();
    static {
        validSyntaxFiles.put("CCB", "mytextfiles/Cartoons_Comics_Books.csv.txt");
        validSyntaxFiles.put("HCB", "mytextfiles/Hobbies_Collectibles_Books.csv.txt");
        validSyntaxFiles.put("MTV", "mytextfiles/Movies_TV.csv.txt");
        validSyntaxFiles.put("MRB", "mytextfiles/Music_Radio_Books.csv.txt");
        validSyntaxFiles.put("NEB", "mytextfiles/Nostalgia_Eclectic_Books.csv.txt");
        validSyntaxFiles.put("OTR", "mytextfiles/Old_Time_Radio.csv.txt");
        validSyntaxFiles.put("SSM", "mytextfiles/Sports_Sports_Memorabilia.csv.txt");
        validSyntaxFiles.put("TPA", "mytextfiles/Trains_Planes_Automobiles.csv.txt");
    }
    private static final Map<String, String> validSemanticSerFiles = new HashMap<>();
    static {
        validSemanticSerFiles.put("CCB", "myserializedfiles/Cartoons_Comics_Books.csv.ser.txt");
        validSemanticSerFiles.put("HCB", "myserializedfiles/Hobbies_Collectibles_Books.csv.ser.txt");
        validSemanticSerFiles.put("MTV", "myserializedfiles/Movies_TV.csv.ser.txt");
        validSemanticSerFiles.put("MRB", "myserializedfiles/Music_Radio_Books.csv.ser.txt");
        validSemanticSerFiles.put("NEB", "myserializedfiles/Nostalgia_Eclectic_Books.csv.ser.txt");
        validSemanticSerFiles.put("OTR", "myserializedfiles/Old_Time_Radio.csv.ser.txt");
        validSemanticSerFiles.put("SSM", "myserializedfiles/Sports_Sports_Memorabilia.csv.ser.txt");
        validSemanticSerFiles.put("TPA", "myserializedfiles/Trains_Planes_Automobiles.csv.ser.txt");
    }
    private static final Map<String, String> errorFiles = new HashMap<>();
    static {
        errorFiles.put("SYN", "myerrorfiles/syntax_error_file.txt");
        errorFiles.put("SEM", "myerrorfiles/semantic_error_file.txt");
    }
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
    private static final List<Map<String, String>> filesToDelete = new ArrayList<>();
    static {
        filesToDelete.add(validSyntaxFiles);
        filesToDelete.add(validSemanticSerFiles);
        filesToDelete.add(errorFiles);
    }
    // ---------------------------------------------------------------- //
    /**
     * This method write the syntax error message to the specified file
     * @param textFile the file where the syntax error was caught in
     * @param e the syntax error message
     * @param line the record with the error
     * @return a formatted string describing the syntax error
     */
    private static String synError(String textFile, String e, String line) {
        return String.format("Syntax error in file: %s%n====================%nError: %s%nRecord: %s%n%n", textFile, e, line);
    }

    /**
     * This method write the semantic error message to the specified file
     * @param textFile the file where the semantic error was caught
     * @param e the semantic error message
     * @param line the record with the error
     * @return a formatted string describing the semantic error
     */
    private static String semError(String textFile, String e, String line) {
        return String.format("Syntax error in file: %s%n====================%nError: %s%nRecord: %s%n%n", textFile, e, line);
    }

    /**
     * This method opens the stream for all the file objects
     * @param files the file to write to
     * @param toWrite the message to write to the file
     * @throws FileNotFoundException if the file cannot be found
     */
    private static void openPrintWriter(String files, String toWrite) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new FileOutputStream(files, true));
        pw.println(toWrite);
        pw.close();
    }

    /**
     * This method deletes all the text files used in this program
     */
    private static void deleteFiles() {
        for(Map<String, String> fileMap: Driver.filesToDelete) {
            for(String filePath: fileMap.values()) {
                File file = new File(filePath);
                file.delete();
            }
        }
    }

    /**
     * This method serializes all the text files
     * @param files the file where the serialized text goes
     * @param array an array of Book objects to serialize
     */
    private static void serializeAll(String files, ArrayList<Book> array) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(files));
            out.writeObject(array);
            out.flush();
            out.close();
        } catch (IOException i) {
            System.out.println(array + " not serialized");
        }
    }

    /**
     * This method deserializes all the text files
     * @param files the file containing the serialized Book objects
     * @return a list containing the deserialized Book objects
     */
    private static List<Book> deserializeAll(String files) {
        List<Book> array = new ArrayList<>();
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(files));
            array = (List<Book>)in.readObject();
            in.close();
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found");
        } catch (IOException i) {
            System.out.println("Array not deserialized");
        }
        return array;
    }

    /**
     * This method validates user's input and executes the program accordingly
     * @param number the user input
     * @param array an arraylist containing the deserialized Book objects
     */
    private static void validateCommands(int number, List<Book> array) {
        if(number > 0) {
            try {
                for(int i = current; i < current + number; i++) {
                    System.out.println(array.get(i));
                    index = i;
                }
                current = index;
            } catch(ArrayIndexOutOfBoundsException e) {
                current = index;
                System.out.println("EOF reached.");
            }
        } else if(number < 0) {
            try {
                if(current == 0) { throw new ArrayIndexOutOfBoundsException(); }
                else {
                    if(current + (number + 1) < 0) {
                        System.out.println("BOF reached.");
                        for(int i = 0; i <= current; i++) {
                            System.out.println(array.get(i));
                            index = 0;
                        }
                    } else {
                        for(int i = current + number + 1; i <= current; i++) {
                            System.out.println(array.get(i));
                            index = current + number + 1;
                        }
                    }
                }
                current = index;
            } catch(ArrayIndexOutOfBoundsException e) {
                current = 0;
                System.out.println("BOF reached.");
                System.out.println(array.get(current));
            }
        }
    }
    // ---------------------------------------------------------------- //

    /**
     * This function validates syntax, partitions book records based on genre.
     */
    private static void do_part1() {
        Scanner scan, sc; String title, author, price, isbn, genre, year = "";

        try {
            scan = new Scanner(new FileInputStream("part1_input_file_names.txt"));  //read the file containing other files
            int number = scan.nextInt();

            while(scan.hasNextLine()){
                String textFile = scan.nextLine(); //read each file
                if(textFile.isEmpty()) continue;

                try {
                    sc = new Scanner(new FileInputStream("yourtextfiles/" + textFile)); //read individual files

                    while(sc.hasNextLine()) {
                        String line = sc.nextLine().trim();  //read each line of the file

                        if(line.startsWith("\"")) {  //if the line stats with a quotation mark, skip all commas(,) with the quotation, and split where the quotation mark ends
                            int firstQuote = line.indexOf("\"");
                            int secondQuote = line.indexOf("\"", firstQuote + 1);

                            title = line.substring(firstQuote + 1, secondQuote);
                            String[] withoutTitle = line.substring(secondQuote + 2).split(",");
                            if(withoutTitle.length < 5) {
                                author = withoutTitle[0]; price = withoutTitle[1]; isbn = withoutTitle[2]; genre = withoutTitle[3];
                            } else {
                                author = withoutTitle[0]; price = withoutTitle[1]; isbn = withoutTitle[2]; genre = withoutTitle[3]; year = withoutTitle[4];
                            }

                            if(withoutTitle.length > 5 || line.endsWith(",")) { // too many fields
                                try {
                                    throw new TooManyFieldsException("Too Many Fields");
                                } catch (TooManyFieldsException e) {
                                    tooManyFields++;
                                    openPrintWriter(errorFiles.get("SYN"), synError(textFile, String.valueOf(e), line));
                                    continue;
                                }
                            } else if(withoutTitle.length < 5) { // too few fields
                                try {
                                    throw new TooFewFieldsException("Too Few Fields");
                                } catch (TooFewFieldsException e) {
                                    tooFewFields++;
                                    openPrintWriter(errorFiles.get("SYN"), synError(textFile, String.valueOf(e), line));
                                    continue;
                                }
                            }
                        } else {
                            String[] withTitle = line.split(",");
                            if(withTitle.length < 6) {
                                title = withTitle[0]; author = withTitle[1]; price = withTitle[2]; isbn = withTitle[3]; genre = withTitle[4];
                            } else {
                                title = withTitle[0]; author = withTitle[1]; price = withTitle[2]; isbn = withTitle[3]; genre = withTitle[4]; year = withTitle[5];
                            }

                            if(withTitle.length > 6 || line.endsWith(",")) { // too many fields
                                try {
                                    throw new TooManyFieldsException("Too Many Fields");
                                } catch (TooManyFieldsException e) {
                                    tooManyFields++;
                                    openPrintWriter(errorFiles.get("SYN"), synError(textFile, String.valueOf(e), line));
                                    continue;
                                }
                            } else if(withTitle.length < 6) { // too few fields
                                try {
                                    throw new TooFewFieldsException("Too Few Fields");
                                } catch (TooFewFieldsException e) {
                                    tooFewFields++;
                                    openPrintWriter(errorFiles.get("SYN"), synError(textFile, String.valueOf(e), line));
                                    continue;
                                }
                            }
                        }

                        try {
                            if(title.isBlank()) { //missing title
                                throw new MissingFieldException("Missing Title");
                            } else if(author.isBlank()) { //missing author
                                throw new MissingFieldException("Missing Author");
                            } else if(price.isBlank()) { //missing price
                                throw new MissingFieldException("Missing Price");
                            } else if(isbn.isBlank()) { //missing isbn
                                throw new MissingFieldException("Missing ISBN");
                            } else if(genre.isBlank()) { //missing genre
                                throw new MissingFieldException("Missing Genre");
                            } else if(!validSyntaxFiles.containsKey(genre)) { //invalid genre
                                try {
                                    throw new UnknownGenreException("Unknown Genre");
                                } catch (UnknownGenreException e) {
                                    unknownGenre++;
                                    openPrintWriter(errorFiles.get("SYN"), synError(textFile, String.valueOf(e), line));
                                    continue;
                                }
                            } else if(year.isBlank()) { //missing genre
                                throw new MissingFieldException("Missing Year");
                            }

                            openPrintWriter(validSyntaxFiles.get(genre), line); // syntax-free text
                        } catch (MissingFieldException e) {
                            missingFields++;
                            openPrintWriter(errorFiles.get("SYN"), synError(textFile, String.valueOf(e), line));
                        }
                    }
                    sc.close();
                } catch(FileNotFoundException e) {
                    System.out.println("Inner file not found for reading.");
                }
            }
            scan.close();
        } catch(FileNotFoundException e) {
            System.out.println("File not found for reading.");
        }
    }

    /**
     * This function validates semantics, reads each genre files into lists of Book objects, then serializes each lists into binary files.
     */
    private static void do_part2() {
        ArrayList<Book> ccbSerial = new ArrayList<>(), hcbSerial = new ArrayList<>(), mtvSerial = new ArrayList<>(), mrbSerial = new ArrayList<>(),
                nebSerial = new ArrayList<>(), otrSerial = new ArrayList<>(), ssmSerial = new ArrayList<>(), tpaSerial = new ArrayList<>();
        Scanner scan; Book book = null; String title, author, price, isbn, genre, year;

        for(String value: validSyntaxFiles.values()) {
            try {
                scan = new Scanner(new FileInputStream(value));

                while(scan.hasNextLine()) {
                    String line = scan.nextLine().trim();

                    if(line.startsWith("\"")) {  //line does not start with quotation marks, so just split at each comma(,)
                        int firstQuote = line.indexOf("\"");
                        int secondQuote = line.indexOf("\"", firstQuote + 1);

                        title = line.substring(firstQuote + 1, secondQuote);
                        String[] withoutTitle = line.substring(secondQuote + 2).split(",");
                        author = withoutTitle[0]; price = withoutTitle[1]; isbn = withoutTitle[2]; genre = withoutTitle[3]; year = withoutTitle[4];
                    } else {
                        String[] withTitle = line.split(",");
                        title = withTitle[0]; author = withTitle[1]; price = withTitle[2]; isbn = withTitle[3]; genre = withTitle[4]; year = withTitle[5];
                    }

                    try {
                        book = new Book(title, author, Double.parseDouble(price), isbn, genre, Integer.parseInt(year));
                    } catch (BadPriceException e) {
                        badPrice++;
                        openPrintWriter(errorFiles.get("SEM"), semError(value.substring(12), String.valueOf(e), line));
                        continue;
                    } catch (BadIsbn10Exception e) {
                        badIsbn10++;
                        openPrintWriter(errorFiles.get("SEM"), semError(value.substring(12), String.valueOf(e), line));
                        continue;
                    } catch (BadIsbn13Exception e) {
                        badIsbn13++;
                        openPrintWriter(errorFiles.get("SEM"), semError(value.substring(12), String.valueOf(e), line));
                        continue;
                    } catch (BadIsbnLengthException e) {
                        badIsbnLength++;
                        openPrintWriter(errorFiles.get("SEM"), semError(value.substring(12), String.valueOf(e), line));
                        continue;
                    } catch (UnknownGenreException e) {
                        // already handled
                    } catch (BadYearException e) {
                        badYear++;
                        openPrintWriter(errorFiles.get("SEM"), semError(value.substring(12), String.valueOf(e), line));
                        continue;
                    }

                    switch(value) {  //add all syntax-free and semantic-free Book objects in their respective genre list
                        case "mytextfiles/Cartoons_Comics_Books.csv.txt" -> ccbSerial.add(book);
                        case "mytextfiles/Hobbies_Collectibles_Books.csv.txt" -> hcbSerial.add(book);
                        case "mytextfiles/Movies_TV.csv.txt" -> mtvSerial.add(book);
                        case "mytextfiles/Music_Radio_Books.csv.txt" -> mrbSerial.add(book);
                        case "mytextfiles/Nostalgia_Eclectic_Books.csv.txt" -> nebSerial.add(book);
                        case "mytextfiles/Old_Time_Radio.csv.txt" -> otrSerial.add(book);
                        case "mytextfiles/Sports_Sports_Memorabilia.csv.txt" -> ssmSerial.add(book);
                        case "mytextfiles/Trains_Planes_Automobiles.csv.txt" -> tpaSerial.add(book);
                    }
                }
            } catch(FileNotFoundException e) {
                System.out.println("File not found for reading.");
            }
        }

        for(String keys: validSemanticSerFiles.keySet()) {  //serialize all lists
            switch(keys) {
                case "CCB" -> serializeAll(validSemanticSerFiles.get(keys), ccbSerial);
                case "HCB" -> serializeAll(validSemanticSerFiles.get(keys), hcbSerial);
                case "MTV" -> serializeAll(validSemanticSerFiles.get(keys), mtvSerial);
                case "MRB" -> serializeAll(validSemanticSerFiles.get(keys), mrbSerial);
                case "NEB" -> serializeAll(validSemanticSerFiles.get(keys), nebSerial);
                case "OTR" -> serializeAll(validSemanticSerFiles.get(keys), otrSerial);
                case "SSM" -> serializeAll(validSemanticSerFiles.get(keys), ssmSerial);
                case "TPA" -> serializeAll(validSemanticSerFiles.get(keys), tpaSerial);
            }
        }
    }

    /**
     * This function reads the binary files, deserializes each file into a corresponding genre list, and then provides an interactive program to allow the user to navigate the arrays.
     */
    private static void do_part3() {
        List<Book> ccbSerial = new ArrayList<>(), hcbSerial = new ArrayList<>(),  mtvSerial = new ArrayList<>(), mrbSerial = new ArrayList<>(),
                nebSerial = new ArrayList<>(), otrSerial = new ArrayList<>(), ssmSerial = new ArrayList<>(), tpaSerial = new ArrayList<>();
        Scanner scan = new Scanner(System.in); String selectedFile = "";

        for(String keys: validSemanticSerFiles.keySet()) {  //deserialize all lists
            switch(keys) {
                case "CCB" -> ccbSerial = deserializeAll(validSemanticSerFiles.get(keys));
                case "HCB" -> hcbSerial = deserializeAll(validSemanticSerFiles.get(keys));
                case "MTV" -> mtvSerial = deserializeAll(validSemanticSerFiles.get(keys));
                case "MRB" -> mrbSerial = deserializeAll(validSemanticSerFiles.get(keys));
                case "NEB" -> nebSerial = deserializeAll(validSemanticSerFiles.get(keys));
                case "OTR" -> otrSerial = deserializeAll(validSemanticSerFiles.get(keys));
                case "SSM" -> ssmSerial = deserializeAll(validSemanticSerFiles.get(keys));
                case "TPA" -> tpaSerial = deserializeAll(validSemanticSerFiles.get(keys));
            }
        }
        booksCreated = ccbSerial.size() + hcbSerial.size() + mtvSerial.size() + mrbSerial.size() + nebSerial.size()
                + otrSerial.size() + ssmSerial.size() + tpaSerial.size();

        Map<String, List<Book>> allLists = new HashMap<>();
        allLists.put("Cartoons_Comics_Books.csv.ser", ccbSerial);
        allLists.put("Hobbies_Collectibles_Books.csv.ser", hcbSerial);
        allLists.put("Movies_TV.csv.ser", mtvSerial);
        allLists.put("Music_Radio_Books.csv.ser", mrbSerial);
        allLists.put("Nostalgia_Eclectic_Books.csv.ser", nebSerial);
        allLists.put("Old_Time_Radio.csv.ser", otrSerial);
        allLists.put("Sports_Sports_Memorabilia.csv.ser", ssmSerial);
        allLists.put("Trains_Planes_Automobiles.csv.ser", tpaSerial);

        // prints the welcome message
        System.out.println("""
                \n~~~~~ WELCOME, MR. BOOKER! ~~~~~
                I will be helping you navigate through your book titles, categorized by genre, as well as providing a facility for identifying and removing invalid book records :)""");

        while(true) {
            System.out.println("\n----------------------------- \n          Main Menu          \n-----------------------------");
            System.out.printf(" v  View the selected file: %s", selectedFile);
            System.out.println("\n s  Select a file to view");
            System.out.println(" x  Exit");
            System.out.println("-----------------------------");

            //prompting user to enter a choice from the menu above
            System.out.print("Enter Your Choice: ");
            String code = scan.next();

            //--------------------------------------------------------------------------------------------------
            if(code.equals("v")) {
                if(selectedFile.isEmpty()) System.out.println("No file selected.");
                else {
                    System.out.printf("viewing: %s", selectedFile);
                    while(true) {
                        try {
                            System.out.print("\n\nEnter an integer number: ");
                            int n = scan.nextInt();

                            if(n == 0) { System.out.println("Viewing ended."); break; }
                            else {
                                for(String key: allLists.keySet()) {
                                    if(key.equals(selectedFile.substring(0, selectedFile.indexOf(" ")))) {
                                        validateCommands(n, allLists.get(key));
                                    }
                                }
                            }
                        } catch(InputMismatchException e) {
                            System.out.println("Enter an integer.");
                        }
                    }
                }
            }

            //--------------------------------------------------------------------------------------------------
            else if(code.equals("s")) {
                System.out.println("\n------------------------------ \n        File Sub-Menu          \n------------------------------");
                System.out.printf(" 1  Cartoons_Comics_Books.csv.ser           (%d records)", ccbSerial.size());
                System.out.printf("\n 2  Hobbies_Collectibles_Books.csv.ser      (%d records)", hcbSerial.size());
                System.out.printf("\n 3  Movies_TV.csv.ser                       (%d records)", mtvSerial.size());
                System.out.printf("\n 4  Music_Radio_Books.csv.ser               (%d records)", mrbSerial.size());
                System.out.printf("\n 5  Nostalgia_Eclectic_Books.csv.ser        (%d records)", nebSerial.size());
                System.out.printf("\n 6  Old_Time_Radio.csv.ser                  (%d records)", otrSerial.size());
                System.out.printf("\n 7  Sports_Sports_Memorabilia.csv.ser       (%d records)", ssmSerial.size());
                System.out.printf("\n 8  Trains_Planes_Automobiles.csv.ser       (%d records)", tpaSerial.size());
                System.out.println("\n 9  Exit");
                System.out.println("------------------------------");

                //prompting user to enter a choice from the menu above
                System.out.print("Enter Your Choice: ");
                int choice = scan.nextInt();

                if(choice == 1) { selectedFile = String.format("Cartoons_Comics_Books.csv.ser (%d records)", ccbSerial.size()); }
                else if(choice == 2) { selectedFile = String.format("Hobbies_Collectibles_Books.csv.ser (%d records)", hcbSerial.size()); }
                else if(choice == 3) { selectedFile = String.format("Movies_TV.csv.ser (%d records)", mtvSerial.size()); }
                else if(choice == 4) { selectedFile = String.format("Music_Radio_Books.csv.ser (%d records)", mrbSerial.size()); }
                else if(choice == 5) { selectedFile = String.format("Nostalgia_Eclectic_Books.csv.ser (%d records)", nebSerial.size()); }
                else if(choice == 6) { selectedFile = String.format("Old_Time_Radio.csv.ser (%d records)", otrSerial.size()); }
                else if(choice == 7) { selectedFile = String.format("Sports_Sports_Memorabilia.csv.ser (%d records)", ssmSerial.size()); }
                else if(choice == 8) { selectedFile = String.format("Trains_Planes_Automobiles.csv.ser (%d records)", tpaSerial.size()); }
                else if(choice == 9) { System.out.println("File Sub-Menu Exited"); break; }
                else { System.out.println("Sorry that is an invalid choice. Try again.\n"); }
            }

            //--------------------------------------------------------------------------------------------------
            else if(code.equals("x")) { System.out.println("Interactive Main Menu terminated.\n"); break; }

            //--------------------------------------------------------------------------------------------------
            else { System.out.println("Sorry that is an invalid choice. Try again."); }
        }
    }
}
