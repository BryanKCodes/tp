package seedu.address.model.team.exceptions;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.person.Role;

/**
 * Signals that one or more required roles are missing when attempting to form a team.
 */
public class MissingRolesException extends Exception {

    /**
     * Constructs a MissingRolesException with a list of missing roles.
     * The exception message is automatically formatted to list all missing roles.
     *
     * @param missingRoles The list of roles that are missing.
     */
    public MissingRolesException(List<Role> missingRoles) {
        super(formatMessage(missingRoles));
    }

    /**
     * Formats the exception message from the list of missing roles.
     *
     * @param missingRoles The list of roles that are missing.
     * @return Formatted error message.
     */
    private static String formatMessage(List<Role> missingRoles) {
        String roleList = missingRoles.stream()
                .map(Role::toString)
                .collect(Collectors.joining(", "));

        return "Cannot form a team: No persons available for role(s): " + roleList + ".";
    }
}
