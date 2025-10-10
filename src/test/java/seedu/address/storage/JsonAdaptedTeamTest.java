package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTeams.TEAM_A;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;
import seedu.address.testutil.TypicalPersons;
import seedu.address.testutil.TypicalTeams;

public class JsonAdaptedTeamTest {
    private static final List<Person> ALL_PERSONS = TypicalPersons.getTypicalPersons();
    private static final List<Team> ALL_TEAMS = TypicalTeams.getTypicalTeams();

    @Test
    public void toModelType_validTeamDetails_returnsTeam() throws Exception {
        JsonAdaptedTeam team = new JsonAdaptedTeam(TEAM_A);
        assertEquals(TEAM_A, team.toModelType(ALL_PERSONS));
    }

    @Test
    public void toModelType_invalidPersonId_throwsIllegalValueException() {
        List<String> invalidPersonIds = TEAM_A.getPersons().stream()
                .map(Person::getId)
                .collect(Collectors.toList());
        invalidPersonIds.add("invalid-id-123");

        JsonAdaptedTeam team = new JsonAdaptedTeam(TEAM_A.getId(), invalidPersonIds);
        String expectedMessage = "Invalid Person ID in Team: invalid-id-123";
        assertThrows(IllegalValueException.class, expectedMessage, () -> team.toModelType(ALL_PERSONS));
    }
}
