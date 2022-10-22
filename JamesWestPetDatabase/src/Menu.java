/**
 * Base Menu class (could be implemented as abstract)
 */
class Menu
{
    private final MenuCommand[] commands;
    private String header;
    private String prompt;
    Menu(MenuCommand[] commands, String header, String prompt)
    {
        this.commands = commands;
        this.header = header + "\n";
        this.prompt = prompt;
    }
    Menu(MenuCommand[] commands)
    {
        this(commands, "Main menu\n", "Choice: ");
    }
    public String getHeader()
    {
        return header;
    }
    public void setHeader(String header)
    {
        this.header = header;
    }
    public String getFooter()
    {
        return prompt;
    }
    public void setFooter(String footer)
    {
        this.prompt = footer;
    }
    public String buildMenu()
    {
        return header + Option.list(commands) + prompt;
    }

    public MenuCommand getCommand(int number)
    {
        return commands[number - 1];
    }
    public void runCommand(MenuCommand command)
    {
        command.execute();
    }
}
