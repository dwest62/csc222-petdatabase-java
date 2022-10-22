package cli_helpers;

public class Field extends StringIntPair
{
    public Field(int width, String name)
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
