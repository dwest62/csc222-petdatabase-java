public class PetDatabaseCommands {

    class ShowPets extends MenuCommand
    {
        ShowPets()
        {
            super("View all Pets");
        }
        public void execute()
        {
            PetDatabase.showPets();
        }
    }
    class AddPets extends MenuCommand
    {
        AddPets()
        {
            super("Add new pets");
        }
        public void execute()
        {
            PetDatabase.addPets();
        }
    }
    class RemovePets extends MenuCommand
    {
        RemovePets() {
            super("Remove a pet");
        }
        public void execute()
        {
            PetDatabase.removePet();
        }
    }
    class UpdatePet extends MenuCommand
    {
        UpdatePet() {
            super("Update a pet");
        }
        public void execute()
        {
            PetDatabase.updatePet();
        }
    }
    class SearchByName extends MenuCommand
    {
        SearchByName()
        {
            super("Search for pet by name");
        }
        public void execute()
        {
            PetDatabase.searchPetsByName();
        }
    }
    class SearchByAge extends MenuCommand
    {
        SearchByAge()
        {
            super("Search for pet by age");
        }
        public void execute()
        {
            PetDatabase.searchPetsByAge();
        }
    }
    class SearchByType extends MenuCommand
    {
        SearchByType()
        {
            super("Search for pet by type");
        }
        public void execute()
        {
            PetDatabase.searchPetsByType();
        }
    }
}