package menu_commands;

import cli_helpers.MenuCommand;
import main.Pet;
import main.PetDatabase;


public class AddPets extends MenuCommand
{
    public AddPets()
    {
        super("Add new pets");
    }

    public void execute()
    {
        int count = 0;
        String[] input;
        System.out.println("Please add pet data. Enter 'done' when finished.");
        System.out.print("Enter pet data (name, age): ");
        while (!(input = PetDatabase.STDIN.nextLine().split(" "))[0].equalsIgnoreCase("done"))
        {
            PetDatabase.PETS.add(new Pet(input[0], Integer.parseInt(input[1])));
            count++;
            System.out.print("Enter pet data (name, age): ");
        }
        System.out.println(count + " pets added!");
    }
}
