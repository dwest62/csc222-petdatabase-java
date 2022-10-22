package main;

import cli_helpers.*;
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
            },
            "What would you like to do?",
            "Choice: "
    );

    public static void run()
    {
        System.out.println("Welcome to Pet Database!");
        MenuCommand command;
        while (!((command = promptChoice()) instanceof Exit))
            command.execute();
    }

    public static MenuCommand promptChoice()
    {
        System.out.print(menu.buildMenu());
        return menu.getCommand(Integer.parseInt(STDIN.nextLine()));
    }

}
