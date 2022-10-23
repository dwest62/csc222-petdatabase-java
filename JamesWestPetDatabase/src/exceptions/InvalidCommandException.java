package exceptions;

public class InvalidCommandException extends Exception
{
	public InvalidCommandException (String input)
	{
		super("InvalidCommandException: " + input + " is not a valid command.");
	}
}
