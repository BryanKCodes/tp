package seedu.address.model.team;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.model.team.exceptions.DuplicateChampionException;
import seedu.address.model.team.exceptions.DuplicateRoleException;
import seedu.address.model.team.exceptions.InvalidTeamSizeException;
import seedu.address.testutil.PersonBuilder;

public class TeamTest {

    // Valid team with 5 players: unique roles and unique champions
    private final Person topPlayer = new PersonBuilder().withName("Top Player")
            .withRole("top").withChampion("Garen").withRank("gold").build();
    private final Person junglePlayer = new PersonBuilder().withName("Jungle Player")
            .withRole("jungle").withChampion("Lee Sin").withRank("platinum").build();
    private final Person midPlayer = new PersonBuilder().withName("Mid Player")
            .withRole("mid").withChampion("Ahri").withRank("diamond").build();
    private final Person adcPlayer = new PersonBuilder().withName("ADC Player")
            .withRole("adc").withChampion("Jinx").withRank("gold").build();
    private final Person supportPlayer = new PersonBuilder().withName("Support Player")
            .withRole("support").withChampion("Thresh").withRank("platinum").build();

    private final List<Person> validFivePersons = Arrays.asList(
            topPlayer, junglePlayer, midPlayer, adcPlayer, supportPlayer);

    // Invalid: Only 4 players
    private final List<Person> invalidFourPersons = Arrays.asList(
            topPlayer, junglePlayer, midPlayer, adcPlayer);

    // Invalid: 6 players
    private final Person extraPlayer = new PersonBuilder().withName("Extra Player")
            .withRole("mid").withChampion("Zed").withRank("master").build();
    private final List<Person> invalidSixPersons = Arrays.asList(
            topPlayer, junglePlayer, midPlayer, adcPlayer, supportPlayer, extraPlayer);

    // Invalid: Duplicate role (two mid players)
    private final Person duplicateMidPlayer = new PersonBuilder().withName("Another Mid")
            .withRole("mid").withChampion("Zed").withRank("diamond").build();
    private final List<Person> duplicateRolePlayers = Arrays.asList(
            topPlayer, junglePlayer, midPlayer, duplicateMidPlayer, supportPlayer);

    // Invalid: Duplicate champion (two Ahri players)
    private final Person duplicateChampionPlayer = new PersonBuilder().withName("Another Ahri Player")
            .withRole("adc").withChampion("Ahri").withRank("platinum").build();
    private final List<Person> duplicateChampionPlayers = Arrays.asList(
            topPlayer, junglePlayer, midPlayer, duplicateChampionPlayer, supportPlayer);

    @Test
    public void constructor_validPersons_success() {
        Team team = new Team(validFivePersons);
        assertEquals(validFivePersons, team.getPersons());
        assertNotNull(team.getId());
    }

    @Test
    public void constructor_validPersonsWithId_success() {
        String testId = "test-team-id";
        Team team = new Team(testId, validFivePersons);
        assertEquals(validFivePersons, team.getPersons());
        assertEquals(testId, team.getId());
    }

