import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * The `gatorLibrary` class represents the main entry point for the Gator
 * Library Book Management System.
 * It reads instructions from an input file, performs corresponding library
 * actions, and writes the output to a file.
 */
public class gatorLibrary {

    // FileWriter for writing output to a file
    static FileWriter writer = null;

    // Instance of the GatorLibServices class for handling library actions
    static GatorLibServices gatorLibServices = null;

    /**
     * The main method that initializes the Gator Library Book Management System.
     *
     * @param args Command-line arguments, where the first argument is the input
     *             file name.
     */
    public static void main(String[] args) {

        try {
            String filename = "";
            if (args.length != 0) {
                filename = args[0];
            }
            RedBlackMethod rb = new RedBlackMethod();
            File inputFile = new File(filename);
            String outputFileName = filename.split("\\.", 2)[0] + "_" + "output_file.txt";
            writer = new FileWriter(outputFileName);

            gatorLibServices = new GatorLibServices(rb, writer);
            parsingInputFile(inputFile);
        } catch (Exception e) {

        }
    }

    /**
     * Reads the input file and performs the corresponding library actions based on
     * the instructions provided.
     *
     * @param inputFile The input file containing library action instructions.
     */
    public static void parsingInputFile(File inputFile) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isBlank()) {
                    String libraryActionToPerform = line.substring(0, line.indexOf('('));
                    if (LibraryActionConstant.QUIT.equals(libraryActionToPerform.trim())) {
                        gatorLibServices.performQuitAction();
                        break;
                    } else {
                        libraryActionPerformer(libraryActionToPerform.trim(), line);
                    }
                }
            }

            reader.close();
            writer.close();
        } catch (Exception e) {
            System.out.println("--->" + e.getMessage());
        }

    }

    /**
     * Performs the specified library action based on the provided action type and
     * data.
     *
     * @param libraryAction The type of library action to perform.
     * @param dataString    The data associated with the library action.
     */
    public static void libraryActionPerformer(String libraryAction, String dataString) {
        switch (libraryAction) {
            // Handle the PRINT_BOOK action
            case LibraryActionConstant.PRINT_BOOK: {
                String dataStringTrimmed = dataString.trim();
                String inputData = dataStringTrimmed.substring(dataStringTrimmed.indexOf('(') + 1,
                        dataStringTrimmed.length() - 1);
                gatorLibServices.performPrintBookAction(Integer.parseInt(inputData.trim()));

            }
                break;

            // Handle the PRINT_BOOKS action
            case LibraryActionConstant.PRINT_BOOKS: {
                String dataStringTrimmed = dataString.trim();
                String inputData = dataStringTrimmed.substring(dataStringTrimmed.indexOf('(') + 1,
                        dataStringTrimmed.length() - 1);
                String parseString[] = inputData.split(",", 2);
                gatorLibServices.performPrintBooksAction(Integer.parseInt(parseString[0].trim()),
                        Integer.parseInt(parseString[1].trim()));

            }
                break;

            // Handle the INSERT_BOOK action
            case LibraryActionConstant.INSERT_BOOK: {
                String dataStringTrimmed = dataString.trim();
                String inputData = dataStringTrimmed.substring(dataString.indexOf('(') + 1,
                        dataStringTrimmed.length() - 1);
                String parseString[] = inputData.split(",", 4);

                BookNode newBook = new BookNode(Integer.parseInt(parseString[0].trim()), parseString[1].trim(),
                        parseString[2].trim(),
                        parseString[3].trim().equalsIgnoreCase("\"Yes\"") ? true : false);

                gatorLibServices.performInsertBookAction(newBook);
            }
                break;
            // Handle the BORROW_BOOK action
            case LibraryActionConstant.BORROW_BOOK: {
                String dataStringTrimmed = dataString.trim();
                String inputData = dataStringTrimmed.substring(dataStringTrimmed.indexOf('(') + 1,
                        dataStringTrimmed.length() - 1);
                String parseString[] = inputData.split(",", 3);
                System.out.print(parseString[0]);
                System.out.print(parseString[1]);
                System.out.print(parseString[2]);
                gatorLibServices.performBorrowBookAction(
                        Integer.parseInt(parseString[0].trim()),
                        Integer.parseInt(parseString[1].trim()),
                        Integer.parseInt(parseString[2].trim()));

            }

                break;
            // Handle the RETURN_BOOK action
            case LibraryActionConstant.RETURN_BOOK: {
                String dataStringTrimmed = dataString.trim();
                String inputData = dataStringTrimmed.substring(dataStringTrimmed.indexOf('(') + 1,
                        dataStringTrimmed.length() - 1);
                String parseString[] = inputData.split(",", 2);

                gatorLibServices.performReturnBookAction(Integer.parseInt(parseString[0].trim()),
                        Integer.parseInt(parseString[1].trim()));
            }
                break;

            // Handle the DELETE_BOOK action
            case LibraryActionConstant.DELETE_BOOK: {
                String dataStringTrimmed = dataString.trim();
                String inputData = dataStringTrimmed.substring(dataStringTrimmed.indexOf('(') + 1,
                        dataStringTrimmed.length() - 1);
                gatorLibServices.performDeleteBookAction(Integer.parseInt(inputData.trim()));
            }
                break;

            // Handle the FIND_CLOSEST_BOOK action
            case LibraryActionConstant.FIND_CLOSEST_BOOK: {
                String dataStringTrimmed = dataString.trim();
                String inputData = dataStringTrimmed.substring(dataStringTrimmed.indexOf('(') + 1,
                        dataStringTrimmed.length() - 1);
                gatorLibServices.performFindClosestBookSearch(Integer.parseInt(inputData.trim()));
            }
                break;
            case LibraryActionConstant.FIND_COLOUR_FLIP_COUNT: {
                gatorLibServices.performColourFlipCountAction();
            }
                break;
            default:
                // Handle unknown action
                break;
        }
        return;
    }
}