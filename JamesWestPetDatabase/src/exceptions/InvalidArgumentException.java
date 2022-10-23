package exceptions;

public class InvalidArgumentException extends Exception
{
    public InvalidArgumentException (String input)
    {
        super("InvalidArgumentException: " + input + " is not a valid input.");
    }
}
