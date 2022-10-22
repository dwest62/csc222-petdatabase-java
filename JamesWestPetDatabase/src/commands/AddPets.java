package commands;
import cli_helpers.MenuCommand;
import main.Pet;
import static main.PetDatabaseCLI.*;

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
        while (!(input = scanner.nextLine().split(" "))[0].equalsIgnoreCase("done"))
        {
            pets.add(new Pet(input[0], Integer.parseInt(input[1])));
            count++;
            System.out.print("Enter pet data (name, age): ");
        }
        System.out.println(count + " pets added!");
    }
}
