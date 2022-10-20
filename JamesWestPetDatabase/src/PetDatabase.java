import java.util.Scanner;
import java.io.File;

// TODO Add Filename
// TODO Implement Load Database
// TODO Implement Save Database
/**
 * Represents a pet database cli.
 */
public class PetDatabase
{
    final static PetMenu PET_MENU = new PetMenu(7); // Main database menu
    final static String FILENAME = "pets.txt";
    final static String[] COLUMN_NAMES = {"ID", "NAME", "AGE", "TYPE"}; // Corresponds to widths. Used in table printer.
    static int[] COLUMN_WIDTHS = {4, 6, 5, 16}; // ID, Name, Age, Type
    static Scanner s = new Scanner(System.in); // Input reader
    static Pet[] pets = new Pet[200]; // Pet array stores pet data. Max 200.
    static int petCount = 0; // Current number of pets stored in pet array.

    /**
     * Main database function.
     *
     * @param args Not currently utilized
     */
    public static void main(String[] args)
    {
        System.out.println("Welcome to Pet Database!");
        loadDatabase();
        int state;
        while ((state = PET_MENU.consumeState()) != PET_MENU.EXIT)
        {
            switch (state)
            {
                case PetMenu.FILL_STATE -> fillState();
                case PetMenu.SHOW_PETS -> showPets();
                case PetMenu.ADD_PETS -> addPets();
                case PetMenu.UPDATE_PET -> updatePet();
                case PetMenu.REMOVE_PET -> removePet();
                case PetMenu.SEARCH_BY_NAME -> searchPetsByName();
                case PetMenu.SEARCH_BY_AGE -> searchPetsByAge();
                case PetMenu.SEARCH_BY_TYPE -> searchPetsByType();
            }
        }
        System.out.println("Farewell!");
    }

    // TODO
    public static void loadDatabase()
    {

    }
    // TODO
    public static void saveDatabase()
    {

    }

    /**
     * Shows pet data in table and pauses.
     */
    public static void showPets()
    {
        showAllPets();
        pauseForContinue();
    }

    /**
     * Displays a table containing pet info to the console.
     */
    public static void showAllPets()
    {
        printTableHeader();
        for (int i = 0; i < petCount; i++)
            printTableRow(pets[i].getID(), pets[i].getName(), pets[i].getAge(), pets[i].print());
        printTableFooter(petCount);
    }
    /**
     * Prompt user for pets and add pets to database.
     */
    public static void addPets()
    {
        System.out.print(getPetInputRules() + "Please add pet data. Enter 'done' when finished.\n");
        String prompt = "Enter the name and age (name, age): ";
        String input = promptValidPetData(prompt);

        while (!input.equalsIgnoreCase("done"))
        {
            String name = input.substring(0, input.indexOf(",")).trim();
            int age = Integer.parseInt(input.substring(input.indexOf(", ") + 1).trim());
            pets[petCount++] = promptPetWithType(name, age);
            updateColWidths(name);
            input = promptValidPetData(prompt);
        }
    }

    // TODO
    public static void addPet()
    {

    }

    /**
     * Update pet in database
     */
    public static void updatePet()
    {
        showAllPets();

        // Loop to find index
        int id = persistentPromptPositiveInt("Enter the pet ID to update: ");
        int index = -1;
        for (int i = 0; i < petCount; i++)
            if (pets[i].getID() == id)
            {
                index = i;
                break;
            }

        // If id found, get pet data and update pet and column widths, else display clever message.
        if (index >= 0)
        {
            String input = promptValidPetData("Enter the new name and age (name, age) or enter done to exit: ");

            if (!input.equalsIgnoreCase("done"))
            {
                String tempName = pets[index].getName(), name = input.substring(0, input.indexOf(",")).trim();
                int tempAge = pets[index].getAge();
                int age = Integer.parseInt(input.substring(input.indexOf(", ") + 1).trim());

                pets[index].setName(name);
                pets[index].setAge(age);
                updateColWidths(name);

                System.out.println(tempName + " " + tempAge + " changed to " + pets[index].getName() + " "
                        + pets[index].getAge());
            }
        } else
        {
            System.out.println(id + " not found in database..."
                    + (index <= petCount ? "You should really let ghost pets rest in peace." : "Try again."));
            pauseForContinue();
        }
    }

