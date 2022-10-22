package menu_commands;

import cli_helpers.MenuCommand;

public class Exit extends MenuCommand
{
    public Exit()
    {
        super("Exit");
    }

    public void execute()
    {
        System.out.print("Farewell!");
    }
}
