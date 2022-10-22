import java.util.ArrayList;
import java.util.Scanner;

public class PetDatabaseCLI
{
    static final ArrayList<Field> TABLE_FIELDS = new ArrayList<Field>()
    {
        {
            add(new Field(4, "ID"));
            add(new Field(6, "Name"));
            add(new Field(5, "AGE"));
        }
    };

    static final TableBuilder TABLE_BUILDER = new TableBuilder(TABLE_FIELDS);

    static Menu menu = new Menu(
            new MenuCommand[]{
                    new ShowPets(),
                    new AddPets(),
                    new RemovePets(),
                    new Exit()
            },
            "What would you like to do?",
            "Choice: "
    );

    static Scanner s = new Scanner(System.in);
    static ArrayList<Pet> pets = new ArrayList<>();
    static int petCount = 0;

    public void run()
    {
        System.out.println("Welcome to Pet Database!");
        MenuCommand command;
        while (!((command = promptChoice()) instanceof Exit))
            command.execute();
    }

    public MenuCommand promptChoice()
    {
        System.out.println(menu.buildMenu());
        return menu.getCommand(Integer.parseInt(s.nextLine()));
    }

    static class ShowPets extends MenuCommand
    {
        ShowPets()
        {
            super("View all Pets");
        }

        public static void execute()
        {
            System.out.println(TABLE_BUILDER.buildAutoSizeTable(getPetsData()));
        }
        private static String[][] getPetsData()
        {
            String[][] strArr = new String[pets.size()][3];
            for(int i = 0; i < pets.size(); i++)
                strArr[i] = new String[] {
                        Integer.toString(pets.get(i).getID()),
                        pets.get(i).getName(),
                        Integer.toString(pets.get(i).getAge())
                };
            return strArr;
        }
    }

    static class AddPets extends MenuCommand
    {
        AddPets()
        {
            super("Add new pets");
        }

        public static void execute()
        {
            int count = 0;
            String[] input;
            System.out.println("Please add pet data. Enter 'done' when finished.");
            while(!(input = s.nextLine().split(" "))[0].equalsIgnoreCase("done"))
            {
                System.out.print("Enter pet data (name, age): ");
                pets.add(new Pet(input[0], Integer.parseInt(input[1])));
                count++;
            }
            System.out.println(count + " pets added!");
        }
    }

    static class RemovePets extends MenuCommand
    {
        RemovePets()
        {
            super("Remove a pet");
        }

        public static void execute()
        {
            ShowPets.execute();
            System.out.print("Enter the pet ID to remove: ");

            String delPetName = "";
            // Cool use of functional program here, but can't yet use.
            // Pet pet = pets.stream().filter(p -> p.getID() == Integer.parseInt(s.nextLine())).findFirst().orElse(null);
            pets.remove(Pet.findByID(pets, Integer.parseInt(s.nextLine())));
        }
    }

    static class Exit extends MenuCommand
    {
        Exit()
        {
            super("Exit");
        }

        public static void execute()
        {
            ;
        }
    }

    static class Field extends StringIntPair
    {
        Field(int width, String name)
        {
            super(width, name);
        }

        public int getWidth()
        {
            return super.n;
        }

        public String getName()
        {
            return super.str;
        }

        public void setWidth(int width)
        {
            super.n = width;
        }

        public void setName(String name)
        {
            super.str = name;
        }
    }

    static class TableBuilder
    {
        private final ArrayList<Field> fields;

        TableBuilder(ArrayList<Field> fields)
        {
            this.fields = fields;
        }

        public String buildHeader()
        {
            String[] entries = StringIntPair.extractStrings(fields.toArray(new Field[0]));
            return buildHorizontalBar() +
                    buildRow(entries) +
                    buildHorizontalBar();
        }

        public String buildHorizontalBar()
        {
            StringBuilder s = new StringBuilder("+");
            int totalWidth = 0;
            for (Field field : fields)
                totalWidth += field.getWidth();
            return s.append("-".repeat(Math.max(0, totalWidth + fields.size() - 1))).append("+\n").toString();
        }

        public String buildRow(String[] row)
        {
            StringBuilder s = new StringBuilder("|");
            for (int i = 0; i < row.length; i++)
                s.append(" ")
                        .append(row[i])
                        .append(" ".repeat(Math.max(0, fields.get(i).getWidth() - row[i].length() - 1)))
                        .append("|");
            return s.append('\n').toString();
        }

        public String buildFooter(int nRows)
        {
            return buildHorizontalBar() + nRows + " entries in table.\n";
        }

        public String buildAutoSizeTable(String[][] table)
        {
            autoFitWidths(table);
            StringBuilder s = new StringBuilder(buildHeader());
            for (String[] row : table)
                s.append(buildRow(row));

            return s.append(buildFooter(table.length)).toString();
        }

        public void autoFitWidths(String[][] rows)
        {
            for (String[] row : rows)
                for (int i = 0; i < row.length; i++)
                    fields.get(i).setWidth(Math.max(row[i].length() + 2, fields.get(i).getWidth()));
        }

        public void addField(Field field)
        {
            fields.add(field);
        }

        public void addField(Field field, int index)
        {
            fields.add(index, field);
        }

        public void removeField(Field field)
        {
            fields.remove(field);
        }

        public void removeField(int index)
        {
            fields.remove(index);
        }
    }
}
