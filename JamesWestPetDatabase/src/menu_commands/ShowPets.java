package menu_commands;

import cli_helpers.MenuCommand;
import main.PetDatabase;

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
        PetDatabase.STDIN.nextLine();
    }

    public static String[][] getPetsData()
    {
        String[][] strArr = new String[PetDatabase.PETS.size()][3];
        for (int i = 0; i < PetDatabase.PETS.size(); i++)
            strArr[i] = new String[]{
                    Integer.toString(PetDatabase.PETS.get(i).getID()),
                    PetDatabase.PETS.get(i).getName(),
                    Integer.toString(PetDatabase.PETS.get(i).getAge())
            };
        return strArr;
    }

    private static void print()
    {
        System.out.print(PetDatabase.TABLE_BUILDER.buildAutoSizeTable(getPetsData()));
    }
}