    /**
     * Remove pet from database.
     */
    public static void removePet()
    {
        showAllPets();

        int id = persistentPromptPositiveInt("Enter the pet ID to remove: ");

        // Loop to copy over Pet to be deleted if id is found.
        String delPetName = "";
        for (int i = 0, j = 0; i < petCount; i++)
        {
            if (pets[i].getID() == id)
            {
                delPetName = pets[i].getName();
            } else
                pets[j++] = pets[i];
        }

        // If pet was found, decrement count and notify user, else notify user none was found
        if (delPetName.length() > 0)
        {
            System.out.println(delPetName + " is removed. No pets were harmed during this process.");
            petCount--;
        } else System.out.println("No pet found by that id.");
        pauseForContinue();
    }

    /**
     * Find pet by name.
     */
    public static void searchPetsByName()
    {
        // Get name
        System.out.print("Enter a name to search (case insensitive): ");
        String input = s.nextLine().trim();

        // Print table including only rows where pet name is matched
        printTableHeader();
        int counter = 0;
        for (int i = 0; i < petCount; i++)
            if (pets[i].getName().equalsIgnoreCase(input))
            {
                printTableRow(pets[i].getID(), pets[i].getName(), pets[i].getAge(), pets[i].print());
                counter++;
            }
        printTableFooter(counter);
        pauseForContinue();
    }

    /**
     * Search pets by age.
     */
    public static void searchPetsByAge()
    {
        int age = persistentPromptPositiveInt("Enter an age to search: ");
        printTableHeader();
        int counter = 0;
        for (int i = 0; i < petCount; i++)
            if (pets[i].getAge() == age)
            {
                printTableRow(pets[i].getID(), pets[i].getName(), pets[i].getAge(), pets[i].print());
                counter++;
            }
        printTableFooter(counter);
        pauseForContinue();
    }

    /**
     * Search pet by type
     */
    public static void searchPetsByType()
    {
        String prompt = "What type of pet would you like to search ? \n" + PetTypes.getPetTypeOptions();
        int choice = persistentPromptPositiveInt(prompt);
        int count = 0;
        printTableHeader();
        for (int i = 0; i < petCount; i++)
        {
            if (checkIfChosenInstance(pets[i], choice))
            {
                printTableRow(pets[i].getID(), pets[i].getName(), pets[i].getAge(), pets[i].print());
                count++;
            }
        }
        printTableFooter(count);
        pauseForContinue();
    }


    /**
     * Get choice from user and fill state with choice.
     */
    public static void fillState()
    {
        int choice = persistentPromptPositiveInt(PetMenu.getMenu() + "Your choice: ");
        while (choice < 1 || choice > PET_MENU.EXIT)
        {
            System.out.println("Invalid choice. Please choose a number between 1 and " + PET_MENU.EXIT);
            choice = persistentPromptPositiveInt(PetMenu.getMenu() + "Your choice: ");
        }
        PET_MENU.fillState(choice);
        System.out.println();
    }

    /**
     * @param pet    Pet to check
     * @param choice Choice of pet type
     * @return if pet is instance of type chosen, return true; else false.
     */
    public static boolean checkIfChosenInstance(Pet pet, int choice)
    {
        return switch (choice)
                {
                    case PetTypes.DOG -> pet instanceof Dog;
                    case PetTypes.CAT -> pet instanceof Cat;
                    case PetTypes.FISH -> pet instanceof Fish;
                    default -> pet != null;
                };
    }

    /**
     * @param name name of new Pet
     * @param age  age of new Pet
     * @return Pet with name and age given by params returned as user defined type
     */
    public static Pet promptPetWithType(String name, int age)
    {
        String prompt = "What type of pet is " + name + "?\n" + PetTypes.getPetTypeOptions();
        int choice = persistentPromptPositiveInt(prompt);
        while (choice < 1 || choice > 4)
        {
            System.out.println("Invalid choice, please choose a number between 1 and 4.");
            choice = persistentPromptPositiveInt(prompt);
        }
        return switch (choice)
                {
                    case PetTypes.DOG -> new Dog(name, age);
                    case PetTypes.CAT -> new Cat(name, age);
                    case PetTypes.FISH -> new Fish(name, age);
                    default -> new Pet(name, age);
                };
    }


    /**
     * Persistently prompt user until positive int is given
     *
     * @param prompt request for entry from user
     * @return valid positive int
     */
    public static int persistentPromptPositiveInt(String prompt)
    {
        while (true)
        {
            System.out.print(prompt);
            String input = s.nextLine().trim();
            if (input.matches("^[0-9]+$"))
                return Integer.parseInt(input);
            else System.out.print("Error: " + input + " is not a valid positive number.\n");
        }
    }

