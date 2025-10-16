package seedu.address.model.exceptions;

import seedu.address.model.person.Person;
import seedu.address.model.team.Team;

/**
 * Signals that the operation cannot be performed because the person is currently in a team.
 * Thrown when attempting to delete or edit a person who is assigned to a team.
 */
public class PersonInTeamException extends RuntimeException {
    /**
     * Constructs a PersonInTeamException with details about the person and team.
     * @param person The person who is in a team.
     * @param team The team that the person belongs to.
     */
    public PersonInTeamException(Person person, Team team) {
        super(String.format("Cannot perform operation: %s is currently in team %s. "
                + "Please remove them from the team first.",
                person.getName(), team.getId()));
    }
}
