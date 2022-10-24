/**
 * Represents an abstract option. Could be better implemented as an interface or abstract class.
 */
public class Option extends StringIntPair
{
    
    /**
     * Constructs an option
     * @param n number
     * @param str name
     */
    protected Option(int n, String str)
    {
        super(n, str);
    }
    
    /**
     * @return String containing option name and number with default deliminator ". "
     */
    public String list()
    {
        return list(". ");
    }
    /**
     * @param deliminator separates number from name
     * @return String containing option name and number separated by supplied deliminator
     */
    public String list(String deliminator) {
        return super.n + deliminator + super.str;
    }
    
    /**
     * @param options Array of options to list
     * @param deliminator separates each number from name
     * @return list of names and numbers separated by supplied deliminator
     */
    public static String list(Option[] options, String deliminator)
    {
        return list(options, deliminator, 0);
    }
    
    /**
     * @param options Array of options to list
     * @param deliminator separates each number from name
     * @param offset offset for each line of option info
     * @return list of names and numbers separated by supplied deliminator
     */
    public static String list(Option[] options, String deliminator, int offset)
    {
        StringBuilder s = new StringBuilder();
        for (Option option : options)
            s.append(" ".repeat(offset)).append(option.list(deliminator)).append('\n');
        return s.toString();
    }
}