    // TODO Replace with parseArgument
    /**
     * Prompt user for pet data and validate data follows specified rules. See getPetInputRules().
     *
     * @return Valid input String
     */
    public static String promptValidPetData(String prompt)
    {
        System.out.print(prompt);
        String input = s.nextLine().trim();
        while (!input.matches("^[A-Z][A-Za-z]*.?( [A-Z][a-zA-Z]*.?){0,2}, [0-9]{1,2}$")
                && !input.equalsIgnoreCase("done"))
        {
            System.out.print("Input invalid. Please review rules and try again.\n");
            System.out.print(prompt);
            input = s.nextLine().trim();
        }
        return input;
    }

    /**
     * Pause before moving forward
     */
    public static void pauseForContinue()
    {
        System.out.print("Hit enter to continue... ");
        s.nextLine();
    }

    /**
     * @return String containing rules for input of pet name and age.
     */
    public static String getPetInputRules()
    {
        return """
                +-----------------------------------------------------------------------------------------------------+
                |  							HOW TO SUCCESSFULLY DIGITALIZE YOUR PET	                                  |
                +-----------------------------------------------------------------------------------------------------+
                |  1. First character of First name, Middle name and Last name must be a capital letter.              |
                |  2. Middle and Last names are optional. Names are separated from each other by spaces.              |
                |  3. A '.' appending the end of first, middle, or last name is also optional.                        |
                |  4. The Name field is ended with a ','.                                                             |
                |  5. Other than in cases involving rules 2 - 4, all name field characters are letters.               |
                |  6. Following the name field is a space then a positive number representing the pets age (0 to 99). |
                +-----------------------------------------------------------------------------------------------------+
                Example: Mr. Mittens, 9
                """;
    }


    /**
     * Print table header based on column constants.
     */
    public static void printTableHeader()
    {
        System.out.println(buildTableHorizontalBar());
        System.out.println(buildTableRow(COLUMN_NAMES));
        System.out.println(buildTableHorizontalBar());
    }

    /**
     * @return Horizontal bar based on column constants.
     */
    public static String buildTableHorizontalBar()
    {
        StringBuilder s = new StringBuilder("+");
        int totalWidth = 0;
        for (int width : COLUMN_WIDTHS)
            totalWidth += width;
        s.append("-".repeat(Math.max(0, totalWidth + COLUMN_WIDTHS.length - 1))).append("+");
        return s.toString();
    }

    /**
     * Build table row
     *
     * @param fields Array containing row data to be built.
     * @return row String containing table spacing and vertical edges
     */
    public static String buildTableRow(String[] fields)
    {
        StringBuilder s = new StringBuilder();
        s.append("|");
        for (int i = 0; i < COLUMN_WIDTHS.length; i++)
        {
            int width = COLUMN_WIDTHS[i];
            s.append(" ").append(fields[i]).append(" ".repeat(Math.max(0, width - fields[i].length() - 1))).append("|");
        }
        return s.toString();
    }

    /**
     * Required print table row
     *
     * @param id   Pet id to be printed
     * @param name Pet name to be printed
     * @param age  Pet age to be printed
     */
    public static void printTableRow(int id, String name, int age, String type)
    {
        System.out.println(buildTableRow(new String[]{Integer.toString(id), name, Integer.toString(age), type}));
    }

    /**
     * Print table footer with row count info
     *
     * @param nRows rows in table
     */
    public static void printTableFooter(int nRows)
    {
        System.out.println(buildTableHorizontalBar());
        System.out.println(nRows + " rows in set.");
    }


    /**
     * Widens the column if needed to account for the largest name in the table
     *
     * @param name name of pet
     */
    public static void updateColWidths(String name)
    {
        COLUMN_WIDTHS[0] = Math.max(getNDigits(Pet.getNextID()) + 2, COLUMN_WIDTHS[0]);
        COLUMN_WIDTHS[1] = Math.max(COLUMN_WIDTHS[1], name.length() + 2);
    }

    /**
     * @param n number to check
     * @return number of digits the integer holds. assumes base-10.
     */
    public static int getNDigits(int n)
    {
        int count = 0;
        while ((n /= 10) > 0)
            count++;
        return count;
    }

