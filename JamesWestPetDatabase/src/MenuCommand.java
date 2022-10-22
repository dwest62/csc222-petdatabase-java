class MenuCommand extends Option
{
    static int nOptions = 1;

    MenuCommand(String name)
    {
        super(nOptions++, name);
    }

    public static void execute()
    {
    }
    public static MenuCommand getFromNumber(MenuCommand[] commands, int number)
    {
        return commands[number - 1];
    }
    public boolean isEqualTo(MenuCommand command)
    {
        return this.equals(command);
    }
}