/*
 * Author: James West
 * Date: 10.23.2022
 * Class: Fall 100 CSC222 Intro Programming with Java
 * Assignment: Assignment6 - Pet Database Version 2
 * Description: Represents a Pet Database which saves, displays, and modifies user submitted pet data.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a Pet Database which saves, displays, and modifies user submitted pet data.
 */
public class JamesWestPetDatabaseV2
{
    static final public int CAPACITY = 5; // Capacity of database
    final public static String FILE_NAME = "pets.txt"; // File in which pet data will be retrieved and stored
    final public static File FILE = new File(FILE_NAME); // File object created from file name
    final public static ArrayList<Pet> PETS = new ArrayList<>(); // Temporary Pet storage
    
    // CLI display table field names and widths
    final static ArrayList<Field> TABLE_FIELDS = new ArrayList<>()
    {
        {
            add(new Field(4, "ID"));
            add(new Field(6, "Name"));
            add(new Field(5, "AGE"));
        }
    };
    // Table builder initiated with above fields
    final public static TableBuilder TABLE_BUILDER = new TableBuilder(TABLE_FIELDS);
    
    /**
     * Main driver function
     * @param args not utilized
     */
    public static void main(String[] args)
    {
        
        loadDatabase();
        try
        {
            PetDatabaseCLI.run();
        } catch (Exception e)
        {
            System.out.println("Unexpected error: " + e.getMessage());
        } finally
        {
            saveToDatabase();
        }

    }
    
    /**
     * Load information from file given by filename constant and add to temporary app storage
     */
    public static void loadDatabase()
    {
        try
        {
            String data = fileToString(FILE);
            String[] lines = data.split("\n");
            for(String line: lines)
            {
                String[] input = line.split(" ");
                PETS.add(
                        new Pet(
                                input[0],
                                Integer.parseInt(input[1]),
                                Integer.parseInt(input[2])
                        )
                );
            }

        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Save temporary storage to file given by filename constant
     */
    public static void saveToDatabase()
    {
        StringBuilder str = new StringBuilder();
        for(Pet pet: PETS)
            str
                .append(pet.getName()).append(" ")
                .append(pet.getAge()).append(" ")
                .append(pet.getID()).append("\n");
        try
        {
            stringToFile(FILE, str.toString());
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Processes file returning a String copy of its contents.
     * @param file File to process
     * @return String containing file contents
     * @throws FileNotFoundException upon failure
     */
    public static String fileToString(File file) throws FileNotFoundException
    {
        StringBuilder output = new StringBuilder();
        Scanner input = new Scanner(file);
        while (input.hasNextLine())
            output.append(input.nextLine()).append('\n');
        return output.toString();
    }
    
    /**
     * Writes given String to file
     * @param file File to write
     * @param input String containing contents to be written
     * @throws FileNotFoundException upon failure
     */
    public static void stringToFile(File file, String input) throws FileNotFoundException
    {
        PrintWriter output = new PrintWriter(file);
        output.print(input);
        output.close();
    }
}

