package cli_helpers;

public class StringIntPair
{
    protected int n = 0;
    protected String str = "";

    protected StringIntPair(int n, String str)
    {
        this.n = n;
        this.str = str;
    }

    public static int[] extractInts(StringIntPair[] pairs)
    {
        int[] numbers = new int[pairs.length];
        for(int i = 0; i < pairs.length; i++)
            numbers[i] = pairs[i].n;
        return numbers;
    }
    public static String[] extractStrings(StringIntPair[] pairs)
    {
        String[] strings = new String[pairs.length];
        for(int i = 0; i < pairs.length; i++)
            strings[i] = pairs[i].str;
        return strings;
    }
}