    /**
     * Represents a pet menu which specifies and assists in the management of the pet database menu states.
     */
    static class PetMenu extends Menu
    {
        // Menu options
        public final static int SHOW_PETS = 1;
        public final static int ADD_PETS = 2;
        public final static int UPDATE_PET = 3;
        public final static int REMOVE_PET = 4;
        public final static int SEARCH_BY_NAME = 5;
        public final static int SEARCH_BY_AGE = 6;
        public final static int SEARCH_BY_TYPE = 7;

        PetMenu(int nOptions)
        {
            super(nOptions);
        }

        /**
         * @return String displaying the menu.
         */
        public static String getMenu()
        {
            return """
                                        
                    What would you like to do?
                    1. View all pets
                    2. Add more pets
                    3. Update an existing pet
                    4. Remove an existing pet
                    5. Search pets by name
                    6. Search pets by age
                    7. Search pets by type
                    8. Exit program
                    """;
        }
    }

    public static class PetTypes
    {
        public final static int DOG = 1;
        public final static int CAT = 2;
        public final static int FISH = 3;
        public final static int PET = 4;


        public static String getPetTypeOptions()
        {
            return DOG + ".Dog\n" + CAT + ".Cat\n" + FISH + ".Fish\n" + PET
                    + ".Generic Pet\nYour choice: ";
        }
    }
}

/**
 * Base Menu class (could be implemented as abstract)
 */
class Menu
{
    public static final int FILL_STATE = 0;
    public final int EXIT;
    private int state = 0;

    /**
     * Menu constructor
     *
     * @param nOptions Number of options the menu will handle.
     */
    Menu(int nOptions)
    {
        EXIT = nOptions + 1;
    }

    /**
     * Get current state and set menu state to FILL_STATE
     *
     * @return current state
     */
    public int consumeState()
    {
        int copy = state;
        state = FILL_STATE;
        return copy;
    }

    /**
     * @return Current state
     */
    public int getState()
    {
        return state;
    }

    /**
     * Update Menu State.
     *
     * @param state New menu state.
     */
    public void fillState(int state)
    {
        if (state < 0 || state > EXIT)
        {
            throw new IllegalArgumentException("ERROR. INVALID MENU STATE.");
        } else
            this.state = state;
    }
}

/**
 * Represents a pet class containing pet data
 */
class Pet
{
    private static int nextID = 0;
    private final int ID;            // Unique id given to each pet
    private String name;
    private int age;

    Pet(String name, int age, int id)
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
    Pet(String name, int age)
    {
        this(name, age, nextID);
    }

    public static int getNextID()
    {
        return nextID;
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
    public void setAge(int age)
    {
        this.age = age;
    }

    public String print()
    {
        return "I am a pet!";
    }
}

class Dog extends Pet
{
    Dog(String name, int age)
    {
        super(name, age);
    }

    public String print()
    {
        return "I am a dog!";
    }
}

class Cat extends Pet
{

    Cat(String name, int age)
    {
        super(name, age);
    }

    public String print()
    {
        return "I am a cat!";
    }
}

class Fish extends Pet
{
    Fish(String name, int age)
    {
        super(name, age);
    }

    public String print()
    {
        return "I am a fish!";
    }
}

// TODO Implement InvalidAgeException - thrown in setAge() if range is outside 1-50
class InvalidAgeException extends Exception
{
    InvalidAgeException(int age)
    {
        super(age + " is not a valid age.");
    }
}
// TODO Implement InvalidArgumentException - thrown in parseArgument() if input is not valid
class InvalidArgumentException extends Exception
{
    InvalidArgumentException(String input)
    {
        super(input + " is not a valid input.");
    }
}
// TODO Implement InvalidIdException - thrown in removePet() (and update pet?) when ID is not in range 0 - petCount
class InvalidIdException extends Exception
{
    InvalidIdException(int id)
    {
        super("ID " + id + " does not exist");
    }
}
// TODO Implement FullDatabaseException - thrown in addPet() when petCount == CAPACITY
class FullDatabaseException extends Exception
{
    FullDatabaseException()
    {
        super("Database is full.");
    }
}

// TODO Implement Load Database
// TODO Implement Save Database
    // Would be nice to do when adding pets
    // Save pets would need to push pets to database and push new pets to current app
    //

// TODO Throw and handle exceptions
// TODO Add pets added message displaying number of pets added to add pets
// TODO Sort by name, age, id?
// TODO Owner info?

// TODO