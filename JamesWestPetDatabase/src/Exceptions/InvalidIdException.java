package Exceptions;

// TODO Implement Exceptions.InvalidIdException - thrown in removePet() (and update pet?) when ID is not in range 0 - petCount
class InvalidIdException extends Exception
{
    InvalidIdException(int id)
    {
        super("ID " + id + " does not exist");
    }
}
