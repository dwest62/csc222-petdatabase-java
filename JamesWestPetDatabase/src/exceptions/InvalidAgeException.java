package exceptions;

// TODO Implement Exceptions.InvalidAgeException - thrown in setAge() if range is outside 1-50
public class InvalidAgeException extends Exception
{
    public InvalidAgeException (int age)
    {
        super("InvalidAgeException: " + age + " is not a valid age.");
    }
}
