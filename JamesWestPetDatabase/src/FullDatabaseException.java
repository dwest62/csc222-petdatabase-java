/**
 * Used to indicate a database is full.
 * @see Exception
 */
public class FullDatabaseException extends Exception
{
    public FullDatabaseException ()
    {
        super("FullDatabaseException: Database is full.");
    }
}
