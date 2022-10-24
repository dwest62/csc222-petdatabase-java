import java.util.ArrayList;

/**
 * Represents a console table builder
 */
public class TableBuilder
{
    private final ArrayList<Field> fields;
    
    /**
     * @param fields Represents parameters of table fields.
     */
    public TableBuilder(ArrayList<Field> fields)
    {
        this.fields = fields;
    }
    
    /**
     * @return String containing header based on fields given
     */
    public String buildHeader()
    {
        String[] entries = StringIntPair.extractStrings(fields.toArray(new Field[0]));
        return buildHorizontalBar() +
                buildRow(entries) +
                buildHorizontalBar();
    }
    
    /**
     * @return String containing horizontalBar sized appropriately based on fields given
     */
    public String buildHorizontalBar()
    {
        StringBuilder s = new StringBuilder("+");
        int totalWidth = 0;
        for (Field field : fields)
            totalWidth += field.getWidth();
        return s.append("-".repeat(Math.max(0, totalWidth + fields.size() - 1))).append("+\n").toString();
    }
    
    /**
     * @param row array containing data for a give row
     * @return String of combined row data sized appropriately based on fields
     */
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
    
    /**
     * @param nRows number of rows in table
     * @return String containing footer of table sized appropriately based on fields params
     */
    public String buildFooter(int nRows)
    {
        return buildHorizontalBar() + nRows + " rows displayed.\n";
    }
    
    /**
     * Builds table resized to fit to widths of max length entry if needed.
     * @param table 2-D array representing rows and columns in table
     * @return String of table
     */
    public String buildAutoSizeTable(String[][] table)
    {
        autoFitWidths(table);
        StringBuilder s = new StringBuilder(buildHeader());
        for (String[] row : table)
            s.append(buildRow(row));

        return s.append(buildFooter(table.length)).toString();
    }
    
    /**
     * Resize table widths to fit max entry length in table if needed.
     * @param rows 2-D array representing rows and columns in table
     */
    public void autoFitWidths(String[][] rows)
    {
        for (String[] row : rows)
            for (int i = 0; i < row.length; i++)
                fields.get(i).setWidth(Math.max(row[i].length() + 2, fields.get(i).getWidth()));
    }
}
