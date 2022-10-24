/**
 * Used to indicate invalid id
 * @see PetDatabaseCLI.RemovePet
 * @see Exception
 */
public class InvalidIdException extends Exception
{
    public InvalidIdException (int id)
    {
        super("InvalidIdException: " + "ID " + id + " does not exist");
    }
}
