/**
 * Used to communicate a command selection is invalid
 * @see Exception
 * @see Menu.Command
 */
public class InvalidCommandException extends Exception
{
	public InvalidCommandException (String input)
	{
		super("InvalidCommandException: " + input + " is not a valid command.");
	}
}
