package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.team.Team;

/**
 * Displays the details of a specific {@code Team} in a popup window.
 * <p>Uses the provided list index to compute a friendly label
 * such as "Team 1", which is shown instead of the internal team ID.</p>
 */
public class ViewTeamCommand extends Command {
    public static final String COMMAND_WORD = "viewTeam";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows team details in a popup window.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Viewing Team: %1$s";

    private final Index index;

    /**
     * Constructs a {@code ViewTeamCommand} with the given team {@link Index}.
     *
     * @param index the one-based index of the team to display; must not be {@code null}.
     * @throws NullPointerException if {@code index} is {@code null}.
     */
    public ViewTeamCommand(Index index) {
        requireNonNull(index, "Index cannot be null");
        this.index = index;
    }

    /**
     * Executes the command to display the team details corresponding to the given index.
     * <p>
     * Retrieves the team from the model’s filtered team list, validates the index,
     * and then opens a popup window showing that team’s statistics.
     * </p>
     *
     * @param model the {@link Model} which holds the current team list and UI state.
     * @return a {@link CommandResult} that triggers the popup display of team stats.
     * @throws CommandException if the provided index is invalid or out of range.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model, "Model cannot be null");
        List<Team> teams = model.getFilteredTeamList();
        if (index.getZeroBased() >= teams.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TEAM_DISPLAYED_INDEX);
        }
        Team team = teams.get(index.getZeroBased());
        assert team != null : "Team at valid index should not be null";

        String label = "Team " + index.getOneBased(); // user-facing team number
        return CommandResult.showTeamDetail(String.format(MESSAGE_SUCCESS, label), team);
    }

    /**
     * Returns {@code true} if both {@code ViewTeamCommand} objects are equal.
     * Equality is defined based on the team index being the same.
     *
     * @param other another object to compare against.
     * @return {@code true} if both commands have the same index; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ViewTeamCommand)) {
            return false;
        }

        ViewTeamCommand otherCommand = (ViewTeamCommand) other;
        return index.equals(otherCommand.index);
    }

    /**
     * Returns the hash code for this command, consistent with {@link #equals(Object)}.
     * Uses {@link Index#hashCode()} to ensure equal objects produce equal hash codes.
     *
     * @return the hash code based on the {@code index} field.
     */
    @Override
    public int hashCode() {
        return index.hashCode();
    }

    /**
     * Returns a string representation of this command, useful for debugging and logs.
     *
     * @return a formatted string containing the class name and team index.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .toString();
    }
}

