package cli_helpers;

import exceptions.InvalidCommandException;

public class Menu
{
    private final MenuCommand[] commands;
    private String header = "Main menu\n";
    private String prompt = "Choice: ";
    private String deliminator = ". ";
    private int offset = 0;
    public Menu(MenuCommand[] commands)
    {
        this.commands = commands;
    }

    public String getHeader()
    {
        return header;
    }
    public void setHeader(String header)
    {
        this.header = header;
    }
    public String getPrompt ()
    {
        return prompt;
    }
    public void setPrompt (String footer)
    {
        this.prompt = footer;
    }
    public String buildMenu(String deliminator)
    {
        return buildMenu(deliminator, 0);
    }
    public String buildMenu(String deliminator, int offset)
    {
        return header + Option.list(commands, deliminator, offset) + prompt;
    }

    public MenuCommand getCommand(int number) throws InvalidCommandException
    {
        try
        {
            return commands[number - 1];
        } catch (Exception e)
        {
            throw new InvalidCommandException(Integer.toString(number));
        }
    }
    
    public String getDeliminator ()
    {
        return deliminator;
    }
    
    public void setDeliminator (String deliminator)
    {
        this.deliminator = deliminator;
    }
    
    public int getOffset ()
    {
        return offset;
    }
    
    public void setOffset (int offset)
    {
        this.offset = offset;
    }
}
