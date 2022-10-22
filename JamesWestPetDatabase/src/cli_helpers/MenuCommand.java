package cli_helpers;

public class MenuCommand extends Option
{
    static int nOptions = 1;

    protected MenuCommand(String name)
    {
        super(nOptions++, name);
    }

    public void execute()
    {
        throw new RuntimeException("Error: Menu command not overridden");
    }
}