/**
 * Base Menu class (could be implemented as abstract)
 */
class Menu
{
    public final static Option FILL_STATE = new Option(0, "FILL_STATE");
    private Option state = FILL_STATE;

    /**
     * Get current state and set menu state to FILL_STATE
     *
     * @return current state
     */
    public c consumeState()
    {
        Option copy = state;
        state = FILL_STATE;     // Fill state
        return copy;
    }

    /**
     * @return Current state
     */
    public Option getState()
    {
        return state;
    }

    /**
     * Update Menu State.
     *
     * @param choice New menu state.
     */
    public void fillState(int choice)
    {
        this.state = Choice.options[choice];
    }

    public String listMenu(String header, String footer)
    {
        return header + Option.list(Choice.options) + footer;
    }

    public String listMenu()
    {
        return listMenu("Main menu. Please choice from an option below.\n", "Choice: ");
    }
}
