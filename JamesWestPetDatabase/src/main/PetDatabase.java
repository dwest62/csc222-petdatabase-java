package main;

import cli_helpers.Field;
import cli_helpers.TableBuilder;
import filehandler.FileHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class PetDatabase
{
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
        PetDatabaseCLI.run();
        saveToDatabase();
    }
    public static void loadDatabase()
    {
        try
        {
            String data = FileHandler.fileToString(FILE);
            System.out.println(data);
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
            FileHandler.stringToFile(FILE, str.toString());
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

    }
}

