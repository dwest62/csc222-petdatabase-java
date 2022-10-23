package exceptions;

// TODO Implement Exceptions.FullDatabaseException - thrown in addPet() when petCount == CAPACITY
public class FullDatabaseException extends Exception
{
    public FullDatabaseException ()
    {
        super("FullDatabaseException: Database is full.");
    }
}
