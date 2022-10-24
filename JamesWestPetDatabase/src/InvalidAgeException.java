/**
 * Used to indicate invalid age.
 * @see Exception
 */
public class InvalidAgeException extends Exception
{
    public InvalidAgeException (int age)
    {
        super("InvalidAgeException: " + age + " is not a valid age.");
    }
}
