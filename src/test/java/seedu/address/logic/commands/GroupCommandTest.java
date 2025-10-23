package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;
import seedu.address.testutil.PersonBuilder;

public class GroupCommandTest {

    @Test
    public void execute_sufficientPlayers_success() throws Exception {
        // Create 5 unassigned players
        Person top = new PersonBuilder().withName("Top1").withRole("top")
                .withRank("Gold").withChampion("Garen").build();
        Person jungle = new PersonBuilder().withName("Jungle1").withRole("jungle")
                .withRank("Gold").withChampion("Lee Sin").build();
        Person mid = new PersonBuilder().withName("Mid1").withRole("mid")
                .withRank("Gold").withChampion("Ahri").build();
        Person adc = new PersonBuilder().withName("Adc1").withRole("adc")
                .withRank("Gold").withChampion("Jinx").build();
        Person support = new PersonBuilder().withName("Support1").withRole("support")
                .withRank("Gold").withChampion("Leona").build();

        List<Person> unassignedPlayers = Arrays.asList(top, jungle, mid, adc, support);
        ModelStubWithUnassignedPlayers modelStub = new ModelStubWithUnassignedPlayers(unassignedPlayers);

        GroupCommand command = new GroupCommand();
        CommandResult result = command.execute(modelStub);

        // Verify team was added
        assertEquals(1, modelStub.teamsAdded.size());
        assertTrue(result.getFeedbackToUser().contains("Successfully created 1 team"));
    }

    @Test
    public void execute_noUnassignedPlayers_throwsCommandException() {
        ModelStubWithUnassignedPlayers modelStub = new ModelStubWithUnassignedPlayers(new ArrayList<>());

        GroupCommand command = new GroupCommand();

        CommandException exception = assertThrows(CommandException.class, () -> command.execute(modelStub));
        assertTrue(exception.getMessage().contains("No unassigned players"));
    }

    @Test
    public void execute_insufficientPlayers_throwsCommandException() {
        // Only 3 players - not enough for a full team
        Person top = new PersonBuilder().withName("Top1").withRole("top")
                .withRank("Gold").withChampion("Garen").build();
        Person jungle = new PersonBuilder().withName("Jungle1").withRole("jungle")
                .withRank("Gold").withChampion("Lee Sin").build();
        Person mid = new PersonBuilder().withName("Mid1").withRole("mid")
                .withRank("Gold").withChampion("Ahri").build();

        List<Person> unassignedPlayers = Arrays.asList(top, jungle, mid);
        ModelStubWithUnassignedPlayers modelStub = new ModelStubWithUnassignedPlayers(unassignedPlayers);

        GroupCommand command = new GroupCommand();

        assertThrows(CommandException.class, () -> command.execute(modelStub));
    }

    @Test
    public void execute_multipleTeams_success() throws Exception {
        // Create 10 unassigned players (2 per role)
        Person top1 = new PersonBuilder().withName("Top1").withRole("top")
                .withRank("Gold").withChampion("Garen").build();
        Person top2 = new PersonBuilder().withName("Top2").withRole("top")
                .withRank("Silver").withChampion("Darius").build();

        Person jungle1 = new PersonBuilder().withName("Jungle1").withRole("jungle")
                .withRank("Gold").withChampion("Lee Sin").build();
        Person jungle2 = new PersonBuilder().withName("Jungle2").withRole("jungle")
                .withRank("Silver").withChampion("Jarvan IV").build();

        Person mid1 = new PersonBuilder().withName("Mid1").withRole("mid")
                .withRank("Gold").withChampion("Ahri").build();
        Person mid2 = new PersonBuilder().withName("Mid2").withRole("mid")
                .withRank("Silver").withChampion("Zed").build();

        Person adc1 = new PersonBuilder().withName("Adc1").withRole("adc")
                .withRank("Gold").withChampion("Jinx").build();
        Person adc2 = new PersonBuilder().withName("Adc2").withRole("adc")
                .withRank("Silver").withChampion("Ashe").build();

        Person support1 = new PersonBuilder().withName("Support1").withRole("support")
                .withRank("Gold").withChampion("Leona").build();
        Person support2 = new PersonBuilder().withName("Support2").withRole("support")
                .withRank("Silver").withChampion("Thresh").build();

        List<Person> unassignedPlayers = Arrays.asList(
                top1, top2, jungle1, jungle2, mid1, mid2, adc1, adc2, support1, support2
        );
        ModelStubWithUnassignedPlayers modelStub = new ModelStubWithUnassignedPlayers(unassignedPlayers);

        GroupCommand command = new GroupCommand();
        CommandResult result = command.execute(modelStub);

        // Verify 2 teams were added
        assertEquals(2, modelStub.teamsAdded.size());
        assertTrue(result.getFeedbackToUser().contains("Successfully created 2 team"));
    }

    /**
     * A Model stub that always provides unassigned players and tracks teams.
     */
    private class ModelStubWithUnassignedPlayers implements Model {
        private final List<Person> unassignedPlayers;
        private final List<Team> teamsAdded = new ArrayList<>();

        ModelStubWithUnassignedPlayers(List<Person> unassignedPlayers) {
            requireNonNull(unassignedPlayers);
            this.unassignedPlayers = new ArrayList<>(unassignedPlayers);
        }

        @Override
        public ObservableList<Person> getUnassignedPersonList() {
            return FXCollections.observableArrayList(unassignedPlayers);
        }

        @Override
        public void addTeam(Team team) {
            teamsAdded.add(team);
        }

        @Override
        public boolean hasTeam(Team team) {
            return teamsAdded.contains(team);
        }

        @Override
        public boolean isPersonInAnyTeam(Person person) {
            return false;
        }

        @Override
        public Optional<Person> findPersonByName(Name name) {
            return Optional.empty();
        }

        @Override
        public boolean hasPerson(Person person) {
            return false;
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteTeam(Team target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTeam(Team target, Team editedTeam) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            return null;
        }

        @Override
        public GuiSettings getGuiSettings() {
            return null;
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            return null;
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook addressBook) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return null;
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return null;
        }

        @Override
        public ObservableList<Team> getFilteredTeamList() {
            return null;
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredTeamList(Predicate<Team> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }
}
