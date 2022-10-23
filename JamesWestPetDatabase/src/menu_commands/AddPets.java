package menu_commands;

import cli_helpers.MenuCommand;
import exceptions.*;
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
        String input;
        System.out.println("Please add pet data. Enter 'done' when finished.");
        System.out.print("Enter pet data (name, age): ");
        try
        {
            while (!(input = PetDatabase.STDIN.nextLine()).equalsIgnoreCase("done"))
            {
                if (PetDatabase.PETS.size() == PetDatabase.CAPACITY)
                    throw new FullDatabaseException();
                try
                {
                    PetDatabase.PETS.add(parseArguments(input));
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
    public int getAge(String input) throws InvalidArgumentException
    {
        try
        {
            return Integer.parseInt(input.split(" ")[1]);
        } catch (Exception e)
        {
            throw new InvalidArgumentException(input);
        }
    }
}
