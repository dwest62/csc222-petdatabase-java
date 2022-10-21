// TODO Implement FullDatabaseException - thrown in addPet() when petCount == CAPACITY
class FullDatabaseException extends Exception
{
    FullDatabaseException()
    {
        super("Database is full.");
    }
}
