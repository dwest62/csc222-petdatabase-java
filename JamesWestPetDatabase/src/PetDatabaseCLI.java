import java.util.Scanner;

/**
 * Represents a pet database command line interface.
 */
public class PetDatabaseCLI
{
    // Initialize scanner for gaining input from console
    final public static Scanner STDIN = new Scanner(System.in); // Scanner set to receive input from console
    // Initialize CLI Menu
    final static Menu MENU = new Menu(
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
        while (!((command = promptChoice()) instanceof  Exit))
            command.execute();
        command.execute();
    }
    
    /**
     * @return user chosen command
     */
    public static Menu.Command promptChoice()
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
    public static class ShowPets extends Menu.Command
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
        public static String[][] getPetsData()
        {
            String[][] strArr = new String[ JamesWestPetDatabaseV2.PETS.size()][3];
            for (int i = 0; i < JamesWestPetDatabaseV2.PETS.size(); i++)
                strArr[i] = new String[]{
                        Integer.toString(JamesWestPetDatabaseV2.PETS.get(i).getID()),
                        JamesWestPetDatabaseV2.PETS.get(i).getName(),
                        Integer.toString(JamesWestPetDatabaseV2.PETS.get(i).getAge())
                };
            return strArr;
        }
    
        /**
         * Prints pet data in table format
         */
        private static void print()
        {
            System.out.print(JamesWestPetDatabaseV2.TABLE_BUILDER.buildAutoSizeTable(getPetsData()));
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
            System.out.println(JamesWestPetDatabaseV2.TABLE_BUILDER.buildAutoSizeTable(ShowPets.getPetsData()));
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
        public void removePet() throws InvalidIdException
        {
            Pet pet = Pet.findByID(JamesWestPetDatabaseV2.PETS, Integer.parseInt(STDIN.nextLine()));
            JamesWestPetDatabaseV2.PETS.remove(pet);
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
            super("PetDatabaseCLI.Exit");
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
                    if (JamesWestPetDatabaseV2.PETS.size() == JamesWestPetDatabaseV2.CAPACITY)
                        throw new FullDatabaseException();
                    try
                    {
                        JamesWestPetDatabaseV2.PETS.add(parseArguments(input));
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
        public Pet parseArguments(String input) throws InvalidArgumentException, InvalidAgeException
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
