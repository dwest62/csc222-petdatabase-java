/**
 * Represents a cli menu
 */
public class Menu
{
    private final Command[] commands;
    private String header = "Main menu\n";
    private String prompt = "Choice: ";
    private String deliminator = ". ";
    private int offset = 0;
    
    /**
     * Construct a menu
     * @param commands Array of command options held by menu.
     * @see Menu.Command
     */
    public Menu(Command[] commands)
    {
        this.commands = commands;
    }
    
    /**
     * @return header
     */
    public String getHeader()
    {
        return header;
    }
    
    /**
     * @param header set to String
     */
    public void setHeader(String header)
    {
        this.header = header;
    }
    
    /**
     * @return prompt
     */
    public String getPrompt ()
    {
        return prompt;
    }
    
    /**
     * @param prompt set to String
     */
    public void setPrompt (String prompt)
    {
        this.prompt = prompt;
    }
    
    /**
     * @return String containing menu display
     */
    public String buildMenu()
    {
        return header + Option.list(commands, deliminator, offset) + prompt;
    }
    
    /**
     * @param number Command number
     * @return Command
     * @throws InvalidCommandException if invalid command number
     */
    public Command getCommand(int number) throws InvalidCommandException
    {
        try
        {
            return commands[number - 1];
        } catch (Exception e)
        {
            throw new InvalidCommandException(Integer.toString(number));
        }
    }
    
    /**
     * @param deliminator separates command number and command name for display. Default ". ". (example: 1.
     *                    CommandName)
     */
    public void setDeliminator (String deliminator)
    {
        this.deliminator = deliminator;
    }
    
    /**
     * @param offset Set menu
     */
    public void setOffset (int offset)
    {
        this.offset = offset;
    }
    
    /**
     * Represents a Menu.Command. (Could be better implemented as an interface). Commands are numbered starting with
     * 1 and carry an associated name. The execute method is meant to be overridden.
     * @see Option
     */
    public static class Command extends Option
    {
        static int nCommands = 1;
        
        /**
         * @param name utilized to display name.
         */
        protected Command (String name)
        {
            super(nCommands++, name);
        }
        
        /**
         * Execute function. Meant to be overridden. Throws Runtime Exception if not overridden.
         */
        public void execute()
        {
            throw new RuntimeException("Error: Menu command not overridden");
        }
    }
}
