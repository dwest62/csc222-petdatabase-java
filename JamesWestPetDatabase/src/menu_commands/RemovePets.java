package menu_commands;

import cli_helpers.MenuCommand;
import exceptions.InvalidIdException;
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

        try
        {
            removePet();
        } catch (InvalidIdException e)
        {
            System.out.println(e.getMessage());
        }
    }
    public void removePet() throws InvalidIdException
    {
        Pet pet = Pet.findByID(PetDatabase.PETS, Integer.parseInt(PetDatabase.STDIN.nextLine()));
        PetDatabase.PETS.remove(pet);
        System.out.println(pet.getName() + " " + pet.getAge() + " is removed.");
    }
}
