package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

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

public class MakeGroupCommandTest {

    private final Person alice = new PersonBuilder()
            .withName("Alice").withRole("mid").withRank("Gold").withChampion("Ahri").build();
    private final Person bob = new PersonBuilder()
            .withName("Bob").withRole("top").withRank("Platinum").withChampion("Garen").build();
    private final Person cathy = new PersonBuilder()
            .withName("Cathy").withRole("adc").withRank("Diamond").withChampion("Jinx").build();
    private final Person derek = new PersonBuilder()
            .withName("Derek").withRole("jungle").withRank("Gold").withChampion("Lee Sin").build();
    private final Person ella = new PersonBuilder()
            .withName("Ella").withRole("support").withRank("Silver").withChampion("Leona").build();

    @Test
    public void execute_validPlayers_teamCreatedSuccessfully() throws CommandException {
        List<Person> players = Arrays.asList(alice, bob, cathy, derek, ella);
        List<Name> playerNames = new ArrayList<>();
        players.forEach(p -> playerNames.add(p.getName()));

        ModelStubAcceptingTeamAdded modelStub = new ModelStubAcceptingTeamAdded(players);

        MakeGroupCommand command = new MakeGroupCommand(playerNames);
        CommandResult result = command.execute(modelStub);

        Team actualTeam = modelStub.teamsAdded.get(0); // the team that was added
        assertEquals(String.format(MakeGroupCommand.MESSAGE_SUCCESS, actualTeam),
                result.getFeedbackToUser());

        // Compare team members using getPersons()
        List<Person> actualPlayers = actualTeam.getPersons();
        assertTrue(players.containsAll(actualPlayers) && actualPlayers.containsAll(players));
    }

    @Test
    public void execute_duplicateNames_throwsCommandException() throws CommandException {
        List<Name> playerNames = Arrays.asList(
                alice.getName(),
                alice.getName(),
                bob.getName(),
                cathy.getName(),
                derek.getName()
        );
        MakeGroupCommand command = new MakeGroupCommand(playerNames);
        ModelStub modelStub = new ModelStub();

        CommandException thrown = assertThrows(CommandException.class, () -> command.execute(modelStub));
        assertEquals(MakeGroupCommand.MESSAGE_DUPLICATE_NAMES, thrown.getMessage());
    }

    @Test
    public void execute_playerNotFound_throwsCommandException() throws CommandException {
        List<Person> existingPlayers = Arrays.asList(alice, bob, cathy, derek);
        ModelStubAcceptingTeamAdded modelStub = new ModelStubAcceptingTeamAdded(existingPlayers);

        List<Name> playerNames = Arrays.asList(
                alice.getName(), bob.getName(), cathy.getName(), derek.getName(),
                new Name("NonExistent")
        );

        MakeGroupCommand command = new MakeGroupCommand(playerNames);

        CommandException thrown = assertThrows(CommandException.class, () -> command.execute(modelStub));

        // Ensure exception message contains the missing player
        assertTrue(thrown.getMessage().contains("NonExistent"));
    }

    @Test
    public void execute_insufficientPlayers_throwsCommandException() {
        List<Name> fewerNames = Arrays.asList(alice.getName(), bob.getName(), cathy.getName());
        MakeGroupCommand command = new MakeGroupCommand(fewerNames);
        ModelStub modelStub = new ModelStubAcceptingTeamAdded(Arrays.asList(alice, bob, cathy, derek, ella));

        CommandException thrown = assertThrows(CommandException.class, () -> command.execute(modelStub));
        assertEquals(MakeGroupCommand.MESSAGE_INSUFFICIENT_PLAYERS, thrown.getMessage());
    }

    @Test
    public void equals() {
        List<Name> names1 = Arrays.asList(
                alice.getName(), bob.getName(), cathy.getName(), derek.getName(), ella.getName()
        );
        List<Name> names2 = Arrays.asList(
                alice.getName(), bob.getName(), cathy.getName(), derek.getName(), new Name("Fiona")
        );

        MakeGroupCommand command1 = new MakeGroupCommand(names1);
        MakeGroupCommand command2 = new MakeGroupCommand(names1);
        MakeGroupCommand command3 = new MakeGroupCommand(names2);

        assertTrue(command1.equals(command1)); // same object
        assertTrue(command1.equals(command2)); // same values
        assertFalse(command1.equals(command3)); // different values
        assertFalse(command1.equals(null)); // null
        assertFalse(command1.equals("string")); // different type
    }

    // ======= STUBS =======

    private class ModelStub implements Model {

        @Override
        public Optional<Person> findPersonByName(Name name) {
            return Optional.empty();
        }

        @Override
        public boolean hasTeam(Team team) {
            return false;
        }

        @Override
        public boolean hasPerson(Person person) {
            return false;
        }

        @Override
        public void addTeam(Team team) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
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
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return null;
        }

        @Override
        public Path getAddressBookFilePath() {
            return null;
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
        }

        @Override
        public void deletePerson(Person target) {
        }

        @Override
        public void deleteTeam(Team target) {
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
        }

        @Override
        public void setTeam(Team target, Team editedTeam) {
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
        }

        @Override
        public void updateFilteredTeamList(Predicate<Team> predicate) {
        }
    }

    private class ModelStubAcceptingTeamAdded extends ModelStub {
        private final List<Person> existingPlayers;
        private final List<Team> teamsAdded = new ArrayList<>();

        ModelStubAcceptingTeamAdded(List<Person> existingPlayers) {
            requireNonNull(existingPlayers);
            this.existingPlayers = new ArrayList<>(existingPlayers);
        }

        @Override
        public Optional<Person> findPersonByName(Name name) {
            return existingPlayers.stream()
                    .filter(p -> p.getName().equals(name))
                    .findFirst();
        }

        @Override
        public void addTeam(Team team) {
            teamsAdded.add(team);
        }

        @Override
        public boolean hasTeam(Team team) {
            return teamsAdded.contains(team);
        }
    }
}