    @Test
    public void constructor_nullPersons_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Team((List<Person>) null));
        assertThrows(NullPointerException.class, () -> new Team("test-id", null));
    }

    @Test
    public void constructor_nullId_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Team(null, validFivePersons));
    }

    @Test
    public void constructor_fourPersons_throwsInvalidTeamSizeException() {
        InvalidTeamSizeException exception = assertThrows(
                InvalidTeamSizeException.class, () -> new Team(invalidFourPersons));
        assertTrue(exception.getMessage().contains("4"));
    }

    @Test
    public void constructor_sixPersons_throwsInvalidTeamSizeException() {
        InvalidTeamSizeException exception = assertThrows(
                InvalidTeamSizeException.class, () -> new Team(invalidSixPersons));
        assertTrue(exception.getMessage().contains("6"));
    }

    @Test
    public void constructor_duplicateRole_throwsDuplicateRoleException() {
        DuplicateRoleException exception = assertThrows(
                DuplicateRoleException.class, () -> new Team(duplicateRolePlayers));

        // Check that exception contains information about the conflicting players
        assertNotNull(exception.getPerson1());
        assertNotNull(exception.getPerson2());
        assertEquals(exception.getPerson1().getRole(), exception.getPerson2().getRole());
        assertTrue(exception.getMessage().contains("Mid"));
    }

    @Test
    public void constructor_duplicateChampion_throwsDuplicateChampionException() {
        DuplicateChampionException exception = assertThrows(
                DuplicateChampionException.class, () -> new Team(duplicateChampionPlayers));

        // Check that exception contains information about the conflicting players
        assertNotNull(exception.getPerson1());
        assertNotNull(exception.getPerson2());
        assertEquals(exception.getPerson1().getChampion(), exception.getPerson2().getChampion());
        assertTrue(exception.getMessage().contains("Ahri"));
    }

    @Test
    public void hasPerson_existingPerson_returnsTrue() {
        Team team = new Team(validFivePersons);
        assertTrue(team.hasPerson(topPlayer));
        assertTrue(team.hasPerson(junglePlayer));
        assertTrue(team.hasPerson(midPlayer));
        assertTrue(team.hasPerson(adcPlayer));
        assertTrue(team.hasPerson(supportPlayer));
    }

    @Test
    public void hasPerson_nonExistingPerson_returnsFalse() {
        Team team = new Team(validFivePersons);
        Person nonExistingPerson = new PersonBuilder().withName("Non Existing")
                .withRole("top").withChampion("Darius").withRank("bronze").build();
        assertFalse(team.hasPerson(nonExistingPerson));
    }

    @Test
    public void getPersons_modifyReturnedList_teamUnchanged() {
        Team team = new Team(validFivePersons);
        List<Person> returnedList = team.getPersons();

        // Modify the returned list
        Person newPerson = new PersonBuilder().withName("New Player")
                .withRole("top").withChampion("Darius").build();
        returnedList.add(newPerson);

        // Original team should be unchanged
        assertEquals(5, team.getPersons().size());
        assertFalse(team.hasPerson(newPerson));
    }

    @Test
    public void equals_sameTeam_returnsTrue() {
        Team team = new Team("test-id", validFivePersons);
        Team otherTeam = new Team("test-id", validFivePersons);
        assertTrue(team.equals(otherTeam));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        Team team = new Team("test-id", validFivePersons);
        assertTrue(team.equals(team));
    }

    @Test
    public void equals_null_returnsFalse() {
        Team team = new Team("test-id", validFivePersons);
        assertFalse(team.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        Team team = new Team("test-id", validFivePersons);
        assertFalse(team.equals("string"));
    }

    @Test
    public void equals_differentId_returnsFalse() {
        Team team = new Team("test-id-1", validFivePersons);
        Team otherTeam = new Team("test-id-2", validFivePersons);
        assertFalse(team.equals(otherTeam));
    }

    @Test
    public void equals_differentPersons_returnsFalse() {
        List<Person> differentPersons = Arrays.asList(
                junglePlayer, topPlayer, midPlayer, adcPlayer, supportPlayer); // Different order
        Team team = new Team("test-id", validFivePersons);
        Team otherTeam = new Team("test-id", differentPersons);
        assertFalse(team.equals(otherTeam));
    }

    @Test
    public void hashCode_sameTeam_sameHashCode() {
        Team team = new Team("test-id", validFivePersons);
        Team otherTeam = new Team("test-id", validFivePersons);
        assertEquals(team.hashCode(), otherTeam.hashCode());
    }

    @Test
    public void toString_validTeam_correctFormat() {
        Team team = new Team("test-id", validFivePersons);
        String result = team.toString();

        assertTrue(result.contains("test-id"));
        assertTrue(result.contains("persons"));
    }

    @Test
    public void teamSize_constantValue() {
        assertEquals(5, Team.TEAM_SIZE);
    }
}
