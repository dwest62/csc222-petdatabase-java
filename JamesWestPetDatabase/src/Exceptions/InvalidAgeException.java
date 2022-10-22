package Exceptions;

// TODO Implement Exceptions.InvalidAgeException - thrown in setAge() if range is outside 1-50
class InvalidAgeException extends Exception
{
    InvalidAgeException(int age)
    {
        super(age + " is not a valid age.");
    }
}
