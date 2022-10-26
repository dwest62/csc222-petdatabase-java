/*
 * Author: James West
 * Date: 10.23.2022
 * Class: Fall 100 CSC222 Intro Programming with Java
 * Assignment: Assignment6 - Pet Database Version 2
 * Description: Represents a Pet Database which saves, displays, and modifies user submitted pet data.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a Pet Database which saves, displays, and modifies user submitted pet data.
 */
public class JamesWestPetDatabaseV2
{
    static final public int CAPACITY = 5; // Capacity of database
    final private static String FILE_NAME = "pets.txt"; // File in which pet data will be retrieved and stored
    final private static File FILE = new File(FILE_NAME); // File object created from file name
    final public static ArrayList<Pet> PETS = new ArrayList<>(); // Temporary Pet storage
    
    // CLI display table field names and widths
    final private static ArrayList<Field> TABLE_FIELDS = new ArrayList<>()
    {
        {
            add(new Field(4, "ID"));
            add(new Field(6, "Name"));
            add(new Field(5, "AGE"));
        }
    };
    // Table builder initiated with above fields
    final public static TableBuilder TABLE_BUILDER = new TableBuilder(TABLE_FIELDS);
    
    /**
     * Main driver function
     * @param args not utilized
     */
    public static void main(String[] args)
    {
        
        loadDatabase();
        try
        {
            PetDatabaseCLI.run();
        } catch (Exception e)
        {
            System.out.println("Unexpected error: " + e.getMessage());
        } finally
        {
            saveToDatabase();
        }

    }
    
