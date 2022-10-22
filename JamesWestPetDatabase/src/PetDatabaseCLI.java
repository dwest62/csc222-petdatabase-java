import cli_helpers.Field;
import cli_helpers.Menu;
import cli_helpers.MenuCommand;
import cli_helpers.TableBuilder;

import java.util.ArrayList;
import java.util.Scanner;

public class PetDatabaseCLI
{
    static final ArrayList<Field> TABLE_FIELDS = new ArrayList<Field>()
    {
        {
            add(new Field(4, "ID"));
            add(new Field(6, "Name"));
            add(new Field(5, "AGE"));
        }
    };

    static final TableBuilder TABLE_BUILDER = new TableBuilder(TABLE_FIELDS);

    static Menu menu = new Menu(
            new MenuCommand[]{
                    new ShowPets(),
                    new AddPets(),
                    new RemovePets(),
                    new Exit()
            },
            "What would you like to do?",
            "Choice: "
    );

    static Scanner s = new Scanner(System.in);
    static ArrayList<Pet> pets = new ArrayList<>();
    static int petCount = 0;

    public static void run()
    {
        System.out.println("Welcome to Pet Database!\n");
        MenuCommand command;
        while (!((command = promptChoice()) instanceof Exit))
            command.execute();
    }

    public static MenuCommand promptChoice()
    {
        System.out.print(menu.buildMenu());
        return menu.getCommand(Integer.parseInt(s.nextLine()));
    }

    static class ShowPets extends MenuCommand
    {
        ShowPets()
        {
            super("View all Pets");
        }

        public void execute()
        {
            print();
            System.out.print("Hit enter to continue...");
            s.nextLine();
        }

        private static String[][] getPetsData()
        {
            String[][] strArr = new String[pets.size()][3];
            for (int i = 0; i < pets.size(); i++)
                strArr[i] = new String[]{
                        Integer.toString(pets.get(i).getID()),
                        pets.get(i).getName(),
                        Integer.toString(pets.get(i).getAge())
                };
            return strArr;
        }

        private static void print()
        {
            System.out.println(TABLE_BUILDER.buildAutoSizeTable(getPetsData()));
        }
    }

    static class AddPets extends MenuCommand
    {
        AddPets()
        {
            super("Add new pets");
        }

        public void execute()
        {
            int count = 0;
            String[] input;
            System.out.println("Please add pet data. Enter 'done' when finished.");
            System.out.print("Enter pet data (name, age): ");
            while (!(input = s.nextLine().split(" "))[0].equalsIgnoreCase("done"))
            {
                pets.add(new Pet(input[0], Integer.parseInt(input[1])));
                count++;
                System.out.print("Enter pet data (name, age): ");
            }
            System.out.println(count + " pets added!");
        }
    }

    static class RemovePets extends MenuCommand
    {
        RemovePets()
        {
            super("Remove a pet");
        }

        public void execute()
        {
            System.out.println(TABLE_BUILDER.buildAutoSizeTable(ShowPets.getPetsData()));
            System.out.print("Enter the pet ID to remove: ");

            String delPetName = "";
            // Cool use of functional program here, but can't yet use.
            // Pet pet = pets.stream().filter(p -> p.getID() == Integer.parseInt(s.nextLine())).findFirst().orElse(null);
            pets.remove(Pet.findByID(pets, Integer.parseInt(s.nextLine())));
        }
    }

    static class Exit extends MenuCommand
    {
        Exit()
        {
            super("Exit");
        }

        public void execute()
        {
            ;
        }
    }

}
