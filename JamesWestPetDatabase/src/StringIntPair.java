/**
 * Represents a string int pairing. Could be better implemented as an abstract class or interface
 */
public class StringIntPair
{
    protected int n = 0;
    protected String str = "";
    
    /**
     * Construct a new String int pair
     * @param n integer value stored in pair
     * @param str String value stored in pair
     */
    protected StringIntPair(int n, String str)
    {
        this.n = n;
        this.str = str;
    }
    
    /**
     * Extracts ints from an array of String int pairs
     * @param pairs Array of String int pairs
     * @return Array of ints from array of String in pairs
     */
    public static int[] extractInts(StringIntPair[] pairs)
    {
        int[] numbers = new int[pairs.length];
        for(int i = 0; i < pairs.length; i++)
            numbers[i] = pairs[i].n;
        return numbers;
    }
    
    /**
     * Extracts Strings from an array of String int pairs
     * @param pairs Array of String int pairs
     * @return Array of Strings from array of String int pairs
     */
    public static String[] extractStrings(StringIntPair[] pairs)
    {
        String[] strings = new String[pairs.length];
        for(int i = 0; i < pairs.length; i++)
            strings[i] = pairs[i].str;
        return strings;
    }
}