    /**
     * Load information from file given by filename constant and add to temporary app storage
     */
    public static void loadDatabase()
    {
        try
        {
            String data = fileToString(FILE);
            String[] lines = data.split("\n");
            for(String line: lines)
            {
                String[] input = line.split(" ");
                PETS.add(
                        new Pet(
                                input[0],
                                Integer.parseInt(input[1]),
                                Integer.parseInt(input[2])
                        )
                );
            }

        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Save temporary storage to file given by filename constant
     */
    public static void saveToDatabase()
    {
        StringBuilder str = new StringBuilder();
        for(Pet pet: PETS)
            str
                .append(pet.getName()).append(" ")
                .append(pet.getAge()).append(" ")
                .append(pet.getID()).append("\n");
        try
        {
            stringToFile(FILE, str.toString());
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Processes file returning a String copy of its contents.
     * @param file File to process
     * @return String containing file contents
     * @throws FileNotFoundException upon failure
     */
    public static String fileToString(File file) throws FileNotFoundException
    {
        StringBuilder output = new StringBuilder();
        Scanner input = new Scanner(file);
        while (input.hasNextLine())
            output.append(input.nextLine()).append('\n');
        return output.toString();
    }
    
    /**
     * Writes given String to file
     * @param file File to write
     * @param input String containing contents to be written
     * @throws FileNotFoundException upon failure
     */
    public static void stringToFile(File file, String input) throws FileNotFoundException
    {
        PrintWriter output = new PrintWriter(file);
        output.print(input);
        output.close();
    }
    
    /**
     * Represents a cli menu
     */
    public static class Menu
    {
        private final Command[] commands;
        private String header = "Main menu\n";
        private String prompt = "Choice: ";
        private String deliminator = ". ";
        private int offset = 0;
        
        /**
         * Construct a menu
         * @param commands Array of command options held by menu.
         * @see Menu.Command
         */
        public Menu(Command[] commands)
        {
            this.commands = commands;
        }
        
        /**
         * @return header
         */
        public String getHeader()
        {
            return header;
        }
        
        /**
         * @param header set to String
         */
        public void setHeader(String header)
        {
            this.header = header;
        }
        
        /**
         * @return prompt
         */
        public String getPrompt ()
        {
            return prompt;
        }
        
        /**
         * @param prompt set to String
         */
        public void setPrompt (String prompt)
        {
            this.prompt = prompt;
        }
        
        /**
         * @return String containing menu display
         */
        public String buildMenu()
        {
            return header + Option.list(commands, deliminator, offset) + prompt;
        }
        
        /**
         * @param number Command number
         * @return Command
         * @throws InvalidCommandException if invalid command number
         */
        public Command getCommand(int number) throws InvalidCommandException
        {
            try
            {
                return commands[number - 1];
            } catch (Exception e)
            {
                throw new InvalidCommandException(Integer.toString(number));
            }
        }
        
        /**
         * @param deliminator separates command number and command name for display. Default ". ". (example: 1.
         *                    CommandName)
         */
        public void setDeliminator (String deliminator)
        {
            this.deliminator = deliminator;
        }
        
        /**
         * @param offset Set menu
         */
        public void setOffset (int offset)
        {
            this.offset = offset;
        }
        
        /**
         * Represents a Menu.Command. (Could be better implemented as an interface). Commands are numbered starting with
         * 1 and carry an associated name. The execute method is meant to be overridden.
         * @see Option
         */
        public static class Command extends Option
        {
            private static int nCommands = 1;
            
            /**
             * @param name utilized to display name.
             */
            protected Command (String name)
            {
                super(nCommands++, name);
            }
            
            /**
             * Execute function. Meant to be overridden. Throws Runtime Exception if not overridden.
             */
            public void execute()
            {
                throw new RuntimeException("Error: Menu command not overridden");
            }
        }
    }
    
    /**
     * Represents an abstract option. Could be better implemented as an interface or abstract class.
     */
    public static class Option extends StringIntPair
    {
        
        /**
         * Constructs an option
         * @param n number
         * @param str name
         */
        protected Option(int n, String str)
        {
            super(n, str);
        }
        
        /**
         * @return String containing option name and number with default deliminator ". "
         */
        public String list()
        {
            return list(". ");
        }
        /**
         * @param deliminator separates number from name
         * @return String containing option name and number separated by supplied deliminator
         */
        public String list(String deliminator) {
            return super.n + deliminator + super.str;
        }
        
        /**
         * @param options Array of options to list
         * @param deliminator separates each number from name
         * @return list of names and numbers separated by supplied deliminator
         */
        public static String list(Option[] options, String deliminator)
        {
            return list(options, deliminator, 0);
        }
        
        /**
         * @param options Array of options to list
         * @param deliminator separates each number from name
         * @param offset offset for each line of option info
         * @return list of names and numbers separated by supplied deliminator
         */
        public static String list(Option[] options, String deliminator, int offset)
        {
            StringBuilder s = new StringBuilder();
            for (Option option : options)
                s.append(" ".repeat(offset)).append(option.list(deliminator)).append('\n');
            return s.toString();
        }
    }
    
    /**
     * Represents a pet class containing pet data
     */
    public static class Pet
    {
        private static int nextID = 0;
        private final int ID;            // Unique id given to each pet
        private String name;
        private int age;
        
        /**
         * @param name represents name of pet
         * @param age represents age of pet
         * @param id represents pet id
         * @throws InvalidAgeException if age is not in range 1 to 50
         */
        Pet(String name, int age, int id) throws InvalidAgeException
        {
            setName(name);
            setAge(age);
            this.ID = id;
            nextID = Math.max(nextID, id) + 1;
        }
    
        /**
         * Constructor for a Pet object
         *
         * @param name Name of pet
         * @param age  Age of pet
         */
        public Pet(String name, int age) throws InvalidAgeException
        {
            this(name, age, nextID);
        }
    
        /**
         * @return Unique pet id
         */
        public int getID()
        {
            return ID;
        }
    
        /**
         * @return name of pet
         */
        public String getName()
        {
            return name;
        }
    
        /**
         * @param name new name of pet
         */
        public void setName(String name)
        {
            this.name = name;
        }
    
        /**
         * @return Age of pet
         */
        public int getAge()
        {
            return age;
        }
    
        /**
         * @param age new age of pet
         */
        public void setAge(int age) throws InvalidAgeException
        {
            if(age < 1 || age > 50)
                throw new InvalidAgeException(age);
            this.age = age;
        }
        
        /**
         * @param pets ArrayList of pets to search
         * @param id id of pet to find
         * @return pet from supplied ArrayList with supplied id
         * @throws InvalidIdException if id not found
         */
        public static Pet findByID(ArrayList<Pet> pets, int id) throws InvalidIdException
        {
            for (Pet pet : pets)
                if (pet.getID() == id)
                    return pet;
            throw new InvalidIdException(id);
        }
    }
    
    /**
     * Represents a pet database command line interface.
     */
    public static class PetDatabaseCLI
    {
        // Initialize scanner for gaining input from console
        final private static Scanner STDIN = new Scanner(System.in); // Scanner set to receive input from console
        // Initialize CLI Menu
        final static private Menu MENU = new Menu(
                new Menu.Command[]{
                        new ShowPets(),
                        new AddPets(),
                        new RemovePet(),
                        new Exit()
                }
        );
        
        
        /**
         * run cli
         */
        public static void run()
        {
            MENU.setHeader("\nWhat would you like to do?\n");
            MENU.setPrompt("Your choice: ");
            MENU.setDeliminator(") ");
            MENU.setOffset(1);
            System.out.println("Pet Database Program.");
            Menu.Command command;
            while (!((command = promptChoice()) instanceof Exit))
                command.execute();
            command.execute();
        }
        
        /**
         * @return user chosen command
         */
        private static Menu.Command promptChoice()
        {
            System.out.print(MENU.buildMenu());
            String input = "";
            while(true)
            {
                try
                {
                    input = STDIN.nextLine();
                    return MENU.getCommand(Integer.parseInt(input));
                } catch (InvalidCommandException e)
                {
                    System.out.println(e.getMessage());
                    System.out.print(MENU.getPrompt());
                } catch (NumberFormatException e)
                {
                    System.out.print(new InvalidCommandException(input).getMessage());
                    System.out.print(MENU.getPrompt());
                }
            }
        }
        
        /**
         * Represents a command to show all pets in database
         * @see Menu.Command
         */
        private static class ShowPets extends Menu.Command
        {
            /**
             * Constructs show pets command
             */
            public ShowPets()
            {
                super("View all Pets");
            }
        
            /**
             * Executes show pets command
             */
            public void execute()
            {
                print();
                System.out.print("Hit enter to continue...");
                STDIN.nextLine();
            }
        
            /**
             * @return 2-D String array containing pet data. [[id, name, age],...]
             */
            private static String[][] getPetsData()
            {
                String[][] strArr = new String[ PETS.size()][3];
                for (int i = 0; i < PETS.size(); i++)
                    strArr[i] = new String[]{
                            Integer.toString(PETS.get(i).getID()),
                            PETS.get(i).getName(),
                            Integer.toString(PETS.get(i).getAge())
                    };
                return strArr;
            }
        
            /**
             * Prints pet data in table format
             */
            private static void print()
            {
                System.out.print(TABLE_BUILDER.buildAutoSizeTable(getPetsData()));
            }
        }
        
        /**
         * Remove pet from database
         * @see Menu.Command
         */
        public static class RemovePet extends Menu.Command
        {
            /**
             * Construct new remove pet command
             */
            public RemovePet ()
            {
                super("Remove a pet");
            }
        
            /**
             * execute remove pet command. Prompts user for id then removes id from temporary storage.
             */
            public void execute()
            {
                System.out.println(TABLE_BUILDER.buildAutoSizeTable(ShowPets.getPetsData()));
                System.out.print("Enter the pet ID to remove: ");
        
                String delPetName = "";
        
                try
                {
                    removePet();
                } catch (InvalidIdException e)
                {
                    System.out.println(e.getMessage());
                }
            }
        
            /**
             * @throws InvalidIdException if id is not valid
             */
            private void removePet() throws InvalidIdException
            {
                Pet pet = Pet.findByID(PETS, Integer.parseInt(STDIN.nextLine()));
                PETS.remove(pet);
                System.out.println(pet.getName() + " " + pet.getAge() + " is removed.");
            }
        }
        
        /**
         * Represents the pet database exit command.
         * @see Menu.Command
         * @see JamesWestPetDatabaseV2
         */
        public static class Exit extends Menu.Command
        {
            /**
             * Constructs Exit Menu Command
             */
            public Exit()
            {
                super("Exit");
            }
        
            /**
             * Executes exit command. Displays exit message to console
             */
            public void execute()
            {
                System.out.print("Farewell!");
            }
        }
        
        /**
         * Represents the Pet Database menu command option Add Pets. Provides a prompt which allows users to add pets to the
         * database.
         * @see Menu.Command
         * @see Menu
         * @see JamesWestPetDatabaseV2
         */
        public static class AddPets extends Menu.Command
        {
            /**
             * Default constructor
             */
            public AddPets()
            {
                super("Add new pets");
            }
            
            /**
             * Overrides parent method to prompt user for pet data and add pet data to database
             */
            public void execute()
            {
                int count = 0;
                String input;
                System.out.println("Please add pet data. Enter 'done' when finished.");
                System.out.print("Enter pet data (name, age): ");
                try
                {
                    while (!(input = STDIN.nextLine()).equalsIgnoreCase("done"))
                    {
                        if (PETS.size() == CAPACITY)
                            throw new FullDatabaseException();
                        try
                        {
                            PETS.add(parseArguments(input));
                            count++;
                        } catch (InvalidArgumentException | InvalidAgeException e)
                        {
                            System.out.println(e.getMessage());
                        } finally
                        {
                            System.out.print("Enter pet data (name, age): ");
                        }
                    }
                    System.out.println(count + " pets added!");
                } catch (FullDatabaseException e)
                {
                    System.out.println(e.getMessage());
                }
            }
            
            /**
             * Parses arguments to initialize new pet from console input
             * @param input String to be parsed
             * @return new pet initialized to console arguments
             * @throws InvalidArgumentException thrown if arguments are not able to be parsed
             * @throws InvalidAgeException thrown if age is outside range as defined by Pet.
             */
            private Pet parseArguments(String input) throws InvalidArgumentException, InvalidAgeException
            {
                String[] petDetails = input.trim().split(" ");
                if(petDetails.length != 2)
                    throw new InvalidArgumentException(input);
                int age;
                try
                {
                    age = Integer.parseInt(petDetails[1]);
                } catch (Exception e)
                {
                    throw new InvalidArgumentException(input);
                }
                return new Pet(petDetails[0], age);
            }
        }
    }
    
    /**
     * Represents a string int pairing. Could be better implemented as an abstract class or interface
     */
    public static class StringIntPair
    {
        protected int n = 0;
        protected String str = "";
        
        /**
         * Construct a new String int pair
         * @param n integer value stored in pair
         * @param str String value stored in pair
         */
        protected StringIntPair(int n, String str)
        {
            this.n = n;
            this.str = str;
        }
        
        /**
         * Extracts ints from an array of String int pairs
         * @param pairs Array of String int pairs
         * @return Array of ints from array of String in pairs
         */
        public static int[] extractInts(StringIntPair[] pairs)
        {
            int[] numbers = new int[pairs.length];
            for(int i = 0; i < pairs.length; i++)
                numbers[i] = pairs[i].n;
            return numbers;
        }
        
        /**
         * Extracts Strings from an array of String int pairs
         * @param pairs Array of String int pairs
         * @return Array of Strings from array of String int pairs
         */
        public static String[] extractStrings(StringIntPair[] pairs)
        {
            String[] strings = new String[pairs.length];
            for(int i = 0; i < pairs.length; i++)
                strings[i] = pairs[i].str;
            return strings;
        }
    }
    
    /**
     * Represents a console table builder
     */
    public static class TableBuilder
    {
        private final ArrayList<Field> FIELDS;
        
        /**
         * @param fields Represents parameters of table fields.
         */
        public TableBuilder(ArrayList<Field> fields)
        {
            this.FIELDS = fields;
        }
        
        /**
         * @return String containing header based on fields given
         */
        public String buildHeader()
        {
            String[] entries = StringIntPair.extractStrings(FIELDS.toArray(new Field[0]));
            return buildHorizontalBar() +
                    buildRow(entries) +
                    buildHorizontalBar();
        }
        
        /**
         * @return String containing horizontalBar sized appropriately based on fields given
         */
        public String buildHorizontalBar()
        {
            StringBuilder s = new StringBuilder("+");
            int totalWidth = 0;
            for (Field field : FIELDS)
                totalWidth += field.getWidth();
            return s.append("-".repeat(Math.max(0, totalWidth + FIELDS.size() - 1))).append("+\n").toString();
        }
        
        /**
         * @param row array containing data for a give row
         * @return String of combined row data sized appropriately based on fields
         */
        public String buildRow(String[] row)
        {
            StringBuilder s = new StringBuilder("|");
            for (int i = 0; i < row.length; i++)
                s.append(" ")
                        .append(row[i])
                        .append(" ".repeat(Math.max(0, FIELDS.get(i).getWidth() - row[i].length() - 1)))
                        .append("|");
            return s.append('\n').toString();
        }
        
        /**
         * @param nRows number of rows in table
         * @return String containing footer of table sized appropriately based on fields params
         */
        public String buildFooter(int nRows)
        {
            return buildHorizontalBar() + nRows + " rows displayed.\n";
        }
        
        /**
         * Builds table resized to fit to widths of max length entry if needed.
         * @param table 2-D array representing rows and columns in table
         * @return String of table
         */
        public String buildAutoSizeTable(String[][] table)
        {
            autoFitWidths(table);
            StringBuilder s = new StringBuilder(buildHeader());
            for (String[] row : table)
                s.append(buildRow(row));
    
            return s.append(buildFooter(table.length)).toString();
        }
        
        /**
         * Resize table widths to fit max entry length in table if needed.
         * @param rows 2-D array representing rows and columns in table
         */
        public void autoFitWidths(String[][] rows)
        {
            for (String[] row : rows)
                for (int i = 0; i < row.length; i++)
                    FIELDS.get(i).setWidth(Math.max(row[i].length() + 2, FIELDS.get(i).getWidth()));
        }
    }
    
    /**
     * Used to indicate invalid id
     * @see PetDatabaseCLI.RemovePet
     * @see Exception
     */
    public static class InvalidIdException extends Exception
    {
        public InvalidIdException (int id)
        {
            super("InvalidIdException: " + "ID " + id + " does not exist");
        }
    }
    
    /**
     * Used to communicate a command selection is invalid
     * @see Exception
     * @see Menu.Command
     */
    public static class InvalidCommandException extends Exception
    {
        public InvalidCommandException (String input)
        {
            super("InvalidCommandException: " + input + " is not a valid command.");
        }
    }
    
    /**
     * Used to indicate an invalid argument
     * @see Exception
     */
    public static class InvalidArgumentException extends Exception
    {
        public InvalidArgumentException (String input)
        {
            super("InvalidArgumentException: " + input + " is not a valid input.");
        }
    }
    
    /**
     * Used to indicate invalid age.
     * @see Exception
     */
    public static class InvalidAgeException extends Exception
    {
        public InvalidAgeException (int age)
        {
            super("InvalidAgeException: " + age + " is not a valid age.");
        }
    }
    
    /**
     * Used to indicate a database is full.
     * @see Exception
     */
    public static class FullDatabaseException extends Exception
    {
        public FullDatabaseException ()
        {
            super("FullDatabaseException: Database is full.");
        }
    }
    
    /**
     * Represents a table field. Contains name of and width of column in table.
     * @see StringIntPair
     * @see TableBuilder
     */
    public static class Field extends StringIntPair
    {
        public Field(int width, String name)
        {
            super(width, name);
        }
    
        public int getWidth()
        {
            return super.n;
        }
    
        public String getName()
        {
            return super.str;
        }
    
        public void setWidth(int width)
        {
            super.n = width;
        }
    
        public void setName(String name)
        {
            super.str = name;
        }
    }
}

