package main;

import cli_helpers.Field;
import cli_helpers.TableBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class PetDatabase
{
    static final public int CAPACITY = 5;
    final public static String FILE_NAME = "pets.txt";
    final public static File FILE = new File(FILE_NAME);
    final public static Scanner STDIN = new Scanner(System.in);
    final public static ArrayList<Pet> PETS = new ArrayList<>();
    final static ArrayList<Field> TABLE_FIELDS = new ArrayList<>()
    {
        {
            add(new Field(4, "ID"));
            add(new Field(6, "Name"));
            add(new Field(5, "AGE"));
        }
    };
    final public static TableBuilder TABLE_BUILDER = new TableBuilder(TABLE_FIELDS);

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
    public static String fileToString(File file) throws FileNotFoundException
    {
        StringBuilder output = new StringBuilder();
        Scanner input = new Scanner(file);
        while (input.hasNextLine())
            output.append(input.nextLine()).append('\n');
        return output.toString();
    }
    public static void stringToFile(File file, String input) throws FileNotFoundException
    {
        PrintWriter output = new PrintWriter(file);
        output.print(input);
        output.close();
    }
}

