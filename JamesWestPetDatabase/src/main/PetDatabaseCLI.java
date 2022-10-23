package main;

import cli_helpers.*;
import exceptions.InvalidArgumentException;
import exceptions.InvalidCommandException;
import menu_commands.*;

import static main.PetDatabase.STDIN;

public class PetDatabaseCLI
{

    static Menu menu = new Menu(
            new MenuCommand[]{
                    new ShowPets(),
                    new AddPets(),
                    new RemovePets(),
                    new Exit()
            }
    );

    public static void run()
    {
        menu.setHeader("\nWhat would you like to do?\n");
        menu.setPrompt("Your choice: ");
        menu.setDeliminator(") ");
        System.out.println("Pet Database Program.");
        MenuCommand command;
        while (!((command = promptChoice()) instanceof  Exit))
            command.execute();
        command.execute();
    }

    public static MenuCommand promptChoice()
    {
        System.out.print(menu.buildMenu(") ", 1));
        String input = "";
        while(true)
        {
            try
            {
                input = STDIN.nextLine();
                return menu.getCommand(Integer.parseInt(input));
            } catch (InvalidCommandException e)
            {
                System.out.println(e.getMessage());
                System.out.print(menu.getPrompt());
            } catch (NumberFormatException e)
            {
                System.out.print(new InvalidCommandException(input).getMessage());
                System.out.print(menu.getPrompt());
            }
        }
    }

}
