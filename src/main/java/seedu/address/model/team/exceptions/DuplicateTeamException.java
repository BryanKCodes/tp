package seedu.address.model.team.exceptions;

/**
 * Signals that the operation will result in duplicate Teams.
 * Teams are considered duplicates if they have the same identity (ID).
 */
public class DuplicateTeamException extends RuntimeException {
    public DuplicateTeamException() {
        super("Operation would result in duplicate teams");
    }
}
