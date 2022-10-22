package commands;

import cli_helpers.MenuCommand;

import static main.PetDatabaseCLI.*;

public class ShowPets extends MenuCommand
{
    public ShowPets()
    {
        super("View all Pets");
    }

    public void execute()
    {
        print();
        System.out.print("Hit enter to continue...");
        scanner.nextLine();
    }

    public static String[][] getPetsData()
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
