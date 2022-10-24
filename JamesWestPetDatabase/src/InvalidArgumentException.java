/**
 * Used to indicate an invalid argument
 * @see Exception
 */
public class InvalidArgumentException extends Exception
{
    public InvalidArgumentException (String input)
    {
        super("InvalidArgumentException: " + input + " is not a valid input.");
    }
}
