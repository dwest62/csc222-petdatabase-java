package cli_helpers;

public class Option extends StringIntPair
{

    protected Option(int n, String str)
    {
        super(n, str);
    }

    public String list()
    {
        return super.n + ". " + super.str;
    }

    public static String list(Option[] options)
    {
        StringBuilder s = new StringBuilder();
        for (Option option : options)
            s.append(option.list()).append('\n');
        return s.toString();
    }
}
