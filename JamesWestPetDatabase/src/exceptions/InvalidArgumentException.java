package exceptions;

// TODO Implement Exceptions.InvalidArgumentException - thrown in parseArgument() if input is not valid
class InvalidArgumentException extends Exception
{
    InvalidArgumentException(String input)
    {
        super(input + " is not a valid input.");
    }
}
