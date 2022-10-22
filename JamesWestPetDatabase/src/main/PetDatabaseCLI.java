package main;

import cli_helpers.*;
import menu_commands.*;
import java.util.ArrayList;
import java.util.Scanner;

public class PetDatabaseCLI
{
    static final ArrayList<Field> TABLE_FIELDS = new ArrayList<Field>()
    {
        {
            add(new Field(4, "ID"));
            add(new Field(6, "Name"));
            add(new Field(5, "AGE"));
        }
    };

    public static final TableBuilder TABLE_BUILDER = new TableBuilder(TABLE_FIELDS);

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

    public static Scanner scanner = new Scanner(System.in);
    public static ArrayList<Pet> pets = new ArrayList<>();

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
        return menu.getCommand(Integer.parseInt(scanner.nextLine()));
    }

}
