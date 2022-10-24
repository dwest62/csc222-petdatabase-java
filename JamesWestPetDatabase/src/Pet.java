import java.util.ArrayList;

/**
 * Represents a pet class containing pet data
 */
public class Pet
{
    private static int nextID = 0;
    private final int ID;            // Unique id given to each pet
    private String name;
    private int age;
    
    /**
     * @param name represents name of pet
     * @param age represents age of pet
     * @param id represents pet id
     * @throws InvalidAgeException if age is not in range 1 to 50
     */
    Pet(String name, int age, int id) throws InvalidAgeException
    {
        setName(name);
        setAge(age);
        this.ID = id;
        nextID = Math.max(nextID, id) + 1;
    }

    /**
     * Constructor for a Pet object
     *
     * @param name Name of pet
     * @param age  Age of pet
     */
    public Pet(String name, int age) throws InvalidAgeException
    {
        this(name, age, nextID);
    }

    /**
     * @return Unique pet id
     */
    public int getID()
    {
        return ID;
    }

    /**
     * @return name of pet
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name new name of pet
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return Age of pet
     */
    public int getAge()
    {
        return age;
    }

    /**
     * @param age new age of pet
     */
    public void setAge(int age) throws InvalidAgeException
    {
        if(age < 1 || age > 50)
            throw new InvalidAgeException(age);
        this.age = age;
    }
    
    /**
     * @param pets ArrayList of pets to search
     * @param id id of pet to find
     * @return pet from supplied ArrayList with supplied id
     * @throws InvalidIdException if id not found
     */
    public static Pet findByID(ArrayList<Pet> pets, int id) throws InvalidIdException
    {
        for (Pet pet : pets)
            if (pet.getID() == id)
                return pet;
        throw new InvalidIdException(id);
    }
}
