package cli_helpers;

public class Option extends StringIntPair
{

    protected Option(int n, String str)
    {
        super(n, str);
    }
    
    public String list(String deliminator) {
        return super.n + deliminator + super.str;
    }
    public static String list(Option[] options, String deliminator)
    {
        return list(options, deliminator, 0);
    }
    public static String list(Option[] options, String deliminator, int offset)
    {
        StringBuilder s = new StringBuilder();
        for (Option option : options)
            s.append(" ".repeat(offset)).append(option.list(deliminator)).append('\n');
        return s.toString();
    }
}
