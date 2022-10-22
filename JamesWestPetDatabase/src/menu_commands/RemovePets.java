package menu_commands;

import cli_helpers.MenuCommand;
import main.Pet;
import main.PetDatabase;

public class RemovePets extends MenuCommand
{
    public RemovePets()
    {
        super("Remove a pet");
    }

    public void execute()
    {
        System.out.println(PetDatabase.TABLE_BUILDER.buildAutoSizeTable(ShowPets.getPetsData()));
        System.out.print("Enter the pet ID to remove: ");

        String delPetName = "";
        // Cool use of functional program here, but can't yet use.
        // Pet pet = pets.stream().filter(p -> p.getID() == Integer.parseInt(s.nextLine())).findFirst().orElse(null);
        PetDatabase.PETS.remove(Pet.findByID(PetDatabase.PETS, Integer.parseInt(PetDatabase.STDIN.nextLine())));
    }
}
