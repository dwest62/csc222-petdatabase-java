class Option extends StringIntPair
{

    Option(int n, String str)
    {
        super(n, str);
    }

    public String list()
    {
        return getN() + ". " + getStr();
    }

    public static String list(Option[] options)
    {
        StringBuilder s = new StringBuilder();
        for (Option option : options)
            s.append(option.list()).append('\n');
        return s.toString();
    }
}