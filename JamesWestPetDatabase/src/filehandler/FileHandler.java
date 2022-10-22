package filehandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileHandler
{
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
    public static void appendToFile(File file, String input) throws FileNotFoundException
    {
        PrintWriter output = new PrintWriter(new FileOutputStream(file, true));
        output.print(input);
        output.close();
    }
}
