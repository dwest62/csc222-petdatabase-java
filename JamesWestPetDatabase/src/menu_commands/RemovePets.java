package menu_commands;

import static main.PetDatabaseCLI.*;
import cli_helpers.*;
import main.Pet;

public class RemovePets extends MenuCommand
{
    public RemovePets()
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
        pets.remove(Pet.findByID(pets, Integer.parseInt(scanner.nextLine())));
    }
}
