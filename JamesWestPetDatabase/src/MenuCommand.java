class MenuCommand extends Option
{
    final static int CAPACITY = 30;
    static int nOptions = 0;
    static MenuCommand[] options = new MenuCommand[CAPACITY];

    MenuCommand(String name)
    {
        super(nOptions, name);
        options[nOptions++] = this;
    }

    public void execute() throws Exception
    {
        throw new Exception("Not implemented");
    }
}

class
