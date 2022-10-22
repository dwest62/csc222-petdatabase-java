package cli_helpers;

public class Menu
{
    private final MenuCommand[] commands;
    private String header;
    private String prompt;
    public Menu(MenuCommand[] commands, String header, String prompt)
    {
        this.commands = commands;
        this.header = "\n" + header + "\n";
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
}
