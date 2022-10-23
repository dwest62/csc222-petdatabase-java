package exceptions;

// TODO Implement Exceptions.InvalidIdException - thrown in removePet() (and update pet?) when ID is not in range 0 - petCount
public class InvalidIdException extends Exception
{
    public InvalidIdException (int id)
    {
        super("InvalidIdException: " + "ID " + id + " does not exist");
    }
}
